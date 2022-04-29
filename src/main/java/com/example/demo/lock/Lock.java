package com.example.demo.lock;

import com.example.demo.util.RedisUtils2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/1/7 10:54
 */
@Slf4j
public class Lock {

    /**
     * 为了支持线程内可重入锁，增加ThreadLocal,
     * 在加锁时:1.如果不存在则认为此线程未加锁,进行redis加锁操作并将计数器加1
     * 2.如果已经存在,则认为此线程已经加锁,将计数器加1
     * 在解锁时:1.如果计数器不存在或为0,则认为所有加锁已解锁，清除redis锁
     * 2.如果计数器不为0,则将计时器减一
     */
    private static final ThreadLocal<Map<String, AtomicInteger>> lockThreadLocal = new ThreadLocal<Map<String, AtomicInteger>>() {
        @Override
        protected Map<String, AtomicInteger> initialValue() {
            return new ConcurrentHashMap<String, AtomicInteger>();
        }
    };

    /**
     * 毫秒与毫微秒的换算单位 1毫秒 = 1000000毫微秒
     */
    private static final long MILLI_NOW_CONVERSION = 1000 * 1000L;
    /**
     * 默认处理超时时间（毫秒）
     */
    private static final long WAITING = 1000;

    /**
     * 锁的默认持续时间（秒），过期删除
     */
    private static final long EXPIRE = 60;
    /**
     * 锁的key
     */
    private String key;
    /**
     * 锁的关键词
     */
    private String value;
    /**
     * 锁状态标志
     */
    private boolean locked = Boolean.FALSE;

    /**
     * redis客户端
     */
    private RedisUtils2 redisUtils2;


    public Lock(String key, String value, RedisUtils2 redisUtils2) {
        if (StringUtils.isBlank(key)) {
            log.error("防并发redis锁的key为空，无法进行设置，请检查配置");
            throw new ConcurrentLockException(400, "业务正在处理中,请稍后再次操作");
        } else {
            this.key = key;
        }
        if (StringUtils.isBlank(value)) {
            this.value = String.valueOf(System.nanoTime());
        } else {
            this.value = value;
        }
        this.redisUtils2 = redisUtils2;
    }


    public boolean lock() {
        return lock(WAITING, EXPIRE);
    }

    /**
     * 加锁
     *
     * @param waiting 等待时间
     * @return
     */
    public boolean lock(long waiting) {
        return lock(waiting, EXPIRE);
    }

    /**
     * 加锁
     *
     * @param waiting 超时时间(毫秒)
     * @param expire  锁的持续时间（秒），过期删除
     * @return 成功或失败标志
     */
    public boolean lock(long waiting, long expire) {
        Map<String, AtomicInteger> countMap = lockThreadLocal.get();
        if (countMap.containsKey(key)) {
            countMap.get(key).incrementAndGet();
            locked = Boolean.TRUE;
        } else {
            long waitEnd = System.nanoTime() + waiting * MILLI_NOW_CONVERSION;
            while (System.nanoTime() < waitEnd) {
                Boolean result = redisUtils2.setExpire(key, value, expire);
                if (result) {
                    countMap.put(key, new AtomicInteger(1));
                    locked = Boolean.TRUE;
                    break;
                }
                try {
                    Thread.sleep(50, ThreadLocalRandom.current().nextInt(500));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return locked;
    }

    /**
     * 解锁
     * 无论是否加锁成功，都需要调用unlock
     * 建议放在finally 方法块中
     */
    public void unlock() {
        try {
            if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(key) && locked) {
                Map<String, AtomicInteger> countMap = lockThreadLocal.get();
                if (countMap.containsKey(key)) {
                    int count = countMap.get(key).decrementAndGet();
                    if (count == 0) {
                        countMap.remove(key);
                        delKey();
                    }
                } else {
                    delKey();
                }
            }
        } catch (Exception e) {
            log.error("redis并发锁清除出现异常:{}", key, e);
        }
    }


    private void delKey() {
        if (value.equals(redisUtils2.get(key))) {
            redisUtils2.delete(key);
        } else {
            log.error("redis锁内容被修改,不进行释放");
        }
    }

}

