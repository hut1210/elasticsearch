package com.example.demo.strategy;

import com.example.demo.domain.WarnRules;
import com.example.demo.event.WarnEvent;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.Resource;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/9 12:24
 */
public abstract class AbstractWarnTrigger implements IWarnTrigger{

    @Resource
    private ApplicationEventPublisher publisher;

    /**
     * 发布消息
     * @param event
     */
    public void publishEvent(WarnEvent event){
        publisher.publishEvent(event);
    }
}
