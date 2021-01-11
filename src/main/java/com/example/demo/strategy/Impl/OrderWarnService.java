package com.example.demo.strategy.Impl;

import com.example.demo.domain.WarnRules;
import com.example.demo.event.WarnEvent;
import com.example.demo.strategy.IWarnTrigger;
import com.example.demo.strategy.WarnTriggerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/9 12:19
 */
@Component
@Slf4j
public class OrderWarnService  implements InitializingBean, IWarnTrigger {

    @Resource
    private ApplicationEventPublisher publisher;

    @Override
    public void afterPropertiesSet() throws Exception {
        WarnTriggerFactory.registerService(1,this);
    }

    @Override
    public void triggerRule(WarnRules warnRules) {
        log.info("订单OrderWarnService触发预警规则处理方法......{},------{}",warnRules.getWarnName(),Thread.currentThread().getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publisher.publishEvent(new WarnEvent(this, warnRules));
    }
}
