package com.example.demo.strategy.Impl;

import com.example.demo.domain.WarnRules;
import com.example.demo.enums.WarnTargetEnum;
import com.example.demo.event.WarnEvent;
import com.example.demo.strategy.AbstractWarnTrigger;
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
 * @date 2021/1/9 12:53
 */
@Component
@Slf4j
public class StockWarnService extends AbstractWarnTrigger implements InitializingBean, IWarnTrigger {

    @Override
    public void afterPropertiesSet() throws Exception {
        WarnTriggerFactory.registerService(WarnTargetEnum.STOCK_TARGET.getCode(),this);
    }

    @Override
    public void triggerRule(WarnRules warnRules) {
        log.info("库存StockWarnService触发预警规则处理方法...{},---{}",Thread.currentThread().getName(),warnRules.getWarnName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publishEvent(new WarnEvent(this, warnRules));
    }
}
