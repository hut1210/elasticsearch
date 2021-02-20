package com.example.demo.annotation.mustnot;

import com.example.demo.annotation.FilterMustNot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/20 15:51
 */
@FilterMustNot
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Filter_Not_Term {
    String[] fieldName() default {""};
}
