package com.klagan.productservice.annotations.auditable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface AuditableReturn {
    Class<?> type() default Void.class;

    boolean isList() default false;
}
