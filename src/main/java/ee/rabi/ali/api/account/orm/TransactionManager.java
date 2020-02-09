package ee.rabi.ali.api.account.orm;

import ee.rabi.ali.api.account.orm.config.DataSource;
import io.micronaut.runtime.context.scope.ThreadLocal;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;

import static ee.rabi.ali.api.account.constant.ApplicationConstant.SQL_DIALECT;

@ThreadLocal
public class TransactionManager {

    private final DefaultConfiguration defaultConfiguration;

    public TransactionManager(final DataSource dataSource) {
        final DataSourceConnectionProvider dataSourceConnectionProvider = new DataSourceConnectionProvider(dataSource.get());
        this.defaultConfiguration = new DefaultConfiguration();
        defaultConfiguration.setConnectionProvider(dataSourceConnectionProvider);
        defaultConfiguration.setTransactionProvider(new ThreadLocalTransactionProvider(dataSourceConnectionProvider));
        defaultConfiguration.setSQLDialect(SQL_DIALECT);
    }

    public void run(ContextTransactionalRunnable runnable) {
        final DSLContext context = DSL.using(defaultConfiguration);
        context.transaction(() -> runnable.run(context));
    }
}
