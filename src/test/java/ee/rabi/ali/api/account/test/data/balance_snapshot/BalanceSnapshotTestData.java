package ee.rabi.ali.api.account.test.data.balance_snapshot;

import ee.rabi.ali.api.account.app.balance_snapshot.repository.BalanceSnapshotRepository;
import ee.rabi.ali.api.account.app.balance_snapshot.service.model.BalanceSnapshotDto;
import ee.rabi.ali.api.account.orm.model.tables.records.BalanceSnapshotRecord;
import ee.rabi.ali.api.account.test.data.DataHelper;

import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Singleton
public class BalanceSnapshotTestData extends DataHelper {

    public void insertAccountBalanceSnapshot(@NotBlank String accountId, @NotNull BigDecimal initialBalance) {
        insertBalanceSnapshot(accountId, initialBalance);
    }

    public BalanceSnapshotDto getAccount1BalanceSnapshot() {
        return getBalanceSnapshotDto("1");
    }

    public BalanceSnapshotDto getAccount2BalanceSnapshot() {
        return getBalanceSnapshotDto("2");
    }

    public void updateAccount1BalanceSnapshot(@NotNull BigDecimal delta) {
        updateBalance(delta, "1");
    }

    private void updateBalance(final BigDecimal delta, final String accountId) {
        final BalanceSnapshotRecord record = new BalanceSnapshotRepository(txMgr)
                .findByAccountId(accountId)
                .orElseThrow();
        record.setBalance(record.getBalance().add(delta));
        record.store();
    }

    private BalanceSnapshotDto getBalanceSnapshotDto(final String accountId) {
        return new BalanceSnapshotRepository(txMgr)
                .findByAccountId(accountId)
                .map(BalanceSnapshotDto::from)
                .orElseThrow();
    }

    private void insertBalanceSnapshot(final String accountId, final BigDecimal balance) {
        new BalanceSnapshotRepository(txMgr)
                .insert(new BalanceSnapshotRecord(accountId, balance, Timestamp.from(Instant.now()), 0));
    }
}
