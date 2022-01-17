> 参考教程

- `JavaGuide`：[Java 并发常见知识点&面试题总结（进阶篇）](https://javaguide.cn/java/concurrent/java并发进阶常见面试题总结/)
- `尚硅谷`：[互联网大厂高频重点面试题（第2季）](http://www.atguigu.com/download_detail.shtml?v=130)

---

上文说道 [volatile 关键字](https://maxqiu.com/article/detail/127) 不保证原子性，那么在不使用 [synchronized 关键字](https://maxqiu.com/article/detail/125) 的情况下如何解决多线程安全问题呢？就是本文的主角：**`Atomic`原子类**

# 简介

`Atomic` 中文是原子的意思。在这里指一个操作是不可中断的。即使是在多个线程一起执行的时候，一个操作一旦开始，就不会被其他线程干扰。

所以：**原子类** 是具有原子/原子操作特征的类。

并发包`JUC(java.util.concurrent)`的原子类都在`java.util.concurrent.atomic`中

# 验证

运行如下代码：

```java
public class AtomicTest {
    public static void main(String[] args) {
        Date date = new Date();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    date.volatileNumAdd();
                    date.atomicNumAdd();
                }
            }, i + "").start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println("over! volatile:\t" + date.volatileNum);
        System.out.println("over! atomic:\t" + date.atomicNum);
    }
}

class Date {
    volatile int volatileNum = 0;
    AtomicInteger atomicNum = new AtomicInteger(0);

    public void volatileNumAdd() {
        this.volatileNum++;
    }

    public void atomicNumAdd() {
        atomicNum.getAndIncrement();
    }
}
```

结果如下：

```bash
main	 over! volatileNum :	7111
main	 over! atomicNum :  	10000
```

# 分类

# 常用方法

# 底层原理

> `AtomicInteger`部分底层源码

```java
public class AtomicInteger extends Number implements java.io.Serializable {
    // setup to use Unsafe.compareAndSwapInt for updates
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;

    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                (AtomicInteger.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    private volatile int value;

    /**
     * Creates a new AtomicInteger with the given initial value.
     *
     * @param initialValue the initial value
     */
    public AtomicInteger(int initialValue) {
        value = initialValue;
    }

    /**
     * Atomically increments by one the current value.
     *
     * @return the previous value
     */
    public final int getAndIncrement() {
        return unsafe.getAndAddInt(this, valueOffset, 1);
    }
}
```

1. `Unsafe`：是`CAS`的核心类，由于`Java`方法无法直接访问底层系统，需要通过本地`(native)`方法来访问，`Unsafe`相当于一个后门，基于该
类可以直接操作特定内存的数据。`Unsafe`类存在于`sun.misc`包中，其内部方法操作可以像`C`的指针一样直接操作内存，因为`Java`中`CAS`操作的执行依赖于`Unsafe`类的方法。注意：`Unsafe`类中的所自方法都是`native`修饰的。也就是说`Unsaf`e类中的方法都直接调用操作系统底层资源执行相应任务
2. `valueOffset`：表示该变量值在内存中的偏移地址，因为`Unsafe`就是根据内存偏移地址获取数据的。
3. `value`：变量用`volatile`修饰，保证了多线程之间的内存可见性。
4. `getAndIncrement()`：`AtomicInteger.getAndIncrement()`向`Unsafe.getAndAddInt()`传入了三个值
    1. `this`：当前对象
    2. `valueOffset`：当前对象的内存地址
    3. `1`：添加的值

> `Unsafe.getAndAddInt(Object var1, long var2, int var4)`源码

```java
public final int getAndAddInt(Object var1, long var2, int var4) {
    int var5;
    do {
        var5 = this.getIntVolatile(var1, var2);
    } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
    return var5;
}
```

1. 首先`this.getIntVolatile(var1, var2)`获取当前对象`var1`在内存地址`var2`的快照值`var5`
2. 假设获取快照值之后过了一段时间
3. 然后`this.compareAndSwapInt(var1, var2, var5, var5 + var4)`获取当前对象`var1`在内存地址`var2`的真实值并和快照值`var5`进行比较
    1. 相同：表示这段时间这个对象的真实值没有改变，那么更新为期望值`var5 + var4`并返回`true`，取反为`false`后跳出当前循环，最后返回原值`var5`
    2. 不同：表示这段时间这个对象的真实值发生改变，那么返回`false`，取反为`true`后再次进入循环重新取值和比较，直至完成

再举个例子：假设线程A和线程B两个线程同时执行`getAndAddInt`操作

1. `AtomicInteger`里面的`value`原始值均为3，即主内存中`AtomicInteger`的`value`为3，根据JMM模型，线程A和线程B各自持有一份值为3的`value`的副本分别到各自的工作内存。
2. 线程A通过`getIntVolatile(var1, var2)`拿到`value`值3，这时线程A被挂起。
3. 线程B通过`getIntVolatile(var1, var2)`拿到`value`值3，此时刚好线程B没有被挂起并执行`compareAndSwapInt`方法比较内存值也为3，成功修改内存值为4，线程B打完收工，一切OK。
4. 这时线程A恢复，执行`compareAndSwapInt`方法比较，发现自己的值3和主内存的值4不一致，说明该值已经被其它线程修改过了，那A线程本次修改失败，只能重新读取重新来一遍了。
5. 线程A重新获取`value`值，因为变量`value`被`volatile`修饰，所以其它线程对它的修改，线程A总是能够看到，线程A继续执行`compareAndSwapInt`进行比较替换，直到成功。

## CAS

> 原理

`CAS`的全称为`Compare-And-Swap`, 它是一条`CPU`并发原语。它的功能是判断内存某个位置的值是否为预期值，如果是则更改为新的值，这个过程是原子的。

`CAS`并发原语体现在`JAVA`语言中就是`sun.misc.Unsafe`类中的各个方法。调用`UnSafe`类中的`CAS`方法，`JVM`会帮我们实现出`CAS`汇编指令。这是一种完全依赖于硬件的功能，通过它实现了原子操作。再次强调，由于`CAS`是一种系统原语，原语属于操作系统用语范畴，是由若干条指令组成的，用于完成某个功能的一个过程，并且原语的执行必须是连续的，在执行过程中不允许被中断，也就是说`CAS`是一条`CPU`的原子指令，不会造成所谓的数据不一致问题。

`Unsafe`类中的`compareAndSwapInt`是一个本地方法，该方法的实现位于`unsafe.cpp`中。下文是部分源码，详见：[http://hg.openjdk.java.net/jdk8/jdk8/hotspot/file/tip/src/share/vm/prims/unsafe.cpp](http://hg.openjdk.java.net/jdk8/jdk8/hotspot/file/tip/src/share/vm/prims/unsafe.cpp)

```cpp
UNSAFE_ENTRY(jboolean, Unsafe_CompareAndSwapInt(JNIEnv *env, jobject unsafe, jobject obj, jlong offset, jint e, jint x))
  UnsafeWrapper("Unsafe_CompareAndSwapInt");
  oop p = JNIHandles::resolve(obj);
  jint* addr = (jint *) index_oop_from_field_offset_long(p, offset);
  return (jint)(Atomic::cmpxchg(x, addr, e)) == e;
UNSAFE_END
```

先拿到变量`value`在内存中的地址。再通过`Atomic::cmpxchg`实现比较替换，其中参数`x`是即将更新的值，参数`e`是原内存的值。

> 缺点

1. 循环时间长开销很大：源码中有一个`do while`，如果`CAS`一直失败，会给`CPU`带来很大开销
2. 只能保证一个共享变量的原子操作：对多个共享变量操作时，无法保证操作的原子性
3. 会出现ABA问题
