package com.maxqiu.demo.Mode2_WorkQueues;

/**
 * @author Max_Qiu
 */
public class SleepUtils {
    public static void sleep(int second) {
        try {
            Thread.sleep(1000L * second);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
