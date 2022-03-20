package com.maxqiu.demo;

import java.lang.ref.WeakReference;

/**
 * 弱引用
 *
 * @author Max_Qiu
 */
public class WeakReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o1);
        System.out.println(weakReference.get());

        o1 = null;
        System.gc();

        System.out.println(weakReference.get());
    }
}
