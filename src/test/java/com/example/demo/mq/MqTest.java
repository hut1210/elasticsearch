package com.example.demo.mq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/12/24 16:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class MqTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Resource(name = "multiplePool")
    private Executor executor;

    CountDownLatch countDownLatch = new CountDownLatch(1000);

    @Test
    public void testLogin() {
        try {
            for (int i = 0; i < 1000; i++) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            testRestTemplate.postForObject("http://localhost:8090/user/login?username={username}&password={password}", null, String.class, "zs", "123456");
                            countDownLatch.countDown();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            countDownLatch.await();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
