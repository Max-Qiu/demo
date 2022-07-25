package com.maxqiu.demo;

/**
 * 可重入锁
 *
 * @author Max_Qiu
 */
public class Reentrant {
    public static void main(String[] args) {
        Object o = new Object();
        new Thread(() -> {
            synchronized (o) {
                System.out.println(Thread.currentThread().getName() + " 外层");
                synchronized (o) {
                    System.out.println(Thread.currentThread().getName() + " 中层");
                    synchronized (o) {
                        System.out.println(Thread.currentThread().getName() + " 内层");
                    }
                }
            }
        }, "t1").start();
    }
}
