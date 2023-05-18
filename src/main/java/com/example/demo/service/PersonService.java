package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.domain.Person;
import com.example.demo.dto.Result;
import com.example.demo.mapper.PersonMapper;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author hut
 * @date 2022/12/15 8:34 下午
 */
public interface PersonService extends IService<Person> {

    void executeAsync(List<Person> listSub, PersonMapper personMapper, CountDownLatch countDownLatch);

    Result getListSync(int end);

    Result getListAsync();

    Result getListAsync2(int end);

    Result getListAsync3();

    Result getListAsync4(int end);
}
