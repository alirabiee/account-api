package ee.rabi.ali.api.account.orm.aspect;

import ee.rabi.ali.api.account.orm.transaction.TransactionManager;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
@RequiredArgsConstructor
public class TransactionAdvice implements MethodInterceptor<Object, Object> {

    private final TransactionManager txMgr;

    @Override
    public Object intercept(final MethodInvocationContext<Object, Object> context) {
        final Transactional annotation = context.getTargetMethod().getAnnotation(Transactional.class);
        final AtomicReference<Object> result = new AtomicReference<>();
        if (annotation != null) {
            txMgr.getContext().transaction(() -> result.set(context.proceed()));
        } else {
            result.set(context.proceed());
        }
        return result.get();
    }
}
