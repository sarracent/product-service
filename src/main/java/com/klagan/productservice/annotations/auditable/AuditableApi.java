package com.klagan.productservice.annotations.auditable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuditableApi {
    String description() default "";

    boolean body() default false;

    AuditableParam[] parameters() default {};

    AuditableParamIgnore parameterIgnore() default @AuditableParamIgnore;

    AuditableReturn returnMethod() default @AuditableReturn;
}
