package com.maxqiu.demo.ThreadPool;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Max_Qiu
 */
public class TestScheduledThreadPool {

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

        for (int i = 0; i < 5; i++) {
            Future<Integer> result = pool.schedule(() -> {
                int num = new Random().nextInt(100);// 生成随机数
                System.out.println(Thread.currentThread().getName() + " : " + num);
                return num;
            }, 1, TimeUnit.SECONDS);

            System.out.println(result.get());
        }

        pool.shutdown();
    }

}
