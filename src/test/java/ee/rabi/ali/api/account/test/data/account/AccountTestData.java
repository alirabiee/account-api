package ee.rabi.ali.api.account.test.data.account;

import ee.rabi.ali.api.account.app.account.repository.AccountRepository;
import ee.rabi.ali.api.account.orm.model.tables.records.AccountRecord;
import ee.rabi.ali.api.account.test.data.DataHelper;
import ee.rabi.ali.api.account.test.data.balance_snapshot.BalanceSnapshotTestData;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;

@Singleton
public final class AccountTestData extends DataHelper {

    @Inject
    private BalanceSnapshotTestData balanceSnapshotTestData;

    public void insertAccount1WithEurCurrency() {
        insertNewEurAccount("1", BigDecimal.ZERO);
    }

    public void insertAccount1WithEurCurrencyWithInitialBalanceOf1() {
        insertNewEurAccount("1", BigDecimal.ONE);
    }

    public void insertAccount2WithEurCurrency() {
        insertNewEurAccount("2", BigDecimal.ZERO);
    }

    public void insertAccount2WithEurCurrencyWithBalanceOf1() {
        insertNewEurAccount("2", BigDecimal.ONE);
    }

    private void insertNewEurAccount(final String accountId, final BigDecimal initialBalance) {
        new AccountRepository(txMgr).insert(new AccountRecord(accountId, "EUR"));
        balanceSnapshotTestData.insertAccountBalanceSnapshot(accountId, initialBalance);
    }
}
