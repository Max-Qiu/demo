package com.maxqiu.demo;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

/**
 * 测试 volatile 不保证原子性，以及 Atomic 保证原子性
 *
 * @author Max_Qiu
 */
public class AtomicTest {
    @Test
    void test() {
        Date date = new Date();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    date.volatileNumAdd();
                    date.atomicNumAdd();
                }
            }, i + "").start();
        }

        while (Thread.activeCount() > 2) {
            // 让出当前线程，给其他线程执行
            Thread.yield();
        }

        System.out.println("over! volatile:\t" + date.volatileNum);
        System.out.println("over! atomic:\t" + date.atomicNum);
    }
}

class Date {
    volatile int volatileNum = 0;
    AtomicInteger atomicNum = new AtomicInteger(0);

    public void volatileNumAdd() {
        this.volatileNum++;
    }

    public void atomicNumAdd() {
        atomicNum.getAndIncrement();
    }
}
