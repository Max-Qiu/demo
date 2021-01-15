package com.maxqiu.demo.P01_Singleton.type6;

/**
 * @author Max_Qiu
 */
public class SingletonTest6 {
    public static void main(String[] args) {
        new Thread(() -> System.out.println(Singleton.getInstance().hashCode())).start();
        new Thread(() -> System.out.println(Singleton.getInstance().hashCode())).start();
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance1 == instance2); // true
        System.out.println("instance1.hashCode=" + instance1.hashCode());
        System.out.println("instance2.hashCode=" + instance2.hashCode());
    }
}

// 懒汉式（双重检查）
class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    // 提供一个静态的公有方法，加入双重检查代码，解决线程安全问题，同时解决懒加载问，又保证了效率, 推荐使用！！！
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
            // 使用如下输出，多次运行可以发现有多个instance = null输出，但是获取的hashCode仍然一致
            // System.out.println("instance = null");
        }
        return instance;
    }
}
