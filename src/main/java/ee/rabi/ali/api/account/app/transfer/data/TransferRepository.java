package ee.rabi.ali.api.account.app.transfer.data;

import ee.rabi.ali.api.account.app.common.data.Repository;
import ee.rabi.ali.api.account.orm.TransactionManager;
import ee.rabi.ali.api.account.orm.model.tables.Transfer;
import ee.rabi.ali.api.account.orm.model.tables.records.TransferRecord;
import org.jooq.Result;

import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class TransferRepository extends Repository {

    public TransferRepository(final TransactionManager transactionManager) {
        super(transactionManager);
    }

    public List<TransferRecord> findAll() {
        final AtomicReference<Result<TransferRecord>> result = new AtomicReference<Result<TransferRecord>>();

        transactionManager.run(context -> {
            result.set(context.selectFrom(Transfer.TRANSFER).fetch());
        });

        return result.get();
    }

}
