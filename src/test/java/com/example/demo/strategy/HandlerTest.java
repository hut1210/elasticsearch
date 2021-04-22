package com.example.demo.strategy;

import com.example.demo.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/22 11:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= DemoApplication.class)// 指定spring-boot的启动类
public class HandlerTest {

    @Autowired
    private AbstractHandlerProcessor chooser;

    @Test
    public void test() throws Exception{
        //准备数据
        String type = "free";
        //获取任务类型对应的solver
        AbstractHandler processor = chooser.choose(type);
        //调用不同handle的方法进行处理
        processor.handle();
    }
}
