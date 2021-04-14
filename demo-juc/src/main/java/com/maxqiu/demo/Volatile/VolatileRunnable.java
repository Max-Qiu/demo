package com.maxqiu.demo.Volatile;

/**
 * @author Max_Qiu
 */
public class VolatileRunnable implements Runnable {

    private volatile boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        flag = true;

        System.out.println("Flag:" + isFlag());
    }
}