package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/12/22 18:13
 */
public class Log4jTest {
    private static final Logger logger = LogManager.getLogger(Log4jTest.class);

    public static void main(String[] args) {
        String name = "${java:vm}";
        logger.info("{},登录了", name);

        System.out.println(689838036887797761L%3);
        System.out.println(689838037198176256L%3);
        System.out.println(689838037638578177L%3);
    }
}
