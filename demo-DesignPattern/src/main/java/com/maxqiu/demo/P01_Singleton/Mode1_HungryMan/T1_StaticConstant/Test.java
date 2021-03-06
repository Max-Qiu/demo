package com.maxqiu.demo.P01_Singleton.Mode1_HungryMan.T1_StaticConstant;

/**
 * @author Max_Qiu
 */
public class Test {

    public static void main(String[] args) {
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance1 == instance2); // true
        System.out.println("instance1.hashCode=" + instance1.hashCode());
        System.out.println("instance2.hashCode=" + instance2.hashCode());
    }

}

// 饿汉式（静态常量）
class Singleton {

    // 1. 构造器私有化, 外部能new
    private Singleton() {}

    // 2. 本类内部创建对象实例
    private static final Singleton INSTANCE = new Singleton();

    // 3. 提供一个公有的静态方法，返回实例对象
    public static Singleton getInstance() {
        return INSTANCE;
    }

}
