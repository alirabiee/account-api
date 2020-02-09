package ee.rabi.ali.api.account.orm;

import ee.rabi.ali.api.account.orm.exception.NonTransactionalContextException;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor
public class TransactionManager {

    private final TransactionAdvice transactionAdvice;

    public DSLContext getContext() {
        final DSLContext dslContext = transactionAdvice.currentContext.get();
        if (dslContext == null) {
            throw new NonTransactionalContextException();
        }
        return dslContext;
    }
}
