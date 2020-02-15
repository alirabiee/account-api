package ee.rabi.ali.api.account.test.data.transfer;

import ee.rabi.ali.api.account.app.transfer.repository.TransferRepository;
import ee.rabi.ali.api.account.orm.IdGenerator;
import ee.rabi.ali.api.account.orm.model.tables.records.TransferRecord;
import ee.rabi.ali.api.account.test.data.DataHelper;
import ee.rabi.ali.api.account.test.data.ledger.LedgerTestData;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Singleton
public class TransferTestData extends DataHelper {

    @Inject
    private LedgerTestData ledgerTestData;

    public void transfer1EurFromAccount1ToAccount2() {
        getRepository(TransferRepository.class).insert(new TransferRecord(IdGenerator.generate(), "1", "2", BigDecimal.ONE, Timestamp.from(Instant.now())));
        ledgerTestData.debitAccount1By1();
        ledgerTestData.creditAccount2By1();
    }
}
