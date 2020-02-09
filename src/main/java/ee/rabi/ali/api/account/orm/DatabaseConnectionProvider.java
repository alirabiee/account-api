package ee.rabi.ali.api.account.orm;

import ee.rabi.ali.api.account.orm.config.DataSource;
import org.jooq.impl.DataSourceConnectionProvider;

import javax.inject.Singleton;
import java.sql.Connection;

@Singleton
public class DatabaseConnectionProvider {

    private final DataSourceConnectionProvider dataSourceConnectionProvider;

    public DatabaseConnectionProvider(final DataSource dataSource) {
        this.dataSourceConnectionProvider = new DataSourceConnectionProvider(dataSource.get());
    }

    public Connection get() {
        return dataSourceConnectionProvider.acquire();
    }
}
