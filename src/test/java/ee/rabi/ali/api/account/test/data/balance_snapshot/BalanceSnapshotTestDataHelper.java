package ee.rabi.ali.api.account.test.data.balance_snapshot;

import ee.rabi.ali.api.account.orm.model.tables.BalanceSnapshot;
import ee.rabi.ali.api.account.test.TestTransactionManager;
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
public class BalanceSnapshotTestDataHelper {
    @Inject
    private TestTransactionManager txMgr;

    public void insert(@NotBlank String accountId, @NotNull BigDecimal balance) {
        txMgr.getContext()
             .insertInto(BalanceSnapshot.BALANCE_SNAPSHOT)
             .values(accountId, balance, Timestamp.from(Instant.now()), 0)
             .execute();
    }

    public BigDecimal get(@NotBlank String accountId) {
        return txMgr
                .getContext()
                .selectFrom(BalanceSnapshot.BALANCE_SNAPSHOT)
                .where(BalanceSnapshot.BALANCE_SNAPSHOT.ACCOUNT_ID.eq(accountId))
                .fetchOne()
                .getBalance();
    }

    public void update(@NotBlank String accountId, @NotNull BigDecimal balance) {
        txMgr.getContext()
             .update(BalanceSnapshot.BALANCE_SNAPSHOT)
             .set(BalanceSnapshot.BALANCE_SNAPSHOT.BALANCE, balance)
             .set(BalanceSnapshot.BALANCE_SNAPSHOT.UPDATED_AT, Timestamp.from(Instant.now()))
             .where(BalanceSnapshot.BALANCE_SNAPSHOT.ACCOUNT_ID.eq(accountId))
             .execute();
    }
}
