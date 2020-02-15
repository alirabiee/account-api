package ee.rabi.ali.api.account.test.data.account;

import ee.rabi.ali.api.account.test.data.ledger.LedgerTestData;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Currency;

@Singleton
public final class AccountTestData {
    @Inject
    private AccountTestDataHelper dataHelper;
    @Inject
    private LedgerTestData ledgerTestData;

    public void insertAccount1WithEurCurrency() {
        dataHelper.insert("1", Currency.getInstance("EUR"));
    }

    public void insertAccount1WithEurCurrencyWithBalanceOf1() {
        insertAccount1WithEurCurrency();
        ledgerTestData.creditAccount1By1();
    }

    public void insertAccount2WithEurCurrency() {
        dataHelper.insert("2", Currency.getInstance("EUR"));
    }

    public void insertAccount2WithEurCurrencyWithBalanceOf1() {
        insertAccount2WithEurCurrency();
        ledgerTestData.creditAccount2By1();
    }
}
