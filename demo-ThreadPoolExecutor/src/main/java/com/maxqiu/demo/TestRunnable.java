package com.maxqiu.demo;

import java.time.LocalDateTime;

/**
 * @author Max_Qiu
 */
public class TestRunnable implements Runnable {
    private final Integer id;

    public TestRunnable(Integer id) {
        super();
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println(LocalDateTime.now() + "Thread: " + id + " start");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(LocalDateTime.now() + "Thread: " + id + ": end");
    }
}
