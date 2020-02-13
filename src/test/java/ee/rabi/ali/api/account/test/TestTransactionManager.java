package ee.rabi.ali.api.account.test;

import ee.rabi.ali.api.account.orm.config.DataSource;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;

import javax.inject.Singleton;

import static ee.rabi.ali.api.account.constant.ApplicationConstant.SQL_DIALECT;

@Singleton
public class TestTransactionManager {

    private final DSLContext dslContext;

    public TestTransactionManager(final DataSource dataSource) {
        dslContext = buildDslContext(dataSource);
    }

    public void truncateAll() {
        getContext().execute("SET REFERENTIAL_INTEGRITY FALSE");
        final Result<Record> showTables = getContext().resultQuery("SHOW TABLES").fetch();
        for (Record table : showTables) {
            final String tableName = table.get(0, String.class);
            if (!tableName.toLowerCase().contains("flyway")) {
                getContext().execute("DELETE FROM " + tableName);
            }
        }
        getContext().execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    public DSLContext getContext() {
        return dslContext;
    }

    private DSLContext buildDslContext(final DataSource dataSource) {
        final DataSourceConnectionProvider dataSourceConnectionProvider = new DataSourceConnectionProvider(dataSource.get());
        final DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        defaultConfiguration.setConnectionProvider(dataSourceConnectionProvider);
        defaultConfiguration.setTransactionProvider(new ThreadLocalTransactionProvider(dataSourceConnectionProvider));
        defaultConfiguration.setSQLDialect(SQL_DIALECT);
        defaultConfiguration.setSettings(new Settings().withExecuteWithOptimisticLocking(true));
        return DSL.using(defaultConfiguration);
    }
}
