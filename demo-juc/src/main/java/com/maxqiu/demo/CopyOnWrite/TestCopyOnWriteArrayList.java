package com.maxqiu.demo.CopyOnWrite;

/**
 * @author Max_Qiu
 */
public class TestCopyOnWriteArrayList {
    public static void main(String[] args) {
        ListRunnable ht = new ListRunnable();

        for (int i = 0; i < 10; i++) {
            new Thread(ht).start();
        }
    }
}
