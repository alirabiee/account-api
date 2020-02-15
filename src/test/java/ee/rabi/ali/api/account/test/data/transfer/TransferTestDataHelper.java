package ee.rabi.ali.api.account.test.data.transfer;

import ee.rabi.ali.api.account.orm.model.tables.Transfer;
import ee.rabi.ali.api.account.test.TestTransactionManager;
import ee.rabi.ali.api.account.test.data.ledger.LedgerTestDataHelper;
import io.micronaut.validation.Validated;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static ee.rabi.ali.api.account.orm.IdGenerator.generate;

@Singleton
@Validated
public class TransferTestDataHelper {
    @Inject
    private TestTransactionManager txMgr;
    @Inject
    private LedgerTestDataHelper ledgerTestDataHelper;

    public void insert(@NotBlank String fromAccountId, @NotBlank String toAccountId, @NotNull @Min(1) BigDecimal amount) {
        final String id = generate();
        txMgr.getContext()
             .insertInto(Transfer.TRANSFER)
             .values(id, fromAccountId, toAccountId, amount, Timestamp.from(Instant.now()))
             .execute();
        ledgerTestDataHelper.insert(fromAccountId, id, amount.negate());
        ledgerTestDataHelper.insert(toAccountId, id, amount);
    }
}
