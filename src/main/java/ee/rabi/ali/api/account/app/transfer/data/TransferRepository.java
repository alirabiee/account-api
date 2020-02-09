package ee.rabi.ali.api.account.app.transfer.data;

import ee.rabi.ali.api.account.app.common.data.Repository;
import ee.rabi.ali.api.account.orm.TransactionManager;

import javax.inject.Singleton;

@Singleton
public class TransferRepository extends Repository {

    public TransferRepository(final TransactionManager transactionManager) {
        super(transactionManager);
    }

}
