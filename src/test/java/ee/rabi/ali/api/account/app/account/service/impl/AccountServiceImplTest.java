package ee.rabi.ali.api.account.app.account.service.impl;

import ee.rabi.ali.api.account.app.account.repository.AccountRepository;
import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import ee.rabi.ali.api.account.app.balance_snapshot.service.BalanceSnapshotService;
import ee.rabi.ali.api.account.app.ledger.service.LedgerService;
import ee.rabi.ali.api.account.orm.model.tables.records.AccountRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
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
}
