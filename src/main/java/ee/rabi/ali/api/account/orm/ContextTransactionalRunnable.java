package ee.rabi.ali.api.account.orm;

import org.jooq.DSLContext;

@FunctionalInterface
public interface ContextTransactionalRunnable {

    void run(DSLContext context) throws Throwable;
}
