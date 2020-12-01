package com.maxqiu.demo.Atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Max_Qiu
 */
public class AtomicRunnable implements Runnable {

    // private volatile int serialNumber = 0;

    private AtomicInteger serialNumber = new AtomicInteger();

    public void setSerialNumber(AtomicInteger serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getSerialNumber() {
        // return serialNumber++;
        return serialNumber.getAndIncrement();
    }

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
    }
}