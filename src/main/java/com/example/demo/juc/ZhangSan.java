package com.example.demo.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/4/29 9:33
 */
@Slf4j
public class ZhangSan {
    public static final ExecutorService executor = Executors.newFixedThreadPool(10);

    private ZhangSan() {
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long startTime = System.currentTimeMillis();
        log.info("\n开始时间...{}\n", startTime);
        int i = 0;
        ZhangSan zhangSan = new ZhangSan();

        while (true) {
            i++;
            if (i > 1) {
                break;
            }
            // 张三的一天
            zhangSan.theDayOfZhangSan(i);
        }

        log.info("\n耗时：{}\n", System.currentTimeMillis() - startTime);

    }

    // 张三的钱包余额
    private int balance = 0;

    private synchronized void addBalance(int money) {
        balance = balance + money;
    }

    /**
     * 异步编排加速张三的一天
     * <p>
     * 张三早上起床刷牙、吃早饭、与此同时张三打开收音机开始听新闻,做完这些之后张三去找爸妈要钱，根据要到的钱的数目决定去做什么...
     * <p>
     * 张三听收音机的同时做了起床刷牙吃早饭,花费4000毫秒，于此同时收听了新闻，张三最后等待妈妈给钱花费了1000毫秒 总耗时 5000毫秒
     */
    public void theDayOfZhangSan(int i) throws ExecutionException, InterruptedException {

        log.info("第{}天开始了", i);

        // 串行 演示张三 起床、刷牙、吃走饭，
        CompletableFuture<Void> futureStep1 = CompletableFuture.runAsync(() -> {
            log.info("张三醒了看了一眼微信余额，还有{}元", balance);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("张三穿好衣服起床了");
        }, executor).thenRunAsync(() -> {
            log.info("张三开始刷牙");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("张三刷完牙");
        }, executor).thenRunAsync(() -> {
            log.info("张三开始吃早饭");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("张三吃完早饭");
        });

        // 听新闻、和张三起床刷牙吃早饭并行
        CompletableFuture<Void> futureStep02 = CompletableFuture.runAsync(() -> {
            log.info("张三打开收音机，开始听新闻...");
            ArrayList<String> newsList = new ArrayList<>();
            newsList.add("96金60银51铜！中国代表团连续五届残奥会金牌、奖牌双第一...");
            newsList.add("北海一拖船翻沉1人落水，附近油船工作人员“见死不救”被罚");
            newsList.add("【国足0-3不敌澳大利亚，无缘12强赛开门红】在北京时间今天凌晨举行的2022国际足联卡塔尔世界杯预选赛亚洲区最终阶段B组 #国足vs澳大利亚# 的比赛中，上半场第24分钟、第26分钟澳大利亚球员马比尔、博伊尔先后破门，国足0-2落后；下半场第70分钟杜克补射破门再丢一球。最终国足0-3不敌澳大利亚，无缘12强赛开门红。值得一提的是，#国足全场0射正# ，而本轮过后，中国队小组排名");
            try {
                for (String item : newsList) {
                    Thread.sleep(1000);
                    log.info("新闻滚动播放中，这在播放：{}...", item);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("新闻结束了");
        }, executor);

        // 模拟 张三 演示张三 起床、刷牙、吃走饭之后 和妈妈要钱
        CompletableFuture<Integer> futureGetMoneyByMom = futureStep1.thenCombineAsync(futureStep02, (f1, f2) -> {
            log.info("找妈妈要100块钱...");
            try {
                Thread.sleep((int) (Math.random() * 1000) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("妈妈转账100块钱");
            return 100;
        }, executor).whenCompleteAsync((res, throwable) -> {
            if (throwable == null) {
                addBalance(res);
            } else {
                log.error("妈妈没有给钱", throwable);
            }
        }, executor).handle((a,b)->{
            b.printStackTrace();
            return 0;
        });

        // 模拟 张三 演示张三 起床、刷牙、吃走饭之后 同时和爸爸要钱
        CompletableFuture<Integer> futureGetMoneyByDad = futureStep1.thenCombineAsync(futureStep02, (f1, f2) -> {
            log.info("找爸爸要10块钱...");
            try {
                Thread.sleep((int) (Math.random() * 1000) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int money = (int) (Math.random() * 100) + 1;
            log.info("爸爸转账{}块钱", money);
            return money;
        }, executor).whenCompleteAsync((res, throwable) -> {
            if (throwable == null) {
                addBalance(res);
            } else {
                log.error("爸爸没有给钱", throwable);
            }
        }).exceptionally(e->{
            e.printStackTrace();
            return 0;
        });


        // 模拟无论爸爸给钱还是妈妈给钱，张三立刻行动
        CompletableFuture<String> waitMoneyFuture = futureGetMoneyByMom.applyToEitherAsync(futureGetMoneyByDad, res -> {
            log.info("张三看了一眼微信余额，已经有{}元", balance);
            if (balance < 99) {
                log.info("准备去上网");
                return "上网";
            }
            if (balance >= 100 && balance < 200) {
                log.info("准备去蹦迪");
                return "蹦迪";
            }
            if (balance >= 200 && balance < 300) {
                log.info("去约会");
                return "去约会";
            }
            if (balance >= 1000 && balance < 10000) {
                log.info("去投资");
                return "去投资";
            }
            if (balance > 10000) {
                log.info("张三财富自由了");
                return "财富自由了";
            }

            return "继续睡觉";
        }, executor);


        String doWhat = waitMoneyFuture.get();
        log.info("张三第{}天,去{}了", i, doWhat);
    }
}
