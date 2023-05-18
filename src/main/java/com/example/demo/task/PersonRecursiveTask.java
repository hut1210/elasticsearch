package com.example.demo.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.domain.Person;
import com.example.demo.mapper.PersonMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @author hut
 * @date 2023/3/10 11:09 上午
 */
public class PersonRecursiveTask extends RecursiveTask<List<Person>> {

    //创建临界值。也就是任务粒度
    private static final int THRESHOLD = 2;

    private PersonMapper personMapper;
    private final int start;
    private final int end;
    public PersonRecursiveTask(int start, int end, PersonMapper personMapper) {
        this.start = start;
        this.end = end;
        this.personMapper = personMapper;
    }

    @Override
    protected List<Person> compute() {
        //使用二分法细分任务，如果任务小的不能再细分，则直接计算
        if (end-start<=THRESHOLD){
            List<Person> resultList = new ArrayList<>();
            // 任务已不能拆分，直接计算
            // 这里是求给定两个数之间的数求和，rangeClosed包含结束节点
            System.out.println(start+"   "+end);
            for (int i = start; i <= end; i++) {
                LambdaQueryWrapper<Person> lambdaQueryWrapper = new LambdaQueryWrapper();
                lambdaQueryWrapper.eq(Person::getName, "zs" + i);
                resultList.addAll(personMapper.selectList(lambdaQueryWrapper));
            }
            return resultList;
        }else{
            // 使用二分法 细分
            int mid = (start + end)/2;
            System.out.println("mid = "+mid);
            // 细分任务
            PersonRecursiveTask leftTask = new PersonRecursiveTask( start, mid , personMapper);
            PersonRecursiveTask rightTask = new PersonRecursiveTask( mid + 1, end , personMapper );
            // fork()方法去分配任务执行任务
            leftTask.fork();
            List<Person> rightResult = rightTask.compute();
            // join()方法汇总任务结果
            List<Person> leftResult = leftTask.join();
            leftResult.addAll(rightResult);
            return leftResult;
        }
    }
}
