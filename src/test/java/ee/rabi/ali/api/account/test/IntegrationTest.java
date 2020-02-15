package ee.rabi.ali.api.account.test;

import ee.rabi.ali.api.account.orm.config.DataSource;
import ee.rabi.ali.api.account.orm.transaction.impl.TestTransactionManagerImpl;
import ee.rabi.ali.api.account.test.data.TestDataUtil;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.BeforeEach;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public abstract class IntegrationTest {
    @Inject
    protected EmbeddedServer server;
    protected TestTransactionManagerImpl txMgr;
    @Inject
    @Client("/")
    protected HttpClient client;
    @Inject
    protected TestDataUtil dataUtil;
    @Inject
    private DataSource dataSource;

    @PostConstruct
    public void construct() {
        txMgr = new TestTransactionManagerImpl(dataSource);
    }

    @BeforeEach
    public void truncateData() {
        ((TestTransactionManagerImpl) txMgr).truncateAll();
    }
}
