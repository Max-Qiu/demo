package com.maxqiu.demo.CompareAndSwap;

/**
 * @author Max_Qiu
 */
public class CompareAndSwap {
    private int value;

    // 获取内存值
    public synchronized int get() {
        return value;
    }

    // 比较
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;

        if (oldValue == expectedValue) {
            this.value = newValue;
        }

        return oldValue;
    }

    // 设置
    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }
}