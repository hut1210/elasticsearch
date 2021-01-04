package com.example.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/4 14:37
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Factor {
    Factor.Type type() default Factor.Type.NONE;

    public static enum Type {
        NONE,
        MUST,
        MUSTNOT,
        SHOULD,
        FILTERMUST,
        FILTERMUSTNOT,
        FILTERSHOULD;

        private Type() {
        }
    }
}
