package com.maxqiu.demo.ThreadPool;

/**
 * @author Max_Qiu
 */
public class ThreadPoolDemo implements Runnable {

    private int i = 0;

    @Override
    public void run() {
        while (i <= 100) {
            System.out.println(Thread.currentThread().getName() + " : " + i++);
        }
    }

}
