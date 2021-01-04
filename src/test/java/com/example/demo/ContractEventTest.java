package com.example.demo;

import com.example.demo.event.ContractEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author huteng5
 * @version 1.0
 * @date 2020/12/31 16:02
 */
@SpringBootTest
@Slf4j
public class ContractEventTest implements ApplicationContextAware {

    private ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void testListener(){
        for (int i=0;i<10;i++){
            applicationContext.publishEvent(new ContractEvent(this,"测试"+i));
        }

    }
}
