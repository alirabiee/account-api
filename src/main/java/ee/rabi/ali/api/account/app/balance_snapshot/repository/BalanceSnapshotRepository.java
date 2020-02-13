package ee.rabi.ali.api.account.app.balance_snapshot.repository;

import ee.rabi.ali.api.account.app.common.repository.Repository;
import ee.rabi.ali.api.account.orm.model.tables.BalanceSnapshot;
import ee.rabi.ali.api.account.orm.model.tables.records.BalanceSnapshotRecord;
import ee.rabi.ali.api.account.orm.transaction.TransactionManager;

import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Singleton
public class BalanceSnapshotRepository extends Repository {

    public BalanceSnapshotRepository(final TransactionManager transactionManager) {
        super(transactionManager);
    }

    public Optional<BalanceSnapshotRecord> findByAccountId(@NotBlank final String accountId) {
        return Optional.ofNullable(txMgr
                .getContext()
                .selectFrom(BalanceSnapshot.BALANCE_SNAPSHOT)
                .where(BalanceSnapshot.BALANCE_SNAPSHOT.ACCOUNT_ID.eq(accountId))
                .fetchOne());
    }
}
