package ee.rabi.ali.api.account.app.transfer.service.impl;

import ee.rabi.ali.api.account.app.account.service.AccountService;
import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import ee.rabi.ali.api.account.app.balance_snapshot.service.exception.InsufficientBalanceException;
import ee.rabi.ali.api.account.app.ledger.service.LedgerService;
import ee.rabi.ali.api.account.app.ledger.service.model.LedgerDto;
import ee.rabi.ali.api.account.app.transfer.repository.TransferRepository;
import ee.rabi.ali.api.account.app.transfer.service.model.CreateTransferDto;
import ee.rabi.ali.api.account.app.transfer.service.model.TransferDto;
import ee.rabi.ali.api.account.orm.model.tables.records.TransferRecord;
import io.github.netmikey.logunit.api.LogCapturer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import static ee.rabi.ali.api.account.test.util.StringUtils.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferServiceImplTest {

    @RegisterExtension
    public LogCapturer logCapturer = LogCapturer.create().captureForType(TransferServiceImpl.class);

    @Mock
    private TransferRepository transferRepository;
    @Mock
    private LedgerService ledgerService;
    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransferServiceImpl transferService;

    @Test
    public void list_shouldReturnTransfers_givenData() {
        final TransferRecord record = new TransferRecord(
                randomString(),
                randomString(),
                randomString(),
                BigDecimal.TEN,
                Timestamp.from(Instant.now()));
        when(transferRepository.findAll()).thenReturn(Collections.singletonList(record));

        final List<TransferDto> result = transferService.list();

        assertEquals(1, result.size());
        assertThat(result.get(0)).isEqualToComparingFieldByField(record);
    }

    @Test
    public void create_shouldCreateNewTransfer_givenData() throws InsufficientBalanceException {
        final String fromAccountId = randomString();
        final String toAccountId = randomString();
        final CreateTransferDto createTransferDto = CreateTransferDto
                .builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .amount(BigDecimal.TEN)
                .build();
        final AccountDto fromAccountDto = AccountDto
                .builder()
                .id(fromAccountId)
                .currency(Currency.getInstance("EUR"))
                .build();
        final AccountDto toAccountDto = AccountDto
                .builder()
                .id(toAccountId)
                .currency(Currency.getInstance("EUR"))
                .build();
        when(accountService.find(fromAccountId)).thenReturn(fromAccountDto);
        when(accountService.find(toAccountId)).thenReturn(toAccountDto);

        final TransferDto result = transferService.create(createTransferDto);

        assertThat(result).isEqualToIgnoringGivenFields(createTransferDto, "id", "createdAt");

        final ArgumentCaptor<TransferRecord> transferRecordArgumentCaptor = ArgumentCaptor.forClass(TransferRecord.class);
        verify(transferRepository).insert(transferRecordArgumentCaptor.capture());
        final TransferRecord transferRecord = transferRecordArgumentCaptor.getValue();
        assertNotNull(transferRecord.getId());
        assertNotNull(transferRecord.getCreatedAt());
        assertThat(result).isEqualToComparingFieldByField(transferRecord);

        final ArgumentCaptor<LedgerDto> debitLedgerDtoArgumentCaptor = ArgumentCaptor.forClass(LedgerDto.class);
        final ArgumentCaptor<LedgerDto> creditLedgerDtoArgumentCaptor = ArgumentCaptor.forClass(LedgerDto.class);
        verify(ledgerService).create(debitLedgerDtoArgumentCaptor.capture(), creditLedgerDtoArgumentCaptor.capture());
        final LedgerDto debitLedger = debitLedgerDtoArgumentCaptor.getValue();
        final LedgerDto creditLedger = creditLedgerDtoArgumentCaptor.getValue();
        assertLedgerDto(fromAccountId, transferRecord.getId(), debitLedger, BigDecimal.TEN.negate());
        assertLedgerDto(toAccountId, transferRecord.getId(), creditLedger, BigDecimal.TEN);
    }

    private void assertLedgerDto(final String fromAccountId, final String transferRecordId, final LedgerDto debitLedger, final BigDecimal negate) {
        assertEquals(debitLedger.getAccountId(), fromAccountId);
        assertEquals(debitLedger.getTransactionId(), transferRecordId);
        assertEquals(debitLedger.getAmount(), negate);
        assertNotNull(debitLedger.getId());
        assertNotNull(debitLedger.getCreatedAt());
    }
}
