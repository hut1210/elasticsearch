package com.example.demo.lock;

import java.lang.annotation.*;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/1/7 10:51
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ConcurrentLock {

    /**
     * redisKey前缀
     *
     * @return
     */
    String prefix() default "";

    /**
     * redisKey后缀
     *
     * @return
     */
    String suffix() default "";

    /**
     * <code>StringUtils.isBlank()</code>
     * 若前缀为空，则无前缀连接符
     * 若后缀为空，则无后缀连接符
     *
     * @return
     */
    String connector() default "_";

    /**
     * 业务编号表达式
     *
     * @return
     */
    String bizNo();

    /**
     * reidsValue 默认使用系统时间
     * <code>System.nanoTime()</code>
     *
     * @return
     */
    String value() default "";

    /**
     * 等待时间(毫秒)
     */
    long waiting() default 1500;

    /**
     * 超时之后异常（针对某些可以允许并发的可以继续执行）
     */
    boolean exception() default true;

    /**
     * 默认有效时间（秒），防止jimClient故障造成的问题
     *
     * @return
     */
    long expire() default 180;

}
