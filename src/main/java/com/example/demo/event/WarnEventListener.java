package com.example.demo.event;

import com.example.demo.domain.WarnRules;
import com.example.demo.domain.WarnSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/8 21:48
 */
@Component
@Slf4j
public class WarnEventListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "multipleMessageServicePool")
    private Executor executor;

    @Async("multiplePool")
    @EventListener
    public void onContractEvent(WarnEvent event) {
        WarnRules warnRules = event.getWarnRules();
        logger.info("接到预警触发--->{}，发送消息data={}", Thread.currentThread().getName(), event.getWarnRules());

        //查找订阅此预警规则的有效的列表
        List<WarnSubscribe> warnSubscribeList = new ArrayList<>();
        WarnSubscribe warnSubscribe = new WarnSubscribe();
        warnSubscribe.setId(1L);
        warnSubscribe.setMail("test@163.com");
        warnSubscribe.setPhone("1356333223");
        warnSubscribe.setWarnRulesId(1L);
        warnSubscribeList.add(warnSubscribe);
        warnSubscribeList.forEach(item -> {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    log.info("准备发送消息给订阅者..." + Thread.currentThread().getName()+","+item);
                    if(item.getWarnRulesId()==warnRules.getId()){
                        //发送信息给订阅人
                        log.info("发送信息给订阅人--->{}---->{}",Thread.currentThread().getName(),item);
                    }
                }
            });
        });

    }
}
