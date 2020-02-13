package ee.rabi.ali.api.account.test.data.account;

import ee.rabi.ali.api.account.orm.model.tables.Account;
import ee.rabi.ali.api.account.test.TestTransactionManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class AccountTestData {
    @Inject
    private TestTransactionManager txMgr;

    public void addAccount1WithEUR() {
        txMgr.getContext()
             .insertInto(Account.ACCOUNT)
             .values("1", "EUR")
             .execute();
    }
}
