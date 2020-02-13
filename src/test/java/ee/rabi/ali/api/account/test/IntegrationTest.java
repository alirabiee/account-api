package ee.rabi.ali.api.account.test;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.BeforeEach;

import javax.inject.Inject;

public abstract class IntegrationTest {
    @Inject
    protected EmbeddedServer server;
    @Inject
    protected TestTransactionManager txMgr;
    @Inject
    @Client("/")
    protected HttpClient client;

    @BeforeEach
    public void truncateData() {
        txMgr.truncateAll();
    }
}
