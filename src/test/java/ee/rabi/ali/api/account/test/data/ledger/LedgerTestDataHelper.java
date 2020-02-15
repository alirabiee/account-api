package ee.rabi.ali.api.account.test.data.ledger;

import ee.rabi.ali.api.account.orm.IdGenerator;
import ee.rabi.ali.api.account.orm.model.tables.Ledger;
import ee.rabi.ali.api.account.test.TestTransactionManager;
import ee.rabi.ali.api.account.test.data.balance_snapshot.BalanceSnapshotTestDataHelper;
import io.micronaut.validation.Validated;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Singleton
@Validated
public class LedgerTestDataHelper {
    @Inject
    private TestTransactionManager txMgr;
    @Inject
    private BalanceSnapshotTestDataHelper balanceSnapshotTestDataHelper;

    public void insert(@NotBlank String accountId, @NotBlank String transactionId, @NotNull BigDecimal amount) {
        txMgr.getContext()
             .insertInto(Ledger.LEDGER)
             .values(IdGenerator.generate(), accountId, transactionId, amount, Timestamp.from(Instant.now()))
             .execute();
        balanceSnapshotTestDataHelper.update(
                accountId,
                balanceSnapshotTestDataHelper.get(accountId).add(amount));
    }
}
