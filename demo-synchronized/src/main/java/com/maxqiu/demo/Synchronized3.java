package com.maxqiu.demo;

/**
 * 仅静态同步方法，有效
 *
 * @author Max_Qiu
 */
public class Synchronized3 {
    public static void main(String[] args) {
        // 创建多个线程，分别调用同一类的同一静态同步方法和不同静态同步方法
        Thread thread1 = new Thread(Number::synchronizedStaticAdd1, "Thread1");
        Thread thread2 = new Thread(Number::synchronizedStaticAdd1, "Thread2");
        Thread thread3 = new Thread(Number::synchronizedStaticAdd2, "Thread3");
        // 多个线程有序执行（哪个线程先抢到锁哪个线执行）
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
