> 参考教程

- [Java 并发常见知识点&面试题总结（进阶篇）](https://javaguide.cn/java/concurrent/java并发进阶常见面试题总结/)
- [尚硅谷_互联网大厂高频重点面试题（第2季）](http://www.atguigu.com/download_detail.shtml?v=130)

# 简介

`volatile`是`Java`虚拟机提供的轻量级的同步机制

先要从 **CPU 缓存模型** 说起！

## CPU 缓存模型

> 简介

众所周知，`CPU`从内存中读取数据进行处理，而`CPU`的处理速度很快，所以就有一个叫`CPU 缓存`的东西，用来解决`CPU`处理速度和内存处理速度不对等的问题。如下图，使用 [CPU-Z](https://www.cpuid.com/softwares/cpu-z.html) 这款软件就可以看到`CPU`的缓存大小

![](https://cdn.maxqiu.com/upload/60ea4c829d534703bce7d0e841e3f86e.jpg)

> CPU 缓存的工作方式

下图是一个简单的`CPU`缓存示意图（实际上，现代的`CPU`缓存通常分为三层，分别叫 `L1`,`L2`,`L3`缓存）

![](https://cdn.maxqiu.com/upload/23372721c32b468e9f636154b159e11d.png)

`CPU`工作时先复制一份数据到缓存中，使用时直接从缓存中读取数据，当运算完成后再将结果写回内存中。但是，这样存在**内存缓存不一致性**的问题！比如两个线程同时执行，从缓存中读取的`i=1`，两个线程做了`1++`运算完之后再写回内存`i=2`，而正确结果应该是`i=3`。

`CPU`为了解决内存缓存不一致性问题可以通过制定缓存一致协议或者其他手段来解决。

## JMM 内存模型

`JMM`（`Java`内存模型`Java Memory Model`，简称`JMM`）本身是一种抽象的概念并不是真实存在，它描述的是一组规定或则规范，通过这组规范定义了程序中的访问方式。

> `JMM`同步规定

- 线程解锁前，必须把共享变量的值刷新回主内存
- 线程加锁前，必须读取主内存的最新值到自己的本地内存
- 加锁解锁是同一把锁

由于`JVM`运行程序的实体是线程，而每个线程创建时`JVM`都会为其创建一个本地内存，本地内存是每个线程的私有数据区域，而`Java`内存模型中规定所有变量储存在主内存，主内存是共享内存区域，所有的线程都可以访问，但线程对变量的操作（读取赋值等）必须都本地内存进行看。首先要将变量从主内存拷贝的自己的本地内存空间，然后对变量进行操作，操作完成后再将变量写回主内存，不能直接操作主内存中的变量，本地内存中存储着主内存中的变量副本拷贝，前面说过，本地内存是每个线程的私有数据区域，因此不同的线程间无法访问对方的工作内存，线程间的通信（传值）必须通过主内存来完成。

内存模型如下：

![](https://cdn.maxqiu.com/upload/eeddf842b4484578ae7ccea2ad9f79fe.png)

## 并发编程的三个重要特性

- **原子性**：一个的操作或者多次操作，要么所有的操作全部都得到执行并且不会受到任何因素的干扰而中断，要么所有的操作都执行，要么都不执行。
- **可见性**：当一个线程对共享变量进行了修改，那么另外的线程都是立即可以看到修改后的最新值。
- **有序性**：代码在执行的过程中的先后顺序，Java 在编译器以及运行期间的优化，代码的执行顺序未必就是编写代码时候的顺序。

# volatile 的特性

- 保证可见性
- **不**保证原子性
- 禁止指令重排

## 验证：保证可见性

新建一个对象，包含普通变量和`volatile`修饰的变量以及对应的修改方法

```java
class Date {
    int normalNum = 0;
    volatile int volatileNum = 0;

    public void normalNumAdd() {
        this.normalNum++;
    }

    public void volatileNumAdd() {
        this.volatileNum++;
    }
}
```

> 测试普通变量

```java
@Test
void test1() {
    Date date = new Date();

    // 第一个线程
    new Thread(() -> {
        System.out.println(Thread.currentThread().getName() + "\t come in");
        // 暂停一会，模拟数据处理
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        date.normalNumAdd();
        System.out.println(Thread.currentThread().getName() + "\t update num : " + date.normalNum);
    }, "ThreadA").start();

    // 第二个线程（即当前线程）
    while (date.normalNum == 0) {
        // 一直等待，直到 normalNum 不为 0
    }

    System.out.println(Thread.currentThread().getName() + "\t over! num : " + date.normalNum);
}
```

执行时有如下输出，但是线程一直在执行，且不会有最后的输出语句。说明：普通变量在第一个线程修改之后第二个线程并不会刷新值

```
ThreadA	 come in
ThreadA	 update num : 1
```

> 测试`volatile`修饰的变量

```java
@Test
void test2() {
    Date date = new Date();

    // 第一个线程
    new Thread(() -> {
        System.out.println(Thread.currentThread().getName() + "\t come in");
        // 暂停一会，模拟数据处理
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        date.volatileNumAdd();
        System.out.println(Thread.currentThread().getName() + "\t update num : " + date.volatileNum);
    }, "ThreadA").start();

    // 第二个线程（即当前线程）
    while (date.volatileNum == 0) {
        // 一直等待，直到 normalNum 不为 0
    }

    System.out.println(Thread.currentThread().getName() + "\t over! num : " + date.volatileNum);
}
```

执行时有如下输出，第一个线程更新完成后，第二个线程立即有最后的输出语句。说明：`volatile`关键字保证了可见性

```
ThreadA	 come in
ThreadA	 update num : 1
main	 over! num : 1
```

## 验证：不保证原子性

> 创建多个线程并发操作一个数据

```java
@Test
void test3() {
    Date date = new Date();

    // 创建200个线程并发执行数据修改
    for (int i = 0; i < 200; i++) {
        new Thread(() -> {
            // 暂停一会，模拟数据处理
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            date.volatileNumAdd();
        }, "Thread" + i).start();
    }

    // 等待上面的200个线程全部执行完毕，再用 main 查看最终值
    // PS：java 运行后除了 main 线程还有一个 GC 线程，所以 >2 时说明上面的线程没有全部结束
    while (Thread.activeCount() > 2) {
        // 让出当前线程，给其他线程执行
        Thread.yield();
    }

    System.out.println(Thread.currentThread().getName() + "\t over! num : " + date.volatileNum);
}
```

执行后发现最终结果可能不为200。说明：`volatile`关键字不保证原子性

## 验证：禁止指令重排

参考文章：[volatile为什么能禁止指令重排](https://www.chuckfang.com/2020/07/05/volatile/)
