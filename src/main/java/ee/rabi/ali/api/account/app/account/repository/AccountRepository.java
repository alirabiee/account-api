package ee.rabi.ali.api.account.app.account.repository;

import ee.rabi.ali.api.account.app.common.repository.Repository;
import ee.rabi.ali.api.account.orm.TransactionManager;
import ee.rabi.ali.api.account.orm.model.tables.Account;
import ee.rabi.ali.api.account.orm.model.tables.records.AccountRecord;

import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Singleton
public class AccountRepository extends Repository {

    public AccountRepository(final TransactionManager transactionManager) {
        super(transactionManager);
    }

    public List<AccountRecord> findAll() {
        return txMgr.getContext().selectFrom(Account.ACCOUNT).fetch();
    }

    public Optional<AccountRecord> findById(@NotBlank String accountId) {
        return Optional.ofNullable(txMgr.getContext().selectFrom(Account.ACCOUNT).where(Account.ACCOUNT.ID.eq(accountId)).fetchOne());
    }
}
