package com.example.demo.apiversion.mappinghandler;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 接口多版本处理
 * @author admin
 *
 */
public class ApiVesrsionCondition implements RequestCondition<ApiVesrsionCondition> {

    
	/**
	 * Api 版本参数名称
	 */
    public final static String VERSION_PARAM_NAME = "api-version";
    
    /**
     * 路径中版本的前缀， 这里用 /v[1-9]/的形式
     */
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("v(\\d+)\\.(\\d+)");
    
    /**
     * 默认版本号（未添加注解的版本号）
     */
    public static final ApiVesrsionCondition DEFAULT_API_VERSION = new ApiVesrsionCondition(1.0);

    private final double apiVersion;
     
    public ApiVesrsionCondition(double apiVersion){
        this.apiVersion = apiVersion;
    }

    @Override
    public ApiVesrsionCondition combine(ApiVesrsionCondition other) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiVesrsionCondition(other.getApiVersion());
    }

    /**
     * 版本匹配
     * @param request
     * @return
     */
    @Override
    public ApiVesrsionCondition getMatchingCondition(HttpServletRequest request) {
        String v = getRequestVersion(request);
        Matcher m = VERSION_PREFIX_PATTERN.matcher(v + ".999");
        if(m.find()){
            v = m.group(1) + "." + m.group(2);
            // 如果请求的版本号大于配置版本号， 则满足
            if(Double.parseDouble(v) >= this.apiVersion) {
                return this;
            }
        }
        return null;
    }

    @Override
    public int compareTo(ApiVesrsionCondition other, HttpServletRequest request) {
        // 优先匹配最新的版本号
        return Double.compare(other.getApiVersion(), this.apiVersion);
    }
 
    public double getApiVersion() {
        return apiVersion;
    }

    /**
     * 获取请求的版本号
     * @param request
     * @return
     */
    public String getRequestVersion(HttpServletRequest request){
        String paramVersion = request.getHeader(VERSION_PARAM_NAME);
        if(paramVersion==null){
            paramVersion = request.getParameter(VERSION_PARAM_NAME);
        }
        //未找到版本号，则默认最低版本1.0
        if(StringUtils.isEmpty(paramVersion)){
            paramVersion = "v1.0";
        }else if(!paramVersion.startsWith("v")){
            paramVersion = "v" + paramVersion;
        }
        return paramVersion;
    }
 
}