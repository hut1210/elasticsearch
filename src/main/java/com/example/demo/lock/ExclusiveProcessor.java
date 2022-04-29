package com.example.demo.lock;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/1/7 10:53
 */
public interface ExclusiveProcessor<T> {
    T doProcess();
}
