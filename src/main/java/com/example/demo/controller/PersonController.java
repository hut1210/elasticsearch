package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.domain.Person;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.template.CacheLoadble;
import com.example.demo.template.RedisCacheTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/4/25 14:48
 */
@RestController
@RequestMapping("person")
@Slf4j
public class PersonController {

    @Resource
    private PersonMapper personMapper;

    @Autowired
    private RedisCacheTemplate<Person> redisCacheTemplate;

    @RequestMapping(value = "/getOne/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Person getOne(@PathVariable("name") String name) throws InterruptedException, JsonProcessingException {
        log.info("This is getOnePerson has been called......");
        return redisCacheTemplate.findCache("person:" + name, 30_000, new CacheLoadble<Person>() {
            @Override
            public Person load() {
                log.info("查询数据库 name={}", name);
                //查询数据库
                LambdaQueryWrapper<Person> lambdaQueryWrapper = new LambdaQueryWrapper();
                lambdaQueryWrapper.eq(Person::getName, name);
                Person person = personMapper.selectOne(lambdaQueryWrapper);
                return person;
            }
        }, true);
    }

    @ResponseBody
    @PostMapping("/save")
    public String savePerson(@RequestBody Person person){
        personMapper.insert(person);
        return "Success";
    }


}
