/*
package com.example.demo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

*/
/**
 *  @ClassName: FutureResponseTest
 *  * @Description: 根据交易请求的额度和方式，进行扣款汇总计算
 *  * 计算交易一共扣款的数额：
 *  * 		利用Callable和Future来计算，future.get()会获取任务结果，如果没有获取到会一直阻塞直到任务结束。
 *  *
 *  * 想优化一下，即每一个都一个future  这样会更快
 *  * 
 *  * 这样虽可行，但是却有些繁琐。还有一种更好的办法：CompletionService
 *  * 等理论学习后，继续写一个例子
 * ————————————————
 * 版权声明：本文为CSDN博主「cc-lady」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/cc907566076/article/details/84937502 
 * Date: 2022/11/7 11:11 上午
 * @param null
 * @return
 * @author hut
 * @date 2022/11/7 11:07 上午
 *//*

public class FutureResponseTest {
    //线程池
    private final static ExecutorService executor = Executors.newCachedThreadPool();

    //模拟第三方服务
    public static double requestForService(Request request) throws InterruptedException, Exception{
        if(null == request) {
            throw new Exception("请求为空！");
        }
        if(request.getParam() <= 0) {
            throw new Exception("参数小于0，无法进行扣款！" + request);
        }

        System.out.println("开始处理请求...");

        //为了简便直接返回一个结果即可
        double result = 0.0;
        if("WeiXin".equals(request.getMethod())) {
            System.out.println("微信支付扣3%");
//			result = request.getParam() * 0.03;//double类型计算结果不准确  例如17 * 0.05 返回  扣款数  0.8500000000000001
            result = new BigDecimal(String.valueOf(request.getParam())).multiply(new BigDecimal("0.03")).doubleValue();
        }else {
            System.out.println("其他支付直接扣5%");
            result = new BigDecimal(String.valueOf(request.getParam())).multiply(new BigDecimal("0.05")).doubleValue();
        }

        //模拟-使消耗时间长一些
        Thread.sleep(3000);
        System.out.println(request + " 返回扣款结果：" + result);
        return result;
    }


    //调度请求，获得返回结果，并进行汇总处理
    public static void main(String[] args) throws Exception {
        final String[] methodStr = new String[] {"WeiXin","ZhiFuBao","WangYin"};
        final String[] serviceStr = new String[] {"TaoBao","JingDong","TianMao"};

        //为了方便，我们将请求先初始化完毕
        final List<Request> requestList = new ArrayList<Request>();
        for (int i = 0; i < 20; i++) {
            Request request = new Request();
            request.setMethod(methodStr[(int) (Math.random() * 3)]);
            request.setParam((int) (Math.random() * 300));
            request.setServieName(serviceStr[(int) (Math.random() * 3)]);
            requestList.add(request);
        }

        long startTime = System.currentTimeMillis();//开始时间

        //累积计算所有请求的总扣款数--计算任务提前开始且每个都是分开的不相互影响
        List<Future<Double>> futureList = new ArrayList<Future<Double>>();
        for (int i = 0; i < requestList.size(); i++) {
            Request request = requestList.get(i);
            Callable<Double> task = new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    return  requestForService(request);
                }
            };
            Future<Double> future = executor.submit(task);
            futureList.add(future);
        }

        try {

            BigDecimal sum = BigDecimal.ZERO;//同理  double计算结果不精确

            //方法get具有“状态依赖”的内在特性，因而调用者不需要知道任务的状态，此外在任务提交和获得
            //结果中包含的安全发布属性也确保了这个方法是线程安全的。
            //获得结果
            for (int i = 0; i < futureList.size(); i++) {
                //提交任务请求
                double payMent = futureList.get(i).get();
                sum = sum.add(new BigDecimal(String.valueOf(payMent)));
            }
            System.out.println("一共扣款了多少钱？" + sum.doubleValue());
        } catch (InterruptedException e) {
            // TODO: 任务调用get的线程在获得结果之前被中断
            //重新设置线程的中断状态
            Thread.currentThread().interrupt();
            System.out.println("任务调用get的线程在获得结果之前被中断！" + e);
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        } finally {
            executor.shutdown();
        }


        long endTime = System.currentTimeMillis();//结束时间
        System.out.println("消耗时间：" + (endTime - startTime) + "毫秒！");
    }



    */
/**
     * @Title: launderThrowable
     * @Description: 任务执行过程中遇到异常，根据包装的ExecutionException重新抛出异常，并打印异常信息
     * @param @param cause
     * @param @return
     * @return Exception
     * @throws
     * @author CC
     * @date 2018年12月7日 上午9:36:27
     * @version V1.0
     *//*

    private static Exception launderThrowable(Throwable cause) {
        //抛出
        Exception exception = new Exception("任务执行过程中遇到异常！" + cause);
        //打印
        cause.printStackTrace();
        return exception;
    }
}
*/
