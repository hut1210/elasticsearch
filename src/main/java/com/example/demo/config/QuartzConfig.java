package com.example.demo.config;

import com.example.demo.task.TaskJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.concurrent.Executor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2020/12/30 19:56
 */
@Configuration
@Slf4j
public class QuartzConfig implements SchedulerFactoryBeanCustomizer {

    @Resource(name = "multiplePool")
    Executor executor;

    @Resource(name = "multiplePool2")
    ThreadPoolTaskExecutor executor2;

    @Resource
    DataSource dataSource;

    @Value("${scheduled.cron.style}")
    private String cron;

    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setTaskExecutor(executor);
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));
    }
    @Bean
    public JobDetail jobDetail(){
        return JobBuilder.newJob(TaskJob.class)
                .withIdentity("jobDetail")// 可以给该JobDetail起一个id，便于之后的检索。也可以 .withIdentity("myjob", "group1")
                .requestRecovery() // 执行中应用发生故障，需要重新执行
                .storeDurably()   // 即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
    }

    @Bean
    public Trigger trigger(@Qualifier("jobDetail") JobDetail jobDetail){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("trigger")
                .startAt(DateBuilder.futureDate(5,DateBuilder.IntervalUnit.SECOND))// 延迟10秒开始
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
    }
}
