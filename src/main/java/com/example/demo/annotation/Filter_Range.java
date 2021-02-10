package com.example.demo.annotation;

import com.example.demo.enums.FormatEnum;
import com.example.demo.enums.RangeTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/9 10:37
 */
@FilterMust
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Filter_Range {
    String fieldName() default "";

    RangeTypeEnum type();

    FormatEnum format() default FormatEnum.NONE;
}
