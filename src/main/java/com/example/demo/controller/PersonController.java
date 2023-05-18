package com.example.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.bloomfilter.BloomFilterHelper;
import com.example.demo.bloomfilter.RedisBloomFilter;
import com.example.demo.domain.Demo;
import com.example.demo.domain.Person;
import com.example.demo.domain.convert.PersonConvert;
import com.example.demo.domain.convert.PersonDtoConvert;
import com.example.demo.dto.PersonDto;
import com.example.demo.dto.Result;
import com.example.demo.listener.UploadDataListener;
import com.example.demo.lock.BizException;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.param.ImportDataDto;
import com.example.demo.service.PersonService;
import com.example.demo.template.CacheLoadble;
import com.example.demo.template.RedisCacheTemplate;
import com.example.demo.util.RedisUtils;
import com.example.demo.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Resource
    private PersonService personService;

    @Autowired
    private RedisCacheTemplate<Person> redisCacheTemplate;

    @Autowired
    RedisBloomFilter redisBloomFilter;

    @Resource
    RedisUtils redisUtils;

    @Resource
    Redisson redisson;

    @RequestMapping(value = "/getOne/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Person> getOne(@PathVariable("name") String name) throws InterruptedException, JsonProcessingException {
        log.info("This is getOnePerson has been called......");
        return redisCacheTemplate.findCache("person::" + name, 30_000, new CacheLoadble<Person>() {
            @Override
            public Person load() {
                log.info("查询数据库 name={}", name);
                //查询数据库
                RLock rLock = redisson.getReadWriteLock("person::" + name).readLock();
                rLock.lock();
                try {
                    LambdaQueryWrapper<Person> lambdaQueryWrapper = new LambdaQueryWrapper();
                    lambdaQueryWrapper.eq(Person::getName, name);
                    Person person = personMapper.selectOne(lambdaQueryWrapper);
                    return person;
                }finally {
                    rLock.unlock();
                }
            }
        }, true);
    }

    @ResponseBody
    @PostMapping("/save")
    public String savePerson(@RequestBody Person person) {
        personMapper.insert(person);
        redisBloomFilter.put("person::"+person.getId());
        return "Success";
    }

    @ResponseBody
    @PostMapping("/update")
    public String delete(@RequestBody Person person) {
        RLock rLock = redisson.getReadWriteLock("person::" + person.getName()).writeLock();
        rLock.lock();
        try {
            redisUtils.del("person::" + person.getName());
            personMapper.updateById(person);
        }finally {
            rLock.unlock();
        }
        return "Success";
    }

    @ResponseBody
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        personMapper.deleteById(id);
        return "Success";
    }

    @GetMapping(value = "importExcel")
    public Result importExcel(
            @RequestParam(value = "file", required = true) MultipartFile file
    ) {
        ImportDataDto importDataDto = new ImportDataDto();
        importDataDto.setRowCountLimit(100);
        try {
            List failDataList = new ArrayList();
            importDataDto.setFailDataList(failDataList);
            UploadDataListener uploadDataListener = new UploadDataListener<PersonDto>(importDataDto) {
                @Override
                public void validData(PersonDto data) {
                    System.out.println(data);
                    if (StringUtils.isBlank(data.getName())) {
                        throw new BizException(500, "姓名不能为空");
                    }
                    if (StringUtils.isBlank(data.getBirthDay())) {
                        throw new BizException(500, "生日不能为空");
                    }
                }

                @Override
                public void saveData(List<PersonDto> cachedDataList) {
                    //personService.saveBatch(PersonConvert.INSTANCT.convert(cachedDataList));
                    personService.saveBatch(PersonDtoConvert.INSTANCE.dtoToPo(cachedDataList));
                }
            };
            EasyExcel.read(file.getInputStream()).head(PersonDto.class).registerReadListener(uploadDataListener).sheet().headRowNumber(1).doRead();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(500, e.getMessage());
        }
        return Result.ok(importDataDto);
    }



    @GetMapping("/testAsync")
    public Result testMultiThread(){
        List<Object> result = redisUtils.lGet("personList", 0, -1);
        List<Person> personList = result.stream().map(item -> (Person) item).collect(Collectors.toList());
        //测试每100条数据插入开一个线程
        List<List<Person>> lists = Lists.partition(personList,1000);
        CountDownLatch countDownLatch = new CountDownLatch(lists.size());
        long startTime = System.currentTimeMillis();
        for (List<Person> listSub:lists) {
            personService.executeAsync(listSub, personMapper,countDownLatch);
        }
        try {
            countDownLatch.await(); //保证之前的所有的线程都执行完成，才会走下面的；
            // 这样就可以在下面拿到所有线程执行完的集合结果
        } catch (Exception e) {
            log.error("阻塞异常:"+e.getMessage());
        }
        log.info("耗费时间"+(System.currentTimeMillis()-startTime));
        return Result.ok();
    }

    @GetMapping("/testSync")
    public Result testSync(){
        List<Object> result = redisUtils.lGet("personList", 0, -1);
        List<Person> personList = result.stream().map(item -> (Person) item).collect(Collectors.toList());
        long startTime = System.currentTimeMillis();
        personService.saveBatch(personList);
        log.info("耗费时间"+(System.currentTimeMillis()-startTime));
        return Result.ok();
    }


    @GetMapping("/getListSync")
    public Result getListSync(@RequestParam("end")int end){
        return personService.getListSync(end);
    }

    @GetMapping("/getListAsync")
    public Result getListAsync(){
        return personService.getListAsync();
    }

    @GetMapping("/getListAsync2")
    public Result getListAsync2(@RequestParam("end")int end){
        return personService.getListAsync2(end);
    }

    @GetMapping("/getListAsync3")
    public Result getListAsync3(){
        return personService.getListAsync3();
    }

    @GetMapping("/getListAsync4")
    public Result getListAsync4(@RequestParam("end") int end){
        return personService.getListAsync4(end);
    }


}
