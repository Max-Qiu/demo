package com.maxqiu.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Max_Qiu
 */
public class CopyAndSwapTest {
    public static void main(String[] args) {
        AtomicInteger num = new AtomicInteger(5);
        System.out.println(num.compareAndSet(5, 2019) + "\t" + num.get());
        System.out.println(num.compareAndSet(5, 2020) + "\t" + num.get());
    }
}
