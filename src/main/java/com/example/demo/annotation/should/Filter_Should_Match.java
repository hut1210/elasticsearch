package com.example.demo.annotation.should;

import com.example.demo.annotation.FilterShould;
import com.example.demo.enums.MatchOperatorEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/4 14:07
 */
@FilterShould
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Filter_Should_Match {
    String fieldName() default "";

    MatchOperatorEnum operator() default MatchOperatorEnum.OR;
}
