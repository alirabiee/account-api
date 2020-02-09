package ee.rabi.ali.api.account.app.account.data;

import ee.rabi.ali.api.account.app.common.data.Repository;
import ee.rabi.ali.api.account.orm.TransactionManager;

import javax.inject.Singleton;

@Singleton
public class AccountRepository extends Repository {

    public AccountRepository(final TransactionManager transactionManager) {
        super(transactionManager);
    }
}
