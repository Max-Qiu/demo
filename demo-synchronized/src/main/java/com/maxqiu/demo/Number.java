package com.maxqiu.demo;

/**
 * 普通对象
 *
 * @author Max_Qiu
 */
public class Number {
    /**
     * 静态变量
     */
    private static int count = 0;

    /**
     * 实例方法
     */
    public void normalAdd() {
        for (int i = 0; i < 5; i++) {
            count++;
            System.out.println(Thread.currentThread().getName() + ":" + count);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 静态方法
     */
    public static void staticAdd() {
        for (int i = 0; i < 5; i++) {
            count++;
            System.out.println(Thread.currentThread().getName() + ":" + count);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 修饰实例方法
     *
     * 1. 多个线程调用 同一对象 的同步方法会阻塞 <br>
     * 2. 多个线程调用 不同对象 的同步方法不会阻塞
     */
    public synchronized void synchronizedNormalAdd1() {
        for (int i = 0; i < 5; i++) {
            count++;
            System.out.println(Thread.currentThread().getName() + ":" + count);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 3. 不同方法添加同步锁后，同一对象也会阻塞
     */
    public synchronized void synchronizedNormalAdd2() {
        for (int i = 0; i < 5; i++) {
            count++;
            System.out.println(Thread.currentThread().getName() + ":" + count);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 修饰静态方法
     *
     * 静态方法属于类不属于对象，此时 synchronized 同步锁称为对象锁
     */
    public synchronized static void synchronizedStaticAdd1() {
        for (int i = 0; i < 5; i++) {
            count++;
            System.out.println(Thread.currentThread().getName() + ":" + count);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 修饰另一个静态方法
     */
    public synchronized static void synchronizedStaticAdd2() {
        for (int i = 0; i < 5; i++) {
            count++;
            System.out.println(Thread.currentThread().getName() + ":" + count);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 修饰代码块(this|object)
     *
     * 这个 this 指当前对象
     */
    public void synchronizedBlockAdd1() {
        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                count++;
                System.out.println(Thread.currentThread().getName() + ":" + count);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 修饰代码块(Obj.class)
     *
     * 这个 Number.class 指当前类
     */
    public void synchronizedBlockAdd2() {
        synchronized (Number.class) {
            for (int i = 0; i < 5; i++) {
                count++;
                System.out.println(Thread.currentThread().getName() + ":" + count);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
