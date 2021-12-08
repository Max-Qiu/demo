package com.maxqiu.demo;

/**
 * 不同对象，实例方法含代码块同步(class)与静态同步方法，有效
 *
 * @author Max_Qiu
 */
public class Synchronized6 {
    public static void main(String[] args) {
        Number number1 = new Number();
        Number number2 = new Number();
        // 创建多个线程，调用实例方法含代码块同步(class)与静态同步方法
        Thread thread1 = new Thread(number1::synchronizedBlockAdd2, "Thread1");
        Thread thread2 = new Thread(number2::synchronizedBlockAdd2, "Thread2");
        Thread thread3 = new Thread(Number::synchronizedStaticAdd1, "Thread3");
        // 多个线程有序执行（哪个线程先抢到锁哪个线执行）
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
