package com.example.demo.bloomfilter;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.config.RedisConfig;
import com.example.demo.domain.Person;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.util.RedisUtils;
import com.example.demo.util.RedisUtils2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/4/25 15:42
 */
@Component
public class BloomFilterInitData {

    @Autowired
    PersonMapper personMapper;

    @Autowired
    RedisBloomFilter redisBloomFilter;

    @Resource
    RedisUtils redisUtils;

    @PostConstruct
    public void initdata() {
        List<Person> persons = personMapper.selectList(new LambdaQueryWrapper<>());
        for (Person person : persons) {
            redisBloomFilter.put(RedisConfig.personPreFix + person.getName());
        }

        if(!redisUtils.hasKey("personList")){
            for (int i = 0; i < 100000; i++) {
                Person person = new Person();
                person.setName("zs"+i);
                person.setBirthDay(DateTime.now());
                redisUtils.lSet("personList",person);
            }
        }
    }
}
