package ee.rabi.ali.api.account.test.data.ledger;

import ee.rabi.ali.api.account.orm.IdGenerator;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;

@Singleton
public final class LedgerTestData {
    @Inject
    private LedgerTestDataHelper dataHelper;

    public void creditAccount1By1() {
        dataHelper.insert("1", IdGenerator.generate(), BigDecimal.ONE);
    }

    public void creditAccount2By1() {
        dataHelper.insert("2", IdGenerator.generate(), BigDecimal.ONE);
    }
}
