package com.maxqiu.demo;

import java.lang.ref.SoftReference;

/**
 * 软引用
 *
 * @author Max_Qiu
 */
public class SoftReferenceDemo {
    public static void main(String[] args) {
        memoryEnough();
        System.out.println("======================");
        memoryNotEnough();
    }

    /**
     * 内存足够
     */
    public static void memoryEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;
        System.gc();

        System.out.println(softReference.get());
    }

    /**
     * 内存不足
     *
     * JVM配置，故意产生大对虾并配置小内存，使其内存不足导致 OOM 看软引用的回收情况
     *
     * -Xms1m -Xmx1m
     */
    public static void memoryNotEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;
        System.gc();

        try {
            byte[] bytes = new byte[30 * 1024 * 1024];
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println(softReference.get());
        }
    }
}
