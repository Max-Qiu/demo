package com.maxqiu.demo;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用
 *
 * @author Max_Qiu
 */
public class PhantomReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(o1, referenceQueue);

        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("===================");
        o1 = null;
        System.gc();
        Thread.sleep(500);

        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
    }
}
