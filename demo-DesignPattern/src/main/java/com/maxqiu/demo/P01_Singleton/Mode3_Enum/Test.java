package com.maxqiu.demo.P01_Singleton.Mode3_Enum;

/**
 * @author Max_Qiu
 */
public class Test {

    public static void main(String[] args) {
        Singleton instance1 = Singleton.INSTANCE;
        Singleton instance2 = Singleton.INSTANCE;
        System.out.println(instance1 == instance2);
        System.out.println(instance1.hashCode());
        System.out.println(instance2.hashCode());
        instance1.sayOk();
    }

}

// 枚举
enum Singleton {

    INSTANCE; // 属性

    public void sayOk() {
        System.out.println("ok~");
    }

}
