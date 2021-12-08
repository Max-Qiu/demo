package com.maxqiu.demo;

/**
 * 单例模式
 *
 * @author Max_Qiu
 */
public class Singleton {
    private volatile static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        // 先判断对象是否已经实例过，没有实例化过才进入加锁代码
        if (instance == null) {
            // 类对象加锁
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
