package com.maxqiu.demo.Volatile;

/**
 * Volatile关键字<br>
 * 当多个线程进行操作共享数据时，可以保证内存中的数据可见。 <br>
 * 相较于 synchronized 是一种较为轻量级的同步策略。<br>
 * <br>
 * 注意： <br>
 * 1. volatile 不具备“互斥性” <br>
 * 2. volatile 不能保证变量的“原子性”
 * 
 * @author Max_Qiu
 */
public class TestVolatile {

    public static void main(String[] args) {
        VolatileRunnable threadDemo = new VolatileRunnable();
        new Thread(threadDemo).start();
        while (true) {
            if (threadDemo.isFlag()) {
                System.out.println(" ------------- ");
                break;
            }
        }
    }
}
