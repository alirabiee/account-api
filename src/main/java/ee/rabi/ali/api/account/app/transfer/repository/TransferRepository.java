package ee.rabi.ali.api.account.app.transfer.repository;

import ee.rabi.ali.api.account.app.common.repository.Repository;
import ee.rabi.ali.api.account.orm.model.tables.Transfer;
import ee.rabi.ali.api.account.orm.model.tables.records.TransferRecord;
import ee.rabi.ali.api.account.orm.transaction.TransactionManager;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class TransferRepository extends Repository {

    public TransferRepository(final TransactionManager transactionManager) {
        super(transactionManager);
    }

    public List<TransferRecord> findAll() {
        return txMgr.getContext().selectFrom(Transfer.TRANSFER).fetch();
    }
}
