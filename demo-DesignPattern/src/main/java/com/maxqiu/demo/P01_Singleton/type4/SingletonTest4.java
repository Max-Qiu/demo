package com.maxqiu.demo.P01_Singleton.type4;

/**
 * @author Max_Qiu
 */
public class SingletonTest4 {

    public static void main(String[] args) {
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance1 == instance2); // true
        System.out.println("instance1.hashCode=" + instance1.hashCode());
        System.out.println("instance2.hashCode=" + instance2.hashCode());
    }

}

// 懒汉式（同步方法，线程安全，效率低）
class Singleton {
    private static Singleton instance;

    private Singleton() {}

    // 使用同步方法，保证线程安全
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
