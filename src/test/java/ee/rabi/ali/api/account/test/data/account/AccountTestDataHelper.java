package ee.rabi.ali.api.account.test.data.account;

import ee.rabi.ali.api.account.orm.model.tables.Account;
import ee.rabi.ali.api.account.test.TestTransactionManager;
import io.micronaut.validation.Validated;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Currency;

@Singleton
@Validated
public class AccountTestDataHelper {
    @Inject
    private TestTransactionManager txMgr;

    public void addAccount(@NotBlank String accountId, @NotNull Currency currency) {
        txMgr.getContext()
             .insertInto(Account.ACCOUNT)
             .values("1", "EUR")
             .execute();
    }
}
