package com.maxqiu.demo.CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author Max_Qiu
 */
public class LatchRunnable implements Runnable {
    private CountDownLatch latch;

    public LatchRunnable(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 50000; i++) {
                if (i % 100 == 0) {
                    System.out.println(i);
                }
            }
        } finally {
            latch.countDown();
        }
    }
}
