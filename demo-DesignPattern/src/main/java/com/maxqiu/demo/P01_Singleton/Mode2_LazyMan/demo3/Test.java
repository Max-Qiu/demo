package com.maxqiu.demo.P01_Singleton.Mode2_LazyMan.demo3;

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

// 懒汉式（同步代码块，线程不安全）
class Singleton {

    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            // 同步方法修改为同步代码块，不能解决问题
            synchronized (Singleton.class) {
                instance = new Singleton();
            }
        }
        return instance;
    }

}
