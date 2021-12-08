package com.maxqiu.demo;

/**
 * 静态同步方法和实例同步方法组合，无效
 *
 * @author Max_Qiu
 */
public class Synchronized4 {
    public static void main(String[] args) {
        Number number = new Number();
        // 创建两个线程，分别调用类静态同步方法和对象的实例同步方法
        Thread thread1 = new Thread(Number::synchronizedStaticAdd1, "Thread1");
        Thread thread2 = new Thread(number::synchronizedNormalAdd1, "Thread2");
        // 两个线程并行执行，无效锁
        thread1.start();
        thread2.start();
    }
}
