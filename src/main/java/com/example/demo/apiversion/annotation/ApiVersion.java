package com.example.demo.apiversion.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {
    /**
     * 版本号
     * @return
     */
   double value();

}