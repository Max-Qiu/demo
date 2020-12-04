package com.maxqiu.demo.CompareAndSwap;

/**
 *
 * @author Max_Qiu
 */
public class TestCompareAndSwap {

    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                int expectedValue = cas.get();
                boolean b = cas.compareAndSet(expectedValue, (int)(Math.random() * 101));
                System.out.println(b);
            }).start();
        }
    }

}