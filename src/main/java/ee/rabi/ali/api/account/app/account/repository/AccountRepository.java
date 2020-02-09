package ee.rabi.ali.api.account.app.account.repository;

import ee.rabi.ali.api.account.app.common.repository.Repository;
import ee.rabi.ali.api.account.orm.TransactionManager;
import ee.rabi.ali.api.account.orm.model.tables.Account;
import ee.rabi.ali.api.account.orm.model.tables.records.AccountRecord;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class AccountRepository extends Repository {

    public AccountRepository(final TransactionManager transactionManager) {
        super(transactionManager);
    }

    public List<AccountRecord> findAll() {
        return txMgr.getContext().selectFrom(Account.ACCOUNT).fetch();
    }
}
