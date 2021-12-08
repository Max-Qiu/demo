package com.maxqiu.demo;

/**
 * 同一对象，实例方法含代码块同步(this)与实例同步方法，有效
 *
 * @author Max_Qiu
 */
public class Synchronized5 {
    public static void main(String[] args) {
        Number number = new Number();
        // 创建多个线程，调用实例方法含代码块同步(this)与实例同步方法
        Thread thread1 = new Thread(number::synchronizedBlockAdd1, "Thread1");
        Thread thread2 = new Thread(number::synchronizedBlockAdd1, "Thread2");
        Thread thread3 = new Thread(number::synchronizedNormalAdd1, "Thread3");
        // 多个线程有序执行（哪个线程先抢到锁哪个线执行）
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
