package com.example.demo.apiversion.webconfig;

import com.example.demo.apiversion.mappinghandler.ApiVersionHandlerMapping;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 不添加@Configuration注解，避免自动开启
 *
 * 配置使用自定义的映射处理器
 * 注意：这个类不同的SpringBoot版本 可能配置方式会有不同
 */
public class ApiVersionWebConfiguration implements WebMvcRegistrations {

    /**
    * 使用自定义的ApiVersion映射处理器代替MVC默认的映射处理器
    * @return
    */
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiVersionHandlerMapping();
    }
}
