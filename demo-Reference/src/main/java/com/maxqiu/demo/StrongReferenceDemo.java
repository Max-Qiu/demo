package com.maxqiu.demo;

/**
 * 强引用
 *
 * @author Max_Qiu
 */
public class StrongReferenceDemo {
    public static void main(String[] args) {
        // 这样的定义默认就是强引用
        Object o1 = new Object();
        // o2 引用复制
        Object o2 = o1;
        // o1 置空
        o1 = null;
        // GC
        System.gc();
        // 查看 o2
        System.out.println(o2);
    }
}
