package com.example.demo.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author huteng5
 * @version 1.0
 * @date 2020/12/30 20:43
 */
//@Component
public class DataSourceShow implements ApplicationContextAware {
    private ApplicationContext applicationContext = null;

    /**
     * Spring容器会自动调用这个方法，注入到Spring IOC容器中
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        System.out.println("--------------------------------");
        System.out.println(dataSource.getClass().getName());
        System.out.println("--------------------------------");
    }
}
