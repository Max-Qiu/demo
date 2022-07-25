package com.maxqiu.demo.Lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock演示可重入锁
 *
 * @author Max_Qiu
 */
public class Reentrant {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        // 创建线程
        new Thread(() -> {
            try {
                // 上锁
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " 外层");
                try {
                    // 上锁
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " 内层");
                } finally {
                    // 释放锁
                    lock.unlock();
                }
            } finally {
                // 释放锁
                lock.unlock();
            }
        }, "t1").start();
        // 创建新线程
        new Thread(() -> {
            lock.lock();
            System.out.println("aaaa");
            lock.unlock();
        }, "t2").start();
    }
}
