package com.maxqiu.demo.CyclicBarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier （循环栅栏）示例
 *
 * @author Max_Qiu
 */
public class CyclicBarrierDemo {

    // 创建固定值
    private static final int NUMBER = 7;

    public static void main(String[] args) {
        // 创建CyclicBarrier
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER, () -> {
            System.out.println("*****集齐7颗龙珠就可以召唤神龙");
        });

        // 集齐 7 颗龙珠过程
        for (int i = 1; i <= NUMBER; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 星龙被收集");
                    // 等待
                    cyclicBarrier.await();
                    // CyclicBarrier 执行完成后的任务
                    System.out.println(Thread.currentThread().getName() + " 星龙被使用");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
