package com.example.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @author huteng5
 * @version 1.0
 * @date 2020/12/30 20:19
 */
@Component
@Slf4j
public class TaskJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("定时执行任务中...{}---{},{}---{}",Thread.currentThread().getName(),System.currentTimeMillis(), LocalDateTime.now(),Runtime.getRuntime().availableProcessors());
    }
}
