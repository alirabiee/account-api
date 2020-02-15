package ee.rabi.ali.api.account.test.data.ledger;

import ee.rabi.ali.api.account.app.ledger.repository.LedgerRepository;
import ee.rabi.ali.api.account.app.ledger.service.model.LedgerDto;
import ee.rabi.ali.api.account.orm.IdGenerator;
import ee.rabi.ali.api.account.orm.model.tables.records.LedgerRecord;
import ee.rabi.ali.api.account.test.data.TestData;
import ee.rabi.ali.api.account.test.data.balance_snapshot.BalanceSnapshotTestData;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class LedgerTestData extends TestData {

    @Inject
    private BalanceSnapshotTestData balanceSnapshotTestData;

    public void creditAccount1By1() {
        getRepository(LedgerRepository.class)
                .insert(new LedgerRecord(
                        IdGenerator.generate(),
                        "1",
                        IdGenerator.generate(),
                        BigDecimal.ONE,
                        Timestamp.from(Instant.now())));
        balanceSnapshotTestData.updateAccount1BalanceSnapshot(BigDecimal.ONE);
    }

    public void debitAccount1By1() {
        getRepository(LedgerRepository.class)
                .insert(new LedgerRecord(
                        IdGenerator.generate(),
                        "1",
                        IdGenerator.generate(),
                        BigDecimal.ONE.negate(),
                        Timestamp.from(Instant.now())));
        balanceSnapshotTestData.updateAccount1BalanceSnapshot(BigDecimal.ONE.negate());
    }

    public void creditAccount2By1() {
        getRepository(LedgerRepository.class)
                .insert(new LedgerRecord(
                        IdGenerator.generate(),
                        "2",
                        IdGenerator.generate(),
                        BigDecimal.ONE,
                        Timestamp.from(Instant.now())));
        balanceSnapshotTestData.updateAccount1BalanceSnapshot(BigDecimal.ONE);
    }

    public List<LedgerDto> findAll() {
        return getRepository(LedgerRepository.class)
                .findAll()
                .stream()
                .map(LedgerDto::from)
                .collect(Collectors.toList());
    }
}
