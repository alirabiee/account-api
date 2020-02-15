package ee.rabi.ali.api.account.test.data;

import ee.rabi.ali.api.account.app.common.repository.Repository;
import ee.rabi.ali.api.account.orm.config.DataSource;
import ee.rabi.ali.api.account.orm.transaction.TransactionManager;
import ee.rabi.ali.api.account.orm.transaction.impl.TransactionManagerImpl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public abstract class DataHelper {

    protected TransactionManager txMgr;
    @Inject
    private DataSource dataSource;

    @PostConstruct
    public void construct() {
        txMgr = new TransactionManagerImpl(dataSource);
    }

    protected <T extends Repository> T getRepository(Class<T> c) {
        try {
            return c.getConstructor(TransactionManager.class).newInstance(txMgr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
