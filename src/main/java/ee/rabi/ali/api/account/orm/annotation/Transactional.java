package ee.rabi.ali.api.account.orm.annotation;

import ee.rabi.ali.api.account.orm.TransactionAdvice;
import io.micronaut.aop.Around;
import io.micronaut.context.annotation.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Type(TransactionAdvice.class)
@Around
public @interface Transactional {
}