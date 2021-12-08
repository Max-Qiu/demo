> 参考文档

- JavaGuide：[Java 并发常见知识点&面试题总结（进阶篇）](https://javaguide.cn/java/concurrent/java并发进阶常见面试题总结/)
- 视频课程：[尚硅谷_JUC线程高级](http://www.atguigu.com/download_detail.shtml?v=7)

# 什么是 `synchronized` 关键字

`synchronized` 关键字解决的是多个线程之间访问资源的同步性，`synchronized`关键字可以保证被它修饰的方法或者代码块在任意时刻只能有一个线程执行。

# 修饰范围

- 修饰实例方法
- 修饰静态方法
- 修饰代码块

# 示例

## 实体

建立一个普通对象，添加

```java
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
```

## 情况0：同一对象，实例方法与静态方法，不加锁，出现问题

```java
public class Synchronized0 {
    public static void main(String[] args) {
        // 创建一个对象
        Number number = new Number();
        // 创建多个线程实例操作同一个对象，不添加同步锁
        Thread thread1 = new Thread(number::normalAdd, "Thread1");
        Thread thread2 = new Thread(number::normalAdd, "Thread2");
        Thread thread3 = new Thread(Number::staticAdd, "Thread3");
        // 多个线程同时执行，出现问题，最终 count 结果不正确
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
```

## 情况1：同一对象，仅实例同步方法，有效

```java
public class Synchronized1 {
    public static void main(String[] args) {
        // 创建一个对象
        Number number = new Number();
        // 创建多个线程，分别调用同一对象的实例同步方法和不同实例同步方法
        Thread thread1 = new Thread(number::synchronizedNormalAdd1, "Thread1");
        Thread thread2 = new Thread(number::synchronizedNormalAdd2, "Thread2");
        Thread thread3 = new Thread(number::synchronizedNormalAdd1, "Thread3");
        // 多个线程有序执行（哪个线程先抢到锁哪个线执行）
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
```

## 情况2：多个对象，仅实例同步方法，无效

```java
public class Synchronized2 {
    public static void main(String[] args) {
        // 创建多对象
        Number number1 = new Number();
        Number number2 = new Number();
        Number number3 = new Number();
        // 创建多个线程，分别调用不同对象的同一实例同步方法和不同实例同步方法
        Thread thread1 = new Thread(number1::synchronizedNormalAdd1, "Thread1");
        Thread thread2 = new Thread(number2::synchronizedNormalAdd1, "Thread2");
        Thread thread3 = new Thread(number3::synchronizedNormalAdd2, "Thread3");
        // 多个线程并行执行，无效锁
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
```

## 情况3：仅静态同步方法，有效

```java
public class Synchronized3 {
    public static void main(String[] args) {
        // 创建多个线程，分别调用同一类的同一静态同步方法和不同静态同步方法
        Thread thread1 = new Thread(Number::synchronizedStaticAdd1, "Thread1");
        Thread thread2 = new Thread(Number::synchronizedStaticAdd1, "Thread2");
        Thread thread3 = new Thread(Number::synchronizedStaticAdd2, "Thread3");
        // 多个线程有序执行（哪个线程先抢到锁哪个线执行）
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
```

## 情况4：静态同步方法和实例同步方法组合，无效

```java
public class Synchronized4 {
    public static void main(String[] args) {
        Number number = new Number();
        // 创建两个线程，分别调用类静态同步方法和对象的实例同步方法
        Thread thread1 = new Thread(Number::synchronizedStaticAdd1, "Thread1");
        Thread thread2 = new Thread(number::synchronizedNormalAdd1, "Thread2");
        // 两个线程并行执行，无效锁
        thread1.start();
        thread2.start();
    }
}
```

## 情况5：同一对象，实例方法含代码块同步(this)与实例同步方法，有效

```java
public class Synchronized5 {
    public static void main(String[] args) {
        Number number = new Number();
        // 创建多个线程，调用实例方法含代码块同步(this)与实例同步方法
        Thread thread1 = new Thread(number::synchronizedBlockAdd1, "Thread1");
        Thread thread2 = new Thread(number::synchronizedBlockAdd1, "Thread2");
        Thread thread3 = new Thread(number::synchronizedNormalAdd1, "Thread3");
        // 多个线程有序执行（哪个线程先抢到锁哪个线执行）
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
```

## 情况6：不同对象，实例方法含代码块同步(class)与静态同步方法，有效

```java
public class Synchronized6 {
    public static void main(String[] args) {
        Number number1 = new Number();
        Number number2 = new Number();
        // 创建多个线程，调用实例方法含代码块同步(class)与静态同步方法
        Thread thread1 = new Thread(number1::synchronizedBlockAdd2, "Thread1");
        Thread thread2 = new Thread(number2::synchronizedBlockAdd2, "Thread2");
        Thread thread3 = new Thread(Number::synchronizedStaticAdd1, "Thread3");
        // 多个线程有序执行（哪个线程先抢到锁哪个线执行）
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
```

# 总结

- `synchronized` 关键字加到 `static` 静态方法和 `synchronized(class)` 代码块上都是是给 Class 类上锁。
- `synchronized` 关键字加到实例方法上是给对象实例上锁。
- 尽量不要使用 `synchronized(String a)` 因为 `JVM` 中，字符串常量池具有缓存功能！

# 实战：单例模式（双重校验锁实现对象单例（线程安全））

```java
/**
 * 单例模式
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
```
