package com.maxqiu.demo.Callable;

import java.util.concurrent.Callable;

/**
 * @author Max_Qiu
 */
public class DemoCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            sum += i;
        }
        return sum;
    }
}

/*
class DemoRunnable implements Runnable {

    @Override
    public void run() {

    }
}*/
