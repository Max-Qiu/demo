package com.maxqiu.demo.P01_Singleton.Mode2_LazyMan.demo1;

/**
 * @author Max_Qiu
 */
public class Test {

    public static void main(String[] args) {
        // 多次运行，有时打印的hashCode不一致
        new Thread(() -> System.out.println(Singleton.getInstance().hashCode())).start();
        new Thread(() -> System.out.println(Singleton.getInstance().hashCode())).start();
    }

}

// 懒汉式（线程不安全）
class Singleton {

    private Singleton() {}

    private static Singleton instance;

    // 提供一个静态的公有方法，当使用到该方法时，才去创建 instance ，即懒汉式
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

}
