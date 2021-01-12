package com.maxqiu.demo.P01_Singleton.type8;

/**
 * @author Max_Qiu
 */
public class SingletonTest8 {

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
