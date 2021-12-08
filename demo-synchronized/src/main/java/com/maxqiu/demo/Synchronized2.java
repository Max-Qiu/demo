package com.maxqiu.demo;

/**
 * 多个对象，仅实例同步方法，无效
 *
 * @author Max_Qiu
 */
public class Synchronized2 {
    public static void main(String[] args) {
        // 创建多对象
        Number number1 = new Number();
        Number number2 = new Number();
        Number number3 = new Number();
        // 创建多个线程，分别调用不同对象的同一实例同步方法和不同实例同步方法
        Thread thread1 = new Thread(number1::synchronizedNormalAdd1, "Thread1");
        Thread thread2 = new Thread(number2::synchronizedNormalAdd1, "Thread2");
        Thread thread3 = new Thread(number3::synchronizedNormalAdd2, "Thread3");
        // 多个线程并行执行，无效锁
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
