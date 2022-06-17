package com.example.demo.bean;

import com.google.common.collect.Lists;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/16 17:08
 */
public class BeanUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        BeanUtils.applicationContext = applicationContext; // NOSONAR
    }

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        Map<String, T> map = applicationContext.getBeansOfType(clazz);
        if(CollectionUtils.isEmpty(map)){
            return null;
        }
        for (T value : map.values()) {
            return value;
        }
        return null;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> getBeanMap(Class<T> clazz) {
        checkApplicationContext();
        Map<String, T> map = applicationContext.getBeansOfType(clazz);
        if(CollectionUtils.isEmpty(map)){
            return null;
        }
        return map;
    }

    public static <T> List<T> getBeanList(Class<T> clazz) {
        checkApplicationContext();
        Map<String, T> map = applicationContext.getBeansOfType(clazz);
        if(CollectionUtils.isEmpty(map)){
            return null;
        }
        return Lists.newArrayList(map.values());
    }

    /**
     * 清除applicationContext静态变量.
     */
    public static void cleanApplicationContext() {
        applicationContext = null;
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
        }
    }
}
