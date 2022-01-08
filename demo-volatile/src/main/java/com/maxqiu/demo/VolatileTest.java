package com.maxqiu.demo;

import org.junit.jupiter.api.Test;

/**
 * volatile 重要特性演示
 *
 * @author Max_Qiu
 */
public class VolatileTest {
    /**
     * 测试 普通 变量
     */
    @Test
    void test1() {
        Date date = new Date();

        // 第一个线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            // 暂停一会，模拟数据处理
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            date.normalNumAdd();
            System.out.println(Thread.currentThread().getName() + "\t update num : " + date.normalNum);
        }, "ThreadA").start();

        // 第二个线程（即当前线程）
        while (date.normalNum == 0) {
            // 一直等待，直到 normalNum 不为 0
        }

        System.out.println(Thread.currentThread().getName() + "\t over! num : " + date.normalNum);
    }

    /**
     * 测试 volatile 变量
     */
    @Test
    void test2() {
        Date date = new Date();

        // 第一个线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            // 暂停一会，模拟数据处理
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            date.volatileNumAdd();
            System.out.println(Thread.currentThread().getName() + "\t update num : " + date.volatileNum);
        }, "ThreadA").start();

        // 第二个线程（即当前线程）
        while (date.volatileNum == 0) {
            // 一直等待，直到 normalNum 不为 0
        }

        System.out.println(Thread.currentThread().getName() + "\t over! num : " + date.volatileNum);
    }

    /**
     * 不保证原子性
     */
    @Test
    void test3() {
        Date date = new Date();

        // 创建200个线程并发执行数据修改
        for (int i = 0; i < 200; i++) {
            new Thread(() -> {
                // 暂停一会，模拟数据处理
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                date.volatileNumAdd();
            }, "Thread" + i).start();
        }

        // 等待上面的200个线程全部执行完毕，再用 main 查看最终值
        // PS：java 运行后除了 main 线程还有一个 GC 线程，所以 >2 时说明上面的线程没有全部结束
        while (Thread.activeCount() > 2) {
            // 让出当前线程，给其他线程执行
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "\t over! num : " + date.volatileNum);
    }
}

class Date {
    int normalNum = 0;
    volatile int volatileNum = 0;

    public void normalNumAdd() {
        this.normalNum++;
    }

    public void volatileNumAdd() {
        this.volatileNum++;
    }
}
