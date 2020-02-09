package ee.rabi.ali.api.account.orm;

import ee.rabi.ali.api.account.orm.config.DataSource;
import io.micronaut.runtime.http.scope.RequestScope;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;

import static ee.rabi.ali.api.account.constant.ApplicationConstant.SQL_DIALECT;

@RequestScope
public class TransactionManager {

    private final DSLContext dslContext;

    public TransactionManager(final DataSource dataSource) {
        dslContext = buildDslContext(dataSource);
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
        return DSL.using(defaultConfiguration);
    }
}
