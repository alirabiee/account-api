package ee.rabi.ali.api.account.app.ledger.service.impl;

import ee.rabi.ali.api.account.app.balance_snapshot.service.BalanceSnapshotService;
import ee.rabi.ali.api.account.app.balance_snapshot.service.exception.InsufficientBalanceException;
import ee.rabi.ali.api.account.app.ledger.repository.LedgerRepository;
import ee.rabi.ali.api.account.app.ledger.service.model.LedgerDto;
import ee.rabi.ali.api.account.orm.model.tables.records.LedgerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static ee.rabi.ali.api.account.test.util.StringUtils.randomString;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LedgerServiceImplTest {

    @Mock
    private LedgerRepository ledgerRepository;
    @Mock
    private BalanceSnapshotService balanceSnapshotService;

    @InjectMocks
    private LedgerServiceImpl ledgerService;

    @Test
    public void getBalance_shouldReturnBalance_givenData() {
        final String accountId = randomString();
        when(ledgerRepository.getBalance(accountId)).thenReturn(TEN);

        final BigDecimal balance = ledgerService.getBalance(accountId);

        assertEquals(TEN, balance);
    }

    @Test
    public void create_shouldInsertLedgerRecordsAndUpdateBalanceSnapshot_givenData() throws InsufficientBalanceException {
        final String accountId = randomString();
        final BigDecimal amount = TEN;
        final LedgerDto ledgerDto = LedgerDto
                .builder()
                .id(randomString())
                .accountId(accountId)
                .transactionId(randomString())
                .amount(amount)
                .createdAt(Timestamp.from(Instant.now()))
                .build();

        ledgerService.create(ledgerDto);

        final ArgumentCaptor<LedgerRecord> ledgerRecordArgumentCaptor = ArgumentCaptor.forClass(LedgerRecord.class);
        verify(ledgerRepository).insert(ledgerRecordArgumentCaptor.capture());
        final LedgerRecord ledgerRecord = ledgerRecordArgumentCaptor.getValue();
        assertThat(ledgerDto).isEqualToComparingFieldByField(ledgerRecord);

        verify(balanceSnapshotService).updateBalance(accountId, amount);
    }
}
