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
        insertNewEurAccount("1", BigDecimal.ZERO, "EUR");
    }

    public void insertAccount1WithEurCurrencyWithInitialBalanceOf1() {
        insertNewEurAccount("1", BigDecimal.ONE, "EUR");
    }

    public void insertAccount2WithEurCurrency() {
        insertNewEurAccount("2", BigDecimal.ZERO, "EUR");
    }

    public void insertAccount2WithGbpCurrency() {
        insertNewEurAccount("2", BigDecimal.ZERO, "GBP");
    }

    public void insertAccount2WithEurCurrencyWithInitialBalanceOf1() {
        insertNewEurAccount("2", BigDecimal.ONE, "EUR");
    }

    private void insertNewEurAccount(final String accountId, final BigDecimal initialBalance, final String currency) {
        new AccountRepository(txMgr).insert(new AccountRecord(accountId, currency));
        balanceSnapshotTestData.insertAccountBalanceSnapshot(accountId, initialBalance);
    }
}
