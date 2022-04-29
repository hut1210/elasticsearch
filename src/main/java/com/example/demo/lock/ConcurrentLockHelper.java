package com.example.demo.lock;

import com.example.demo.util.RedisUtils2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/1/7 10:52
 */
@Slf4j
public class ConcurrentLockHelper<T> {

    private RedisUtils2 redisUtils2;

    /**
     * 等待时间(毫秒)
     */
    private static final long waiting = 1500;

    /**
     * 默认有效时间(秒)
     */
    private static final long expire = 10;


    private ConcurrentLockHelper(RedisUtils2 redisUtils2) {
        this.redisUtils2 = redisUtils2;
    }

    public static <T> ConcurrentLockHelper<T> getInstance(RedisUtils2 redisUtils2) {
        return new ConcurrentLockHelper<T>(redisUtils2);
    }

    public T doAround(String bizNo, ExclusiveProcessor<T> exclusiveProcessor) throws Exception {
        return doAround(bizNo, String.valueOf(System.nanoTime()), this.waiting, this.expire, exclusiveProcessor);
    }

    public T doAround(List<String> bizNoList, ExclusiveProcessor<T> exclusiveProcessor) throws Exception {
        return doAround(bizNoList, String.valueOf(System.nanoTime()), this.waiting, this.expire, exclusiveProcessor);
    }


    public T doAround(String bizNo, String value, long waiting, long expire, ExclusiveProcessor<T> exclusiveProcessor) throws Exception {

        Lock lock = null;
        try {

            if (StringUtils.isBlank(value)) {
                value = String.valueOf(System.nanoTime());
            }
            lock = new Lock(bizNo, value, redisUtils2);
            if (!lock.lock(waiting, expire)) {
                lock.unlock();
                log.debug("redis锁的key:{}获取锁超时", bizNo);
                throw new ConcurrentLockException(400, "业务正在处理中,请稍后再次操作");
            }

        } catch (ConcurrentLockException c) {
            throw c;
        } catch (Exception e) {
            if (lock != null) {
                lock.unlock();
            }
            log.error("获取redis并发锁异常", e);
            throw new ConcurrentLockException(400, "业务正在处理中,请稍后再次操作", e);
        }

        try {
            return exclusiveProcessor.doProcess();
        } finally {
            lock.unlock();
        }
    }

    public T doAround(List<String> bizNoList, String value, long waiting, long expire, ExclusiveProcessor<T> exclusiveProcessor) throws ConcurrentLockException {
        List<Lock> lockList = new ArrayList<>();

        for (String bizNo : bizNoList) {
            Lock lock = null;
            try {
                if (StringUtils.isBlank(value)) {
                    value = String.valueOf(System.nanoTime());
                }
                lock = new Lock(bizNo, value, redisUtils2);
                if (!lock.lock(waiting, expire)) {
                    lock.unlock();
                    log.debug("redis锁的key:{}获取锁超时", bizNo);
                    throw new ConcurrentLockException(400, "业务正在处理中,请稍后再次操作");
                }
            } catch (ConcurrentLockException c) {
                throw c;
            } catch (Exception e) {
                if (lock != null) {
                    lock.unlock();
                }
                log.error("获取redis并发锁异常", e);
                throw new ConcurrentLockException(400, "业务正在处理中,请稍后再次操作", e);
            }
            lockList.add(lock);
        }
        try {
            return exclusiveProcessor.doProcess();
        } finally {
            for (Lock lock : lockList){
                lock.unlock();
            }
        }
    }


}
