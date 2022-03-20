> 参考视频：尚硅谷：[互联网大厂高频重点面试题（第2季）](http://www.atguigu.com/download_detail.shtml?v=130)

# 强引用

当内存不足，`JVM` 开始垃圾回收，对于强引用的对象，就算是出现了 `OOM` 也不会对该对象进行回收。

强引用是最常见的普通对象引用，只要还有强引用指向一个对象，就能表明对象还“活着”，垃圾收集器不会碰这种对象。在 `Java` 中最常见的就是强引用，把一个对象赋给一个引用变量，这个引用变量就是一个强引用。当一个对象被强引用变量引用时，它处于可达状态，它是不可能被垃圾回收机制回收的，即使该对象以后永远都不会被用到 `JVM` 也不会回收。因此强引用是造成 `Java` 内存泄漏的主要原因之一。

对于一个普通的对象，如果没有其他的引用关系，只要超过了引用的作用域或者显式地将相应（强）引用赋值为 `null`，一般认为就是可以被垃圾收集的了（当然具体回收时机还是要看垃圾收集策略）。

```java
public class StrongReferenceDemo {
    public static void main(String[] args) {
        // 这样的定义默认就是强引用
        Object o1 = new Object();
        // o2 引用复制
        Object o2 = o1;
        // o1 置空
        o1 = null;
        // GC
        System.gc();
        // 查看 o2
        System.out.println(o2);
    }
}
```

```bash
java.lang.Object@61bbe9ba
```

# 软引用

软引用是一种相对强引用弱化了一些的引用，需要用 `java.lang.ref.SoftReference` 类来实现，可以让对象豁免一些垃圾收集。

对于只有软引用的对象来说：

- 当系统内存充足时它不会被回收
- 当系统内存不足时它会被回收。

软引用通常用在对内存敏感的程序中，比如高速缓存就有用到软引用，内存够用的时候就保留，不够用就回收！

```java
public class SoftReferenceDemo {
    public static void main(String[] args) {
        memoryEnough();
        System.out.println("======================");
        memoryNotEnough();
    }

    /**
     * 内存足够
     */
    public static void memoryEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;
        System.gc();

        System.out.println(softReference.get());
    }

    /**
     * 内存不足
     *
     * JVM配置，故意产生大对虾并配置小内存，使其内存不足导致 OOM 看软引用的回收情况
     *
     * -Xms1m -Xmx1m
     */
    public static void memoryNotEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;
        System.gc();

        try {
            byte[] bytes = new byte[30 * 1024 * 1024];
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println(softReference.get());
        }
    }
}
```

```
java.lang.Object@61bbe9ba
java.lang.Object@61bbe9ba
java.lang.Object@61bbe9ba
======================
java.lang.Object@610455d6
java.lang.Object@610455d6
java.lang.OutOfMemoryError: Java heap space
	at com.maxqiu.demo.SoftReferenceDemo.memoryNotEnough(SoftReferenceDemo.java:49)
	at com.maxqiu.demo.SoftReferenceDemo.main(SoftReferenceDemo.java:14)
null
```

# 弱引用

弱引用需要用 `java.lang.ref.WeakReference` 类来实现，它比软引用的生存期更短，对于只有弱引用的对象来说，只要垃圾回收机制一运行，不管 `JVM` 的内存空间是否足够，都会回收该对象占用的内存。

```java
public class WeakReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o1);
        System.out.println(weakReference.get());

        o1 = null;
        System.gc();

        System.out.println(weakReference.get());
    }
}
```

```
java.lang.Object@61bbe9ba
null
```

## 软引用和弱引用的使用厂家

假如有一个应用需要读取大量的本地图片：

- 如果每次读取图片都从硬盘读取则会严重影响性能,
- 如果一次性全部加载到内存中又可能造成内存溢出。

此时使用软引用可以解决这个问题。

设计思路是：用一个 `HashMap` 来保存图片的路径和相应图片对象关联的软引用之间的映射关系，在内存不足时，`JVM` 会自动回收这些缓存图片对象所占用的空间，从而有效地避免了 `OOM` 的问题。

`Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();`

# 虚引用

虚引用需要 `java.lang.ref.PhantomReference` 类来实现。

顾名思义，就是形同虚设，与其他几种引用都不同，虚引用并不会决定对象的生命周期。**如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收**。它不能单独使用也不能通过它访问对象，虚引用必须和引用队列 `ReferenceQueue` 联合使用。

虚引用的主要作用是跟踪对象被垃圾回收的状态。仅仅是提供了一种确保对象被 `finalize` 以后，做某些事情的机制。`PhantomReference` 的 `get` 方法总是返回 `null` ，因此无法访问对应的引用对象。其意义在于说明一个对象已经进入 `finalization`阶段，可以被 `gc` 回收，用来实现比 `finalization` 机制更灵活的回收操作。

换句话说，设置虚引用关联的唯一目的，就是在这个对象被收集器回收的时候收到一个系统通知或者后续添加进一步的处理。`Java` 技术允许使用 `finalize()` 方法在垃圾收集器将对象从内存中清除出去之前做必要的清理工作。

## ReferenceQueue

`Java` 提供了4种引用类型，在垃圾回收的时候，都有自己各自的特点。

`ReferenceQueue` 是用来配合引用工作的，没有 `ReferenceQueue` 一样可以运行。创建引用的时候可以指定关联的队列，当 `GC` 释放对象内存的时候，会将引用加入到引用队列，如果程序发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动，这相当于是一种通知机制。

当关联的引用队列中有数据的时候，意味着引用指向的堆内存中的对象被回收。通过这种方式，`JVM` 允许我们在对象被销毁后，做一些自己想做的事情。


```java
public class ReferenceQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        WeakReference<Object> weakReference = new WeakReference<>(o1, referenceQueue);

        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("==============");
        o1 = null;
        System.gc();

        Thread.sleep(500);

        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());
    }
}
```

```
java.lang.Object@61bbe9ba
null
==============
null
java.lang.ref.WeakReference@610455d6
```

## PhantomReference

```java
public class PhantomReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(o1, referenceQueue);

        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("===================");
        o1 = null;
        System.gc();
        Thread.sleep(500);

        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
    }
}
```

```bash
null
null
===================
null
java.lang.ref.PhantomReference@61bbe9ba
```
