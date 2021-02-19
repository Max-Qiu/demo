package com.maxqiu.demo.P01_Singleton.Other;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 网上看到的一种方法，但是不能解决反射的问题
 * 
 * @author Max_Qiu
 */
public class Test {

    public static void main(String[] args)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 1. 先使用反射
        // 获得构造器
        Constructor con = Singleton.class.getDeclaredConstructor();
        // 设置为可访问
        con.setAccessible(true);
        // 构造对象
        Singleton instance2 = (Singleton)con.newInstance();
        System.out.println("instance2.hashCode=" + instance2.hashCode());
        // 2. 再使用普通获取实例的静态方法
        Singleton instance1 = Singleton.getInstance();
        System.out.println("instance1.hashCode=" + instance1.hashCode());
    }

}

class Singleton {

    private static volatile Singleton instance;

    // 添加一个是否已经实例化的判断变量
    private static volatile boolean created = false;

    private Singleton() {
        synchronized (Singleton.class) {
            if (created) {
                // 如果已经实例化，则抛出异常
                throw new RuntimeException("单例只能创建一个");
            } else {
                // 如果未实例化，则允许
                created = true;
            }
        }
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

}
