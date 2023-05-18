package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.builder.helper.PageHelper;
import com.example.demo.domain.Person;
import com.example.demo.domain.User;
import com.example.demo.dto.Result;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.service.PersonService;
import com.example.demo.task.PersonRecursiveTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author hut
 * @date 2022/12/15 8:35 下午
 */
@Service
@Slf4j
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {

    @Resource(name = "multiplePool")
    private Executor executor;

    @Resource
    private PersonMapper personMapper;

    @Override
    @Async("multiplePool")
    public void executeAsync(List<Person> personList, PersonMapper personMapper, CountDownLatch countDownLatch) {
        try {
            log.warn("start executeAsync");
            //异步线程要做的事情
            saveBatch(personList);
            log.warn("end executeAsync");
        } finally {
            // 很关键, 无论上面程序是否异常必须执行countDown,否则await无法释放
            countDownLatch.countDown();
        }
    }

    @Override
    public Result getListSync(int end) {
        long startTime = System.currentTimeMillis();
        /*Page<Person> page = new Page<>(1, 5);
        page = personMapper.selectPage(page, new LambdaQueryWrapper());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        List<Person> resultList = new ArrayList<>();
        for (int i = 0; i < end; i++) {
            LambdaQueryWrapper<Person> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(Person::getName, "zs" + i);
            resultList.addAll(personMapper.selectList(lambdaQueryWrapper));
            /*try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        log.info("耗费时间" + (System.currentTimeMillis() - startTime));
        return Result.ok(resultList);
    }

    @Override
    public Result getListAsync() {
        List<Person> resultList = new ArrayList<>();
        try {
            long startTime = System.currentTimeMillis();
            resultList = getList();
            log.info("耗费时间" + (System.currentTimeMillis() - startTime)+" 数据长度="+resultList.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (CollectionUtils.isNotEmpty(resultList)) {
            //log.info(resultList.toString());
            return Result.ok(resultList.stream().filter(person -> person != null).sorted(Comparator.comparing(Person::getId)).collect(Collectors.toList()));
        }
        return Result.ok();
    }

    @Override
    public Result getListAsync2(int end) {
        List<Person> resultList = new ArrayList<>();
        try {
            long startTime = System.currentTimeMillis();
            resultList = getList2(end);
            log.info("耗费时间" + (System.currentTimeMillis() - startTime)+" 数据长度="+resultList.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (CollectionUtils.isNotEmpty(resultList)) {
            //log.info(resultList.toString());
            return Result.ok(resultList.stream().filter(person -> person != null).sorted(Comparator.comparing(Person::getId)).collect(Collectors.toList()));
        }
        return Result.ok();
    }

    @Override
    public Result getListAsync3() {
        List<Person> resultList = new ArrayList<>();
        try {
            long startTime = System.currentTimeMillis();
            resultList = getList3();
            log.info("耗费时间" + (System.currentTimeMillis() - startTime)+" 数据长度="+resultList.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (CollectionUtils.isNotEmpty(resultList)) {
            //log.info(resultList.toString());
            return Result.ok(resultList.stream().filter(person -> person != null).sorted(Comparator.comparing(Person::getId)).collect(Collectors.toList()));
        }
        return Result.ok();
    }

    @Override
    public Result getListAsync4(int end) {
        List<Person> resultList = new ArrayList<>();
        try {
            long startTime = System.currentTimeMillis();
            PersonRecursiveTask task = new PersonRecursiveTask(0,end,personMapper);
            resultList = new ForkJoinPool().invoke(task);
            log.info("耗费时间" + (System.currentTimeMillis() - startTime)+" 数据长度="+resultList.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (CollectionUtils.isNotEmpty(resultList)) {
            //log.info(resultList.toString());
            return Result.ok(resultList.stream().filter(person -> person != null).sorted(Comparator.comparing(Person::getId)).collect(Collectors.toList()));
        }
        return Result.ok();
    }

    public synchronized List<Person> getList() throws InterruptedException {
        List<Person> resultList = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(10);//创建一个计数器（大小为当前数组的大小，确保所有执行完主线程才结束）
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executor.execute(() -> {
                LambdaQueryWrapper<Person> lambdaQueryWrapper = new LambdaQueryWrapper();
                lambdaQueryWrapper.eq(Person::getName, "zs" + finalI);
                List<Person> personList = personMapper.selectList(lambdaQueryWrapper);
                resultList.addAll(personList);
                /*try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                latch.countDown();
            });
        }
        latch.await();
        return resultList;
    }

    public List<Person> getList2(int end) {
        List<FutureTask<List<Person>>> resultList = new ArrayList<>();
        for (int i = 0; i < end; i++) {
            int finalI = i;
            FutureTask<List<Person>> futureTask = new FutureTask<>(new Callable<List<Person>>() {
                @Override
                public List<Person> call() throws Exception {
                    LambdaQueryWrapper<Person> lambdaQueryWrapper = new LambdaQueryWrapper();
                    lambdaQueryWrapper.eq(Person::getName, "zs" + finalI);
                    List<Person> personList = personMapper.selectList(lambdaQueryWrapper);
                    return personList;
                }
            });

            //把任务丢给线程池调度执行
            executor.execute(futureTask);
            //future异步模式，把任务放进去先，先不取结果
            resultList.add(futureTask);
        }
        List<Person> result = new ArrayList<>();
        while (resultList.size() > 0) {
            Iterator<FutureTask<List<Person>>> iterator = resultList.iterator();
            while (iterator.hasNext()) {
                try {
                    result.addAll(iterator.next().get());
                    //获取一个就删除一个任务
                    iterator.remove();
                } catch (InterruptedException | ExecutionException e) {
                    log.error("多线程查询出现异常：{}", e.getMessage());
                }
            }
        }
        return result;
    }

    public synchronized List<Person> getList3() throws InterruptedException {
        List<Person> resultList = new ArrayList<>();
        //这个要和list的长度一样 防止循环完后 子线程不等待主线程  因为这是循环数大于线程数 latch被减为0后 当前线程就会被唤醒。
        CountDownLatch countDownLatch = new CountDownLatch(10);
        Semaphore semaphore = new Semaphore(10);//定义几个许可 代表那个线程拿到许可那个线程谁执行
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            try {
                semaphore.acquire(10);// 放这的话 是怕有别的线程进来  看情况需要
                executor.execute(() -> {
                    //此处可以放入待处理的业务
                    System.out.println(Thread.currentThread().getName());
                    LambdaQueryWrapper<Person> lambdaQueryWrapper = new LambdaQueryWrapper();
                    lambdaQueryWrapper.eq(Person::getName, "zs" + finalI);
                    List<Person> personList = personMapper.selectList(lambdaQueryWrapper);
                    resultList.addAll(personList);
                    semaphore.release(10);
                });
            } catch (Exception e) {
                e.getMessage();
            } finally {
                countDownLatch.countDown();
            }
        }
        countDownLatch.await();//所有子线程到达后  才执行下面的代码 不过这的意思是
        return resultList;
    }
}
