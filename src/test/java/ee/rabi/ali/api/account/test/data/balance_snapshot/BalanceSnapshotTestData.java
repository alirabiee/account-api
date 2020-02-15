package ee.rabi.ali.api.account.test.data.balance_snapshot;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;

@Singleton
public final class BalanceSnapshotTestData {
    @Inject
    private BalanceSnapshotTestDataHelper dataHelper;

    public BigDecimal getAccount1Balance() {
        return dataHelper.get("1");
    }
}
