package com.example.demo.event;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author huteng5
 * @version 1.0
 * @date 2020/12/31 15:56
 */
@Component
@Slf4j
public class ContractEventListener {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Async("multiplePool")
    @EventListener
    public void onContractEvent(ContractEvent event){
        logger.info("发布data={}",event.getData());
    }
}
