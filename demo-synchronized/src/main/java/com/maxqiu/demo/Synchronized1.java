package com.maxqiu.demo;

/**
 * 同一对象，仅实例同步方法，有效
 *
 * @author Max_Qiu
 */
public class Synchronized1 {
    public static void main(String[] args) {
        // 创建一个对象
        Number number = new Number();
        // 创建多个线程，分别调用同一对象的实例同步方法和不同实例同步方法
        Thread thread1 = new Thread(number::synchronizedNormalAdd1, "Thread1");
        Thread thread2 = new Thread(number::synchronizedNormalAdd2, "Thread2");
        Thread thread3 = new Thread(number::synchronizedNormalAdd1, "Thread3");
        // 多个线程有序执行（哪个线程先抢到锁哪个线执行）
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
