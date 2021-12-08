package com.maxqiu.demo;

/**
 * 同一对象，实例方法与静态方法，不加锁，出现问题
 *
 * @author Max_Qiu
 */
public class Synchronized0 {
    public static void main(String[] args) {
        // 创建一个对象
        Number number = new Number();
        // 创建多个线程实例操作同一个对象，不添加同步锁
        Thread thread1 = new Thread(number::normalAdd, "Thread1");
        Thread thread2 = new Thread(number::normalAdd, "Thread2");
        Thread thread3 = new Thread(Number::staticAdd, "Thread3");
        // 多个线程同时执行，出现问题，最终 count 结果不正确
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
