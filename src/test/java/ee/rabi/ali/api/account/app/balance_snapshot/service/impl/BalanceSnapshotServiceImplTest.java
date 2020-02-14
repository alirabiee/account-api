package ee.rabi.ali.api.account.app.balance_snapshot.service.impl;

import ee.rabi.ali.api.account.app.account.service.exception.AccountNotFoundException;
import ee.rabi.ali.api.account.app.balance_snapshot.repository.BalanceSnapshotRepository;
import ee.rabi.ali.api.account.app.balance_snapshot.service.exception.InsufficientBalanceException;
import ee.rabi.ali.api.account.app.balance_snapshot.service.model.BalanceSnapshotDto;
import ee.rabi.ali.api.account.orm.model.tables.records.BalanceSnapshotRecord;
import io.github.netmikey.logunit.api.LogCapturer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.event.Level;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static ee.rabi.ali.api.account.test.util.StringUtils.randomString;
import static java.math.BigDecimal.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BalanceSnapshotServiceImplTest {

    //CHECKSTYLE:OFF
    @RegisterExtension
    public LogCapturer logCapturer = LogCapturer.create().captureForType(BalanceSnapshotServiceImpl.class);
    //CHECKSTYLE:ON

    @Mock
    private BalanceSnapshotRepository balanceSnapshotRepository;

    @InjectMocks
    private BalanceSnapshotServiceImpl balanceSnapshotService;

    @Test
    public void getBalanceForAccountId_shouldReturnBalance_givenData() {
        final String accountId = randomString();
        when(balanceSnapshotRepository.findByAccountId(accountId)).thenReturn(Optional.of(new BalanceSnapshotRecord(accountId, TEN, Timestamp.from(Instant.now()), 0)));

        final BigDecimal balance = balanceSnapshotService.getBalanceForAccountId(accountId);

        assertEquals(TEN, balance);
    }

    @Test
    public void getBalanceForAccountId_shouldThrowAccountNotFoundException_givenIdDoesNotExist() {
        final String accountId = randomString();
        when(balanceSnapshotRepository.findByAccountId(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> balanceSnapshotService.getBalanceForAccountId(accountId));
        logCapturer.forLevel(Level.ERROR).assertContains("balance-snapshot-service:not-found account-id=" + accountId);
    }

    @Test
    public void create_shouldCreateNewRecord_givenData() {
        final String accountId = randomString();

        final BalanceSnapshotDto balanceSnapshotDto = balanceSnapshotService.create(accountId);

        final ArgumentCaptor<BalanceSnapshotRecord> argumentCaptor = ArgumentCaptor.forClass(BalanceSnapshotRecord.class);
        verify(balanceSnapshotRepository).insert(argumentCaptor.capture());
        final BalanceSnapshotRecord record = argumentCaptor.getValue();
        assertThat(balanceSnapshotDto).isEqualToComparingFieldByField(record);
        assertEquals(accountId, record.getAccountId());
        assertEquals(ZERO, record.getBalance());
        assertEquals(0, record.getVersion());
        assertNotNull(record.getUpdatedAt());
    }

    @Test
    public void updateBalance_shouldUpdateBalance_givenBalanceIsEnough() throws InsufficientBalanceException {
        final String accountId = randomString();
        final BalanceSnapshotRecord mock = mock(BalanceSnapshotRecord.class);
        when(mock.getBalance()).thenReturn(TEN);
        when(balanceSnapshotRepository.findByAccountId(accountId)).thenReturn(Optional.of(mock));

        balanceSnapshotService.updateBalance(accountId, TEN.negate());

        verify(mock).setBalance(ZERO);
        verify(mock).store();
    }

    @Test
    public void updateBalance_shouldThrowInsufficientBalanceException_givenBalanceIsNotEnough() {
        final String accountId = randomString();
        final BalanceSnapshotRecord mock = new BalanceSnapshotRecord(accountId, ONE, Timestamp.from(Instant.now()), 0);
        when(balanceSnapshotRepository.findByAccountId(accountId)).thenReturn(Optional.of(mock));

        assertThrows(InsufficientBalanceException.class, () -> balanceSnapshotService.updateBalance(accountId, TEN.negate()));
        logCapturer.forLevel(Level.ERROR).assertContains("balance-snapshot-service:insufficient-balance account-id=" + accountId + " balance=1 delta=-10");
    }

    @Test
    public void updateBalance_shouldThrowInsufficientBalanceException_givenAccountDoesNotExist() {
        final String accountId = randomString();
        when(balanceSnapshotRepository.findByAccountId(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> balanceSnapshotService.updateBalance(accountId, TEN.negate()));
        logCapturer.forLevel(Level.ERROR).assertContains("balance-snapshot-service:not-found account-id=" + accountId);
    }
}
