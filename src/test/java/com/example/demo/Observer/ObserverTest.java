package com.example.demo.Observer;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/13 15:42
 */
public class ObserverTest {
    public static void main(String[] args) {
        JavaStackObservable javaStackObservable = new JavaStackObservable();

        javaStackObservable.addObserver(new ReaderObserver("小明"));
        javaStackObservable.addObserver(new ReaderObserver("小张"));
        javaStackObservable.addObserver(new ReaderObserver("小爱"));

        javaStackObservable.publish("什么是观察者模式");
    }
}
