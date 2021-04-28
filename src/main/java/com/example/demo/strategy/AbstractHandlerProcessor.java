package com.example.demo.strategy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/22 11:47
 */
@Component
public class AbstractHandlerProcessor implements ApplicationContextAware {

    private ApplicationContext context;

    private Map<String, AbstractHandler> processorMap = new HashMap<>();

    public AbstractHandler choose(String type) throws Exception {
        AbstractHandler handler = processorMap.get(type);
        if(null == handler) {
            throw new Exception("不支持的type类型，联系管理员。。。");
        }
        return handler;
    }

    @PostConstruct
    public void register() {
        Map<String, AbstractHandler> handlerMap = context.getBeansOfType(AbstractHandler.class);
        for(AbstractHandler handler : handlerMap.values()) {
            processorMap.put(handler.type(),handler);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
    }
}
