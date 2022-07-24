package com.maxqiu.demo.Lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * +1 -1
 *
 * @author Max_Qiu
 */
public class IncrDecr {
    public static void main(String[] args) {
        Share share = new Share();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "DD").start();
    }

    // 第一步 创建资源类，定义属性和操作方法
    private static class Share {
        private int number = 0;

        // 创建Lock
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        // +1
        public void incr() throws InterruptedException {
            // 上锁
            lock.lock();
            try {
                // 判断
                while (number != 0) {
                    condition.await();
                }
                // 干活
                number++;
                System.out.println(Thread.currentThread().getName() + " :: " + number);
                // 通知
                condition.signalAll();
            } finally {
                // 解锁
                lock.unlock();
            }
        }

        // -1
        public void decr() throws InterruptedException {
            lock.lock();
            try {
                while (number != 1) {
                    condition.await();
                }
                number--;
                System.out.println(Thread.currentThread().getName() + " :: " + number);
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
}
