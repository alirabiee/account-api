package ee.rabi.ali.api.account.orm;

import ee.rabi.ali.api.account.orm.annotation.Transactional;
import ee.rabi.ali.api.account.orm.config.DataSource;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;

import javax.inject.Singleton;
import java.util.concurrent.atomic.AtomicReference;

import static ee.rabi.ali.api.account.constant.ApplicationConstant.SQL_DIALECT;

@Singleton
public class TransactionAdvice implements MethodInterceptor<Object, Object> {

    private final DefaultConfiguration defaultConfiguration;
    ThreadLocal<DSLContext> currentContext = new ThreadLocal<>();

    public TransactionAdvice(final DataSource dataSource) {
        final DataSourceConnectionProvider dataSourceConnectionProvider = new DataSourceConnectionProvider(dataSource.get());
        this.defaultConfiguration = new DefaultConfiguration();
        defaultConfiguration.setConnectionProvider(dataSourceConnectionProvider);
        defaultConfiguration.setTransactionProvider(new ThreadLocalTransactionProvider(dataSourceConnectionProvider));
        defaultConfiguration.setSQLDialect(SQL_DIALECT);
    }

    @Override
    public Object intercept(final MethodInvocationContext<Object, Object> context) {
        final Transactional annotation = context.getTargetMethod().getAnnotation(Transactional.class);
        final AtomicReference<Object> result = new AtomicReference<>();
        if (annotation != null) {
            if (currentContext.get() == null) {
                currentContext.set(DSL.using(defaultConfiguration));
            }
            currentContext.get().transaction(() -> result.set(context.proceed()));
        } else {
            result.set(context.proceed());
        }
        return result.get();
    }
}
