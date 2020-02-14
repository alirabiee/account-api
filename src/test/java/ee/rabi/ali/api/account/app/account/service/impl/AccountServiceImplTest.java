package ee.rabi.ali.api.account.app.account.service.impl;

import ee.rabi.ali.api.account.app.account.repository.AccountRepository;
import ee.rabi.ali.api.account.app.account.service.exception.AccountNotFoundException;
import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import ee.rabi.ali.api.account.app.balance_snapshot.service.BalanceSnapshotService;
import ee.rabi.ali.api.account.app.ledger.service.LedgerService;
import ee.rabi.ali.api.account.orm.model.tables.records.AccountRecord;
import io.github.netmikey.logunit.api.LogCapturer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.event.Level;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @RegisterExtension
    LogCapturer logCapturer = LogCapturer.create().captureForType(AccountServiceImpl.class);

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private BalanceSnapshotService balanceSnapshotService;
    @Mock
    private LedgerService ledgerService;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void list_shouldReturnAccounts_givenData() {
        final String id = UUID.randomUUID().toString();
        final String currency = "SEK";
        when(accountRepository.findAll()).thenReturn(Collections.singletonList(new AccountRecord(id, currency)));

        final List<AccountDto> result = accountService.list();

        assertEquals(1, result.size());
        final AccountDto accountDto = result.get(0);
        assertEquals(id, accountDto.getId());
        assertEquals(Currency.getInstance(currency), accountDto.getCurrency());
    }

    @Test
    public void find_shouldReturnAccount_givenIdExists() {
        final String id = UUID.randomUUID().toString();
        final String currency = "SEK";
        when(accountRepository.findById(id)).thenReturn(Optional.of(new AccountRecord(id, currency)));

        final AccountDto result = accountService.find(id);

        assertEquals(id, result.getId());
        assertEquals(Currency.getInstance(currency), result.getCurrency());
    }

    @Test
    public void find_shouldThrowAccountNotFoundException_givenIdDoesNotExist() {
        final String id = UUID.randomUUID().toString();
        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.find(id));
        logCapturer.forLevel(Level.ERROR).assertContains("account-service:not-found account-id=" + id);
    }
}
