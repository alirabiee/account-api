package ee.rabi.ali.api.account.test.data.transfer;

import ee.rabi.ali.api.account.app.transfer.repository.TransferRepository;
import ee.rabi.ali.api.account.app.transfer.service.model.TransferDto;
import ee.rabi.ali.api.account.orm.model.tables.records.TransferRecord;
import ee.rabi.ali.api.account.test.data.TestData;
import ee.rabi.ali.api.account.test.data.ledger.LedgerTestData;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static ee.rabi.ali.api.account.orm.IdGenerator.generate;

@Singleton
public class TransferTestData extends TestData {

    @Inject
    private LedgerTestData ledgerTestData;

    public List<TransferDto> findAll() {
        return getRepository(TransferRepository.class).findAll().stream().map(TransferDto::from).collect(Collectors.toList());
    }

    public void transfer1EurFromAccount1ToAccount2() {
        getRepository(TransferRepository.class).insert(new TransferRecord(generate(), "1", "2", generate(), BigDecimal.ONE, Timestamp.from(Instant.now())));
        ledgerTestData.debitAccount1By1();
        ledgerTestData.creditAccount2By1();
    }
}
