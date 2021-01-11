package com.example.demo.task;

import com.example.demo.domain.WarnRules;
import com.example.demo.event.WarnEvent;
import com.example.demo.strategy.IWarnTrigger;
import com.example.demo.strategy.WarnTriggerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/9 12:12
 */
@EnableScheduling
@Component
@Slf4j
public class WarnTask {

    @Resource(name = "multiplePool")
    private Executor executor;

    @Async("multiplePool")
    //@Scheduled(cron = "0/10 * * * * ?")
    @Scheduled(cron = "${scheduled.cron.style}")
    public void test() {
        log.info("预警定时任务执行中......" + Thread.currentThread().getName());
        //获取预警规则为有效的列表
        List<WarnRules> warnRulesList = new ArrayList<>();
        /*WarnRules warnRules = new WarnRules();
        warnRules.setId(1L);
        warnRules.setWarnName("test1");
        warnRules.setWarnTarget(1);
        warnRulesList.add(warnRules);

        WarnRules warnRules1 = new WarnRules();
        warnRules1.setId(2L);
        warnRules1.setWarnName("test2");
        warnRules1.setWarnTarget(2);
        warnRulesList.add(warnRules1);

        WarnRules warnRules2 = new WarnRules();
        warnRules2.setId(3L);
        warnRules2.setWarnName("test3");
        warnRules2.setWarnTarget(3);
        warnRulesList.add(warnRules2);*/
        for (int i = 1; i < 4; i++) {
            WarnRules warnRules = new WarnRules();
            warnRules.setId(Long.valueOf(""+i));
            warnRules.setWarnName("test"+i);
            warnRules.setWarnTarget(i);
            warnRulesList.add(warnRules);
        }

        warnRulesList.forEach(item -> {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        IWarnTrigger warnTrigger = WarnTriggerFactory.getWarnTriggerByTarget(item.getWarnTarget());
                        log.info(item.getWarnTarget() + "  " + item.getWarnName());
                        if (warnTrigger != null) {
                            warnTrigger.triggerRule(item);
                            /*Thread.sleep(500);
                            log.info("触发预警规则！！！" + Thread.currentThread().getName());
                            publisher.publishEvent(new WarnEvent(this, "测试" + Thread.currentThread().getName()));*/
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        });
    }
}
