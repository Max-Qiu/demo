package com.maxqiu.demo.Synchronized;

/**
 * @author Max_Qiu
 */
public class Number {

    public synchronized void getOne() {// Number.class
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("one");
    }

    public synchronized void getTwo() {// this
        System.out.println("two");
    }

    public void getThree() {
        System.out.println("three");
    }

}
