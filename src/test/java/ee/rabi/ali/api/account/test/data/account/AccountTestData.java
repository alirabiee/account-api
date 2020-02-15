package ee.rabi.ali.api.account.test.data.account;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Currency;

@Singleton
public final class AccountTestData {
    @Inject
    private AccountTestDataHelper dataHelper;

    public void insertAccount1WithEUR() {
        dataHelper.insert("1", Currency.getInstance("EUR"));
    }
}
