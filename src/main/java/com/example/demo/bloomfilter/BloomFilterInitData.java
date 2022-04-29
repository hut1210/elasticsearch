package com.example.demo.bloomfilter;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.domain.Person;
import com.example.demo.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void initdata() {
        List<Person> persons = personMapper.selectList(new LambdaQueryWrapper<>());
        for(Person person : persons) {
            redisBloomFilter.put("person:" + person.getName());
        }
    }
}
