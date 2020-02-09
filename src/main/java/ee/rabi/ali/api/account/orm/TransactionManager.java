package ee.rabi.ali.api.account.orm;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor
public class TransactionManager {

    private final TransactionAdvice transactionAdvice;

    public DSLContext getContext() {
        return transactionAdvice.currentContext.get();
    }
}
