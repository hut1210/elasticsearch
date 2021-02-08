package com.example.demo.task;

import com.example.demo.domain.WarnRules;
import com.example.demo.strategy.IWarnTrigger;
import com.example.demo.strategy.WarnTriggerFactory;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2020/12/30 20:19
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
@Slf4j
public class TaskJob extends QuartzJobBean {

    @Resource(name = "multiplePool")
    private Executor executor;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        /*log.info("定时执行任务中...{}---{},{}---{}",Thread.currentThread().getName(),System.currentTimeMillis(), LocalDateTime.now(),Runtime.getRuntime().availableProcessors());
        log.info("预警定时任务执行中......" + Thread.currentThread().getName());
        //获取预警规则为有效的列表
        List<WarnRules> warnRulesList = new ArrayList<>();
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
                            *//*Thread.sleep(500);
                            log.info("触发预警规则！！！" + Thread.currentThread().getName());
                            publisher.publishEvent(new WarnEvent(this, "测试" + Thread.currentThread().getName()));*//*
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        });*/
    }
}
