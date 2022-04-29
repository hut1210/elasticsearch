package com.example.demo;

import java.util.concurrent.*;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/1/27 10:02
 */
public class InterfaceTimeOut {
    public static void main(String args[]){
        test2();
    }

    private static void test1(){
        long startTime = System.currentTimeMillis();
        try {
            FutureTask futureTask=new FutureTask(new Callable() {
                @Override
                public String call() throws Exception {
                    Thread.sleep(3000);
                    System.out.println("call方法执行了");
                    return "call方法返回值";
                }
            });

            FutureTask futureTask1=new FutureTask(new Callable() {
                @Override
                public String call() throws Exception {
                    Thread.sleep(3000);
                    System.out.println("call方法执行了1");
                    return "call方法返回值1";
                }
            });
            new Thread(futureTask).start();
            new Thread(futureTask1).start();
            //futureTask.run();
            System.out.println("获取返回值: " + futureTask.get());
            //futureTask1.run();
            System.out.println("获取返回值1: " + futureTask1.get());
            long endTime = System.currentTimeMillis();
            float excTime = (float)(endTime - startTime) / 1000;
            System.out.println("异步执行时间：" + excTime + "s");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void test2(){
        long startTime = System.currentTimeMillis();
        final ExecutorService exec = Executors.newFixedThreadPool(4);
        Callable<String> call = new Callable<String>(){
            public String call() throws Exception{
                Thread.sleep(1000 * 5);
                return "线程执行完";
            }
        };

        Callable<String> call2 = new Callable<String>(){
            public String call() throws Exception{
                Thread.sleep(1000 * 5);
                return "线程2执行完";
            }
        };

        Future<String> future = exec.submit(call);
        Future<String> future2 = exec.submit(call2);
        String obj = null;
        String obj2 = null;
        try{
            obj = future.get(1000 * 6, TimeUnit.MILLISECONDS);
            System.out.println("任务成功返回1"+obj);

            obj2 = future2.get(1000 * 6, TimeUnit.MILLISECONDS);
            System.out.println("任务成功返回2"+obj2);

            long endTime = System.currentTimeMillis();
            float excTime = (float)(endTime - startTime) / 1000;
            System.out.println("异步执行时间：" + excTime + "s");
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(ExecutionException e){
            e.printStackTrace();
        }catch(TimeoutException e){
            e.printStackTrace();
        }
        exec.shutdown();
    }
}
