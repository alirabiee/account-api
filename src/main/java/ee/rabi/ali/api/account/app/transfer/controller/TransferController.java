package ee.rabi.ali.api.account.app.transfer.controller;

import ee.rabi.ali.api.account.app.transfer.controller.model.CreateTransferRequest;
import ee.rabi.ali.api.account.app.transfer.controller.model.CreateTransferResponse;
import ee.rabi.ali.api.account.app.transfer.service.TransferService;
import ee.rabi.ali.api.account.orm.TransactionManager;
import ee.rabi.ali.api.account.orm.model.tables.Account;
import ee.rabi.ali.api.account.orm.model.tables.Ledger;
import ee.rabi.ali.api.account.orm.model.tables.Transfer;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import lombok.RequiredArgsConstructor;
import org.jooq.Record;
import org.jooq.Result;

import javax.validation.Valid;
import java.util.UUID;

@Controller("/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransactionManager transactionManager;
    private final TransferService transferService;

    @Put
    public CreateTransferResponse create(@Valid @Body CreateTransferRequest createTransferRequest) {
        return CreateTransferResponse.from(transferService.create(createTransferRequest.toCreateTransferDto()));
    }

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        final StringBuilder stringBuilder = new StringBuilder();
        transactionManager.run((context) -> {
            Result<Record> result = context.select().from(Transfer.TRANSFER).fetch();
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
