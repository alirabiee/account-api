package ee.rabi.ali.api.account.orm.transaction.impl;

import ee.rabi.ali.api.account.orm.config.DataSource;
import ee.rabi.ali.api.account.orm.transaction.TransactionManager;
import io.micronaut.runtime.http.scope.RequestScope;
import org.jooq.DSLContext;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;

import static ee.rabi.ali.api.account.constant.ApplicationConstant.SQL_DIALECT;

@RequestScope
public class TransactionManagerImpl implements TransactionManager {

    private final DSLContext dslContext;
    private final DefaultConfiguration defaultConfiguration;

    public TransactionManagerImpl(final DataSource dataSource) {
        defaultConfiguration = buildConfiguration(dataSource);
        dslContext = DSL.using(defaultConfiguration);
    }

    @Override
    public DSLContext getContext() {
        return dslContext;
    }

    private DefaultConfiguration buildConfiguration(final DataSource dataSource) {
        final DataSourceConnectionProvider dataSourceConnectionProvider = new DataSourceConnectionProvider(dataSource.get());
        final DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        defaultConfiguration.setConnectionProvider(dataSourceConnectionProvider);
        defaultConfiguration.setTransactionProvider(new ThreadLocalTransactionProvider(dataSourceConnectionProvider));
        defaultConfiguration.setSQLDialect(SQL_DIALECT);
        defaultConfiguration.setSettings(new Settings().withExecuteWithOptimisticLocking(true));
        return defaultConfiguration;
    }
}
