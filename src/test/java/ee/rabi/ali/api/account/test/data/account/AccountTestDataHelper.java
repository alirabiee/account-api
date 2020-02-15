package ee.rabi.ali.api.account.test.data.account;

import ee.rabi.ali.api.account.orm.model.tables.Account;
import ee.rabi.ali.api.account.test.TestTransactionManager;
import ee.rabi.ali.api.account.test.data.balance_snapshot.BalanceSnapshotTestDataHelper;
import io.micronaut.validation.Validated;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;

@Singleton
@Validated
public class AccountTestDataHelper {
    @Inject
    private TestTransactionManager txMgr;
    @Inject
    private BalanceSnapshotTestDataHelper balanceSnapshotTestDataHelper;

    public void insert(@NotBlank String accountId, @NotNull Currency currency) {
        txMgr.getContext()
             .insertInto(Account.ACCOUNT)
             .values(accountId, currency.getCurrencyCode())
             .execute();
        balanceSnapshotTestDataHelper.insert(accountId, BigDecimal.ZERO);
    }
}
