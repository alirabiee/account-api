package ee.rabi.ali.api.account.test;

import ee.rabi.ali.api.account.orm.config.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

import static ee.rabi.ali.api.account.constant.ApplicationConstant.SQL_DIALECT;

@Slf4j
@Singleton
public class TestTransactionManager {

    private final DSLContext dslContext;

    public TestTransactionManager(final DataSource dataSource) {
        dslContext = buildDslContext(dataSource);
    }

    public void truncateAll() {
        final List<String> truncatedTableNames = new ArrayList<>();
        getContext().transaction(configuration -> {
            final DSLContext context = DSL.using(configuration);
            context.execute("SET REFERENTIAL_INTEGRITY FALSE");
            final Result<Record> showTables = context.resultQuery("SHOW TABLES").fetch();
            for (Record table : showTables) {
                final String tableName = table.get(0, String.class);
                if (!tableName.toLowerCase().contains("flyway")) {
                    getContext().execute("DELETE FROM " + tableName);
                    truncatedTableNames.add(tableName);
                }
            }
            context.execute("SET REFERENTIAL_INTEGRITY TRUE");
        });
        log.info("Truncated tables: {}", truncatedTableNames);
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
