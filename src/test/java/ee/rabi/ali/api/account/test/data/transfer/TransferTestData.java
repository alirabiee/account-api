package ee.rabi.ali.api.account.test.data.transfer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;

@Singleton
public class TransferTestData {
    @Inject
    private TransferTestDataHelper transferTestDataHelper;

    public void transfer1EurFromAccount1ToAccount2() {
        transferTestDataHelper.insert("1", "2", BigDecimal.ONE);
    }
}
