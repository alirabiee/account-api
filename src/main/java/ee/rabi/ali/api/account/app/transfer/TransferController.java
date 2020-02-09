package ee.rabi.ali.api.account.app.transfer;

import ee.rabi.ali.api.account.orm.TransactionManager;
import ee.rabi.ali.api.account.orm.model.tables.Account;
import ee.rabi.ali.api.account.orm.model.tables.Ledger;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import org.jooq.Record;
import org.jooq.Result;

import java.util.UUID;

@Controller("/transfer")
public class TransferController {

    private final TransactionManager transactionManager;

    public TransferController(final TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        final StringBuilder stringBuilder = new StringBuilder();
        transactionManager.run((context) -> {
            Result<Record> result = context.select().from(Account.ACCOUNT).fetch();
            for (Record record : result) {
                stringBuilder.append(record);
            }
        });
        return stringBuilder.toString();
    }

    @Get("/create")
    @Produces(MediaType.TEXT_PLAIN)
    public String create() {
        transactionManager.run((context) -> {
            final String accountId = UUID.randomUUID().toString();
            context.insertInto(Account.ACCOUNT)
                   .values(accountId)
                   .execute();
            context.insertInto(Ledger.LEDGER)
                   .columns(Ledger.LEDGER.ID, Ledger.LEDGER.ACCOUNT_ID, Ledger.LEDGER.AMOUNT, Ledger.LEDGER.TRANSACTION_ID)
                   .values(UUID.randomUUID().toString(), accountId, 1000L, UUID.randomUUID().toString())
                   .execute();
        });
        return "Done.";
    }
}
