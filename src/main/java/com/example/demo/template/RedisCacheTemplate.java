package com.example.demo.template;

import com.example.demo.bloomfilter.RedisBloomFilter;
import com.example.demo.domain.Person;
import com.example.demo.dto.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/4/25 19:55
 */
@Component
public class RedisCacheTemplate<T> {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ObjectMapper jacksonMapper;

    @Resource
    private RedisBloomFilter redisBloomFilter;

    @Resource
    private RedissonClient redissonClient;

    public Result<T> findCache(String key, long expire_ms, CacheLoadble<T> cacheLoadble, boolean useBloom ) throws JsonProcessingException {
        // 这两句话是为了获得泛型T，在运行过程中的真实class类型。
        ParameterizedType parameterizedType = (ParameterizedType)cacheLoadble.getClass().getGenericSuperclass();
        Class<T> TClass =  (Class<T>) parameterizedType.getActualTypeArguments()[0];

        // 先查询在Bloom过滤器是否存在
        if(useBloom) {
            boolean isExistBloom = redisBloomFilter.mightContain(key);
            if(!isExistBloom) {
                return Result.ok("查询不到数据");
            }
        }

        // 先查询缓存
        String obj_json = stringRedisTemplate.opsForValue().get(key);
        if(null != obj_json) {
            // 缓存命中直接返回
            return Result.ok(jacksonMapper.readValue(obj_json, TClass ));
        }

        // 若未命中Redis缓存，则从数据库查询
        RLock lock = redissonClient.getLock("redissonLock:" + key);
        // 最多锁15秒
        lock.lock(15, TimeUnit.SECONDS);
        try {
            // 获锁后，再次查询缓存。 之所以在这里再次查询缓存是因为，在等待锁期间缓存很可能已经（由其他线程）创建完成
            obj_json = stringRedisTemplate.opsForValue().get(key);
            if(null != obj_json) {
                // 缓存命中直接返回
                return Result.ok(jacksonMapper.readValue(obj_json, TClass));
            }

            // 调用实现接口的子类方法（具体加载什么内容由子类实现）
            T loadResult = cacheLoadble.load();
            // 将从数据库查询得到的结果放入Redis缓存
            if(null != loadResult) {
                stringRedisTemplate.opsForValue().set(key, jacksonMapper.writeValueAsString(loadResult),expire_ms,TimeUnit.MILLISECONDS);
            }
            return Result.ok(loadResult);
        }finally {
            lock.unlock(); // 解锁
        }
    }
}
