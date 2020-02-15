package ee.rabi.ali.api.account.test.data.account;

import ee.rabi.ali.api.account.app.account.repository.AccountRepository;
import ee.rabi.ali.api.account.orm.model.tables.records.AccountRecord;
import ee.rabi.ali.api.account.test.data.TestData;
import ee.rabi.ali.api.account.test.data.balance_snapshot.BalanceSnapshotTestData;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static ee.rabi.ali.api.account.orm.IdGenerator.generate;

@Singleton
public class AccountTestData extends TestData {

    @Inject
    private BalanceSnapshotTestData balanceSnapshotTestData;

    public void insertAccount1WithEurCurrency() {
        insertNewEurAccount("1", BigDecimal.ZERO, "EUR");
    }

    public void insertEurAccountWithInitialBalance(@NotBlank final String accountId, @NotNull final BigDecimal initialBalance) {
        insertNewEurAccount(accountId, initialBalance, "EUR");
    }

    public void insertAccount2WithEurCurrency() {
        insertNewEurAccount("2", BigDecimal.ZERO, "EUR");
    }

    public void insertAccount2WithGbpCurrency() {
        insertNewEurAccount("2", BigDecimal.ZERO, "GBP");
    }

    private void insertNewEurAccount(final String accountId, final BigDecimal initialBalance, final String currency) {
        new AccountRepository(txMgr).insert(new AccountRecord(accountId, currency, generate()));
        balanceSnapshotTestData.insertAccountBalanceSnapshot(accountId, initialBalance);
    }
}
