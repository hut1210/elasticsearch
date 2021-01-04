package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2020/12/30 19:57
 */
@Configuration
@Slf4j
public class ExecutorConfig {

    @Bean("multiplePool")
    public Executor asyncServiceExecutor(){
        log.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        //设置最大线程数
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors()+1);
        executor.setKeepAliveSeconds(60);
        //设置队列大小
        executor.setQueueCapacity(99999);
        //设置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-service-");
        //rejection-polic：当pool达到max size的时候，如何处理新任务，拒绝策略
        //CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        executor.initialize();
        return executor;
    }

    @Bean("multiplePool2")
    public ThreadPoolTaskExecutor asyncServiceExecutor2(){
        log.info("start asyncServiceExecutor2");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        //设置最大线程数
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors()+1);
        executor.setKeepAliveSeconds(60);
        //设置队列大小
        executor.setQueueCapacity(99999);
        //设置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-service2-");
        //rejection-polic：当pool达到max size的时候，如何处理新任务，拒绝策略
        //CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        executor.initialize();
        return executor;
    }

    @Bean("singlePool")
    public Executor asyncSingleServiceExecutor(){
        log.info("start asyncSingleServiceExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(1);
        //配置最大线程数
        executor.setMaxPoolSize(1);
        //配置队列大小
        executor.setQueueCapacity(99999);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-single-service-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        executor.initialize();
        return executor;
    }
}
