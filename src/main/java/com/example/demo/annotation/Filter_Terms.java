package com.example.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/8 10:51
 */
@FilterMust
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Filter_Terms {
    String[] fieldName() default {""};

    String split() default "";
}
