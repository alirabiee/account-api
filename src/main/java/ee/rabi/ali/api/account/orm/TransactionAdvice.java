package ee.rabi.ali.api.account.orm;

import ee.rabi.ali.api.account.orm.annotation.Transactional;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
@RequiredArgsConstructor
public class TransactionAdvice implements MethodInterceptor<Object, Object> {

    private final TransactionManager transactionManager;

    @Override
    public Object intercept(final MethodInvocationContext<Object, Object> context) {
        final Transactional annotation = context.getTargetMethod().getAnnotation(Transactional.class);
        final AtomicReference<Object> result = new AtomicReference<>();
        if (annotation != null) {
            transactionManager.getContext().transaction(() -> result.set(context.proceed()));
        } else {
            result.set(context.proceed());
        }
        return result.get();
    }
}
