package com.example.demo.apiversion.annotation;

import com.example.demo.apiversion.webconfig.ApiVersionWebConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(ApiVersionWebConfiguration.class)
public @interface EnableApiVersion {
}
