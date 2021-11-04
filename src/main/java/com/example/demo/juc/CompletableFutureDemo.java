package com.example.demo.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/9/2 18:51
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "没有返回，update mysql ok");
        });
        completableFuture.get();*/

        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "有返回，insert mysql ok");
            int a =10/0;
            return 1024;
        });

        System.out.println(integerCompletableFuture.whenComplete((t, u) -> {
            System.out.println("t ====" + t + ",   u=======" + u);
        }).exceptionally(e -> {
            System.out.println("exception====" + e.getMessage());
            return 500;
        }).get());
    }
}
