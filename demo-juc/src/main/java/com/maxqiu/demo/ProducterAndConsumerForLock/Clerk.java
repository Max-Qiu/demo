package com.maxqiu.demo.ProducterAndConsumerForLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 店员
 * 
 * @author Max_Qiu
 */
public class Clerk {
    private int product = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * 进货
     */
    public void get() {
        lock.lock();

        try {
            // 为了避免虚假唤醒问题，应该总是使用在循环中
            while (product >= 2) {
                System.out.println("产品已满！");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(Thread.currentThread().getName() + " : " + ++product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }

    /**
     * 卖货
     */
    public void sale() {
        lock.lock();

        try {
            while (product <= 0) {
                System.out.println("缺货！");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + --product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
