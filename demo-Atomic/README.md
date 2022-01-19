> 参考教程

- `JavaGuide`：[Java 并发常见知识点&面试题总结（进阶篇）](https://javaguide.cn/java/concurrent/java并发进阶常见面试题总结/)
- `JavaGuide`：[Atomic 原子类总结](https://javaguide.cn/java/concurrent/atomic原子类总结/)
- `尚硅谷`：[互联网大厂高频重点面试题（第2季）](http://www.atguigu.com/download_detail.shtml?v=130)

---

上文说道 [volatile 关键字](https://maxqiu.com/article/detail/127) 不保证原子性，那么在不使用 [synchronized 关键字](https://maxqiu.com/article/detail/125) 的情况下如何解决多线程安全问题呢？就是本文的主角：**`Atomic`原子类**

# 简介

`Atomic` 中文是原子的意思。在这里指一个操作是不可中断的。即使是在多个线程一起执行的时候，一个操作一旦开始，就不会被其他线程干扰。

所以：**原子类** 是具有原子/原子操作特征的类。

并发包`JUC(java.util.concurrent)`的原子类都在`java.util.concurrent.atomic`中

# 示例

运行如下代码：

```java
public class AtomicTest {
    @Test
    void test() {
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
            // 让出当前线程，给其他线程执行
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

- **基本类型**：使用原子的方式更新基本类型
    - `AtomicInteger`：整型原子类
    - `AtomicLong`：长整型原子类
    - `AtomicBoolean`：布尔型原子类 
- **数组类型**：使用原子的方式更新数组里的某个元素
    - `AtomicIntegerArray`：整型数组原子类
    - `AtomicLongArray`：长整型数组原子类
    - `AtomicReferenceArray`：引用类型数组原子类
- **引用类型**：用于包装对象
    - `AtomicReference`：引用类型原子类
    - `AtomicMarkableReference`：原子更新带有标记的引用类型。该类将`boolean`标记与引用关联起来
    - `AtomicStampedReference`：原子更新带有版本号的引用类型。该类将整数值与引用关联起来，可用于解决原子的更新数据和数据的版本号，可以解决使用`CAS`进行原子更新时可能出现的`ABA`问题
- **对象的属性修改类型**
    - `AtomicIntegerFieldUpdater`：原子更新整型字段的更新器
    - `AtomicLongFieldUpdater`：原子更新长整型字段的更新器
    - `AtomicReferenceFieldUpdater`：原子更新引用类型里的字段

# 常用方法

## 基本类型原子类（以`AtomicInteger`为例）

> 代码

```java
public class AtomicIntegerTest {
    @Test
    void test() {
        // 无参构造：默认值为0
        // AtomicInteger atomic = new AtomicInteger();
        // 有参构造：初始化传入初始值，
        AtomicInteger atomic = new AtomicInteger(1);

        System.out.println("获取当前值：" + atomic.get());

        System.out.println("\n设置值");
        atomic.set(5);
        System.out.println("新值：" + atomic.get());

        System.out.println("\n获取当前值，并设置新值");
        System.out.println("原始：" + atomic.getAndSet(2));
        System.out.println("新值：" + atomic.get());

        System.out.println("\n获取当前值，并增加值");
        System.out.println("原始：" + atomic.getAndAdd(2));
        System.out.println("新值：" + atomic.get());

        System.out.println("\n增加值，并获取结果");
        System.out.println("新值：" + atomic.addAndGet(2));

        System.out.println("\n获取当前值，并自增");
        System.out.println("原始：" + atomic.getAndIncrement());
        System.out.println("新值：" + atomic.get());

        System.out.println("\n获取当前值，并自减");
        System.out.println("原始：" + atomic.getAndDecrement());
        System.out.println("新值：" + atomic.get());

        System.out.println("\n自增后，获取当前值，相当于 ++i");
        System.out.println("新值：" + atomic.incrementAndGet());

        System.out.println("\n自减后，获取当前值，相当于 --i");
        System.out.println("新值：" + atomic.decrementAndGet());

        System.out.println("\n比较并修改值");
        int i = atomic.get();
        System.out.println("原始：" + i);
        System.out.println("修改结果1：" + atomic.compareAndSet(i, 8) + "\t值：" + atomic.get());
        System.out.println("修改结果2：" + atomic.compareAndSet(i, 8) + "\t值：" + atomic.get());

        System.out.println("\n转换");
        System.out.println(atomic.intValue());
        System.out.println(atomic.longValue());
        System.out.println(atomic.floatValue());
        System.out.println(atomic.doubleValue());
    }
}
```

> 输出如下

```bash
获取当前值：1

设置值
新值：5

获取当前值，并设置新值
原始：5
新值：2

获取当前值，并增加值
原始：2
新值：4

增加值，并获取结果
新值：6

获取当前值，并自增
原始：6
新值：7

获取当前值，并自减
原始：7
新值：6

自增后，获取当前值，相当于 ++i
新值：7

自减后，获取当前值，相当于 --i
新值：6

比较并修改值
原始：6
修改结果1：true 	值：8
修改结果2：false	值：8

转换
8
8
8.0
8.0
```

## 数组类型原子类（以`AtomicIntegerArray`为例）

> 代码

```java
public class AtomicIntegerArrayTest {
    @Test
    void test() {
        // 有参构造：指定数组长度，数组内初始值为0
        // AtomicIntegerArray atomic = new AtomicIntegerArray(10);
        // 有参构造：传入数组
        AtomicIntegerArray atomic = new AtomicIntegerArray(new int[] {1, 2, 3, 4, 5});

        System.out.println("数组的长度为：" + atomic.length());

        System.out.println("\n获取指定位置的值");
        for (int i = 0; i < atomic.length(); i++) {
            System.out.println(i + "\t" + atomic.get(i));
        }

        System.out.println("\n更新指定位置值");
        atomic.set(0, 2);
        System.out.println("位置：" + 0 + "\t新值：" + atomic.get(0));

        System.out.println("\n获取指定位置当前值，并设置新值");
        System.out.println("位置：" + 0 + "\t原始：" + atomic.getAndSet(0, 2));
        System.out.println("位置：" + 0 + "\t新值：" + atomic.get(0));

        System.out.println("\n获取指定位置当前值，并增加值");
        System.out.println("位置：" + 0 + "\t原始：" + atomic.getAndAdd(0, 2));
        System.out.println("位置：" + 0 + "\t新值：" + atomic.get(0));

        System.out.println("\n指定位置增加值，并获取结果");
        System.out.println("位置：" + 0 + "\t新值：" + atomic.addAndGet(0, 2));

        System.out.println("\n获取指定位置当前值，并自增");
        System.out.println("位置：" + 0 + "\t原始：" + atomic.getAndIncrement(0));
        System.out.println("位置：" + 0 + "\t新值：" + atomic.get(0));

        System.out.println("\n获取指定位置当前值，并自减");
        System.out.println("位置：" + 0 + "\t原始：" + atomic.getAndDecrement(0));
        System.out.println("位置：" + 0 + "\t新值：" + atomic.get(0));

        System.out.println("\n指定位置自增后，获取当前值，相当于 ++i");
        System.out.println("位置：" + 0 + "\t新值：" + atomic.incrementAndGet(0));

        System.out.println("\n指定位置自减后，获取当前值，相当于 --i");
        System.out.println("位置：" + 0 + "\t新值：" + atomic.decrementAndGet(0));

        System.out.println("\n指定位置比较并修改值");
        int i = atomic.get(0);
        System.out.println("位置：" + 0 + "\t原始：" + i);
        System.out.println("位置：" + 0 + "\t修改结果1：" + atomic.compareAndSet(0, i, 8) + "\t值：" + atomic.get(0));
        System.out.println("位置：" + 0 + "\t修改结果2：" + atomic.compareAndSet(0, i, 8) + "\t值：" + atomic.get(0));
    }
}
```

> 输出

```bash
数组的长度为：5

获取指定位置的值
0	1
1	2
2	3
3	4
4	5

更新指定位置值
位置：0	新值：2

获取指定位置当前值，并设置新值
位置：0	原始：2
位置：0	新值：2

获取指定位置当前值，并增加值
位置：0	原始：2
位置：0	新值：4

指定位置增加值，并获取结果
位置：0	新值：6

获取指定位置当前值，并自增
位置：0	原始：6
位置：0	新值：7

获取指定位置当前值，并自减
位置：0	原始：7
位置：0	新值：6

指定位置自增后，获取当前值，相当于 ++i
位置：0	新值：7

指定位置自减后，获取当前值，相当于 --i
位置：0	新值：6

指定位置比较并修改值
位置：0	原始：6
位置：0	修改结果1：true 	值：8
位置：0	修改结果2：false	值：8
```

## 引用类型原子类

> 引用类型用于修改对象，对象示例如下：

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {
    private String name;
    private Integer age;
}
```

### `AtomicReference`

> 代码

```java
public class AtomicReferenceTest {
    @Test
    void test() {
        // 无参构造：默认null
        // AtomicReference<Person> atomic = new AtomicReference<>();
        // 有参构造：传入初始对象
        AtomicReference<Person> atomic = new AtomicReference<>(new Person("张三", 25));

        System.out.println("获取当前值：" + atomic.get());

        System.out.println("\n设置值");
        atomic.set(new Person("李四", 30));
        System.out.println("新值：" + atomic.get());

        System.out.println("\n获取当前值，并设置新值");
        System.out.println("原始：" + atomic.getAndSet(new Person("王五", 35)));
        System.out.println("新值：" + atomic.get());

        System.out.println("\n比较并修改值");
        Person oldP = atomic.get();
        Person newP = new Person("赵六", 40);
        System.out.println("原始：" + oldP);
        System.out.println("修改结果1：" + atomic.compareAndSet(oldP, newP) + "\t值：" + atomic.get());
        System.out.println("修改结果2：" + atomic.compareAndSet(oldP, newP) + "\t值：" + atomic.get());
    }
}
```

> 输出

```bash
获取当前值：Person(name=张三, age=25)

设置值
新值：Person(name=李四, age=30)

获取当前值，并设置新值
原始：Person(name=李四, age=30)
新值：Person(name=王五, age=35)

比较并修改值
原始：Person(name=王五, age=35)
修改结果1：true 	值：Person(name=赵六, age=40)
修改结果2：false	值：Person(name=赵六, age=40)
```

### `AtomicMarkableReference`

> 代码

```java
public class AtomicMarkableReferenceTest {
    @Test
    void test() {
        // 有参构造：初始化对象和标记
        AtomicMarkableReference<Person> atomic = new AtomicMarkableReference<>(new Person("张三", 18), false);

        System.out.println("取值：");
        System.out.println("对象：" + atomic.getReference());
        System.out.println("标记：" + atomic.isMarked());

        System.out.println("\n获取当前值并同时将标记存储在boolean类型的数组中（数组长度至少为1）");
        boolean[] arr = new boolean[1];
        System.out.println("对象：" + atomic.get(arr));
        System.out.println("标记：" + arr[0]);

        System.out.println("\n设置值和标记");
        Person person = new Person("李四", 30);
        atomic.set(person, true);
        System.out.println("新对象：" + atomic.getReference());
        System.out.println("新标记：" + atomic.isMarked());

        System.out.println("\n当对象相同时，单独修改标记");
        System.out.println("原标记：" + atomic.isMarked());
        System.out.println("修改结果：" + atomic.attemptMark(person, false));
        System.out.println("新标记：" + atomic.isMarked());

        System.out.println("\n比较并修改值");
        Person oldP = atomic.getReference();
        boolean oldF = atomic.isMarked();
        Person newP = new Person("王五", 45);
        boolean newF = false;
        System.out.println("修改结果1：" + atomic.compareAndSet(oldP, newP, oldF, newF));
        System.out.println("对象：" + atomic.getReference());
        System.out.println("标记：" + atomic.isMarked());
        System.out.println("修改结果2：" + atomic.compareAndSet(oldP, newP, oldF, newF));
        System.out.println("对象：" + atomic.getReference());
        System.out.println("标记：" + atomic.isMarked());

    }
}
```

> 输出

```bash
取值：
对象：Person(name=张三, age=18)
标记：false

获取当前值并同时将标记存储在boolean类型的数组中（数组长度至少为1）
对象：Person(name=张三, age=18)
标记：false

设置值和标记
新对象：Person(name=李四, age=30)
新标记：true

当对象相同时，单独修改标记
原标记：true
修改结果：true
新标记：false

比较并修改值
修改结果1：true
对象：Person(name=王五, age=45)
标记：false
修改结果2：false
对象：Person(name=王五, age=45)
标记：false
```

### `AtomicReference`

> 代码

```java
public class AtomicStampedReferenceTest {
    @Test
    void test() {
        // 有参构造：初始化对象和标记
        AtomicStampedReference<Person> atomic = new AtomicStampedReference<>(new Person("张三", 18), 1);

        System.out.println("取值：");
        System.out.println("对象：" + atomic.getReference());
        System.out.println("版本：" + atomic.getStamp());

        System.out.println("\n获取当前值并同时将标记存储在boolean类型的数组中（数组长度至少为1）");
        int[] arr = new int[1];
        System.out.println("对象：" + atomic.get(arr));
        System.out.println("版本：" + arr[0]);

        System.out.println("\n设置值和版本");
        Person person = new Person("李四", 30);
        atomic.set(person, 2);
        System.out.println("新对象：" + atomic.getReference());
        System.out.println("新版本：" + atomic.getStamp());

        System.out.println("\n当对象相同时，单独修改版本");
        System.out.println("原版本：" + atomic.getStamp());
        System.out.println("修改结果：" + atomic.attemptStamp(person, 3));
        System.out.println("新版本：" + atomic.getStamp());

        System.out.println("\n比较并修改值");
        Person oldP = atomic.getReference();
        int oldV = atomic.getStamp();
        Person newP = new Person("王五", 45);
        int newV = 4;
        System.out.println("修改结果1：" + atomic.compareAndSet(oldP, newP, oldV, newV));
        System.out.println("对象：" + atomic.getReference());
        System.out.println("版本：" + atomic.getStamp());
        System.out.println("修改结果2：" + atomic.compareAndSet(oldP, newP, oldV, newV));
        System.out.println("对象：" + atomic.getReference());
        System.out.println("版本：" + atomic.getStamp());
    }
}
```

> 输出

```bash
取值：
对象：Person(name=张三, age=18)
版本：1

获取当前值并同时将标记存储在boolean类型的数组中（数组长度至少为1）
对象：Person(name=张三, age=18)
版本：1

设置值和版本
新对象：Person(name=李四, age=30)
新版本：2

当对象相同时，单独修改版本
原版本：2
修改结果：true
新版本：3

比较并修改值
修改结果1：true
对象：Person(name=王五, age=45)
版本：4
修改结果2：false
对象：Person(name=王五, age=45)
版本：4
```

## 对象的属性修改器

如果需要原子更新某个类里的某个字段时，需要用到对象的属性修改原子类。

> 代码

```java
public class AtomicFieldUpdaterTest {
    @Test
    void test() {
        // 整形字段的更新器
        // 使用静态方法创建更新器，第一个参数为对象类型，第二个参数为要更新的字段名称，该字段必须修饰为 public volatile
        AtomicIntegerFieldUpdater<User1> integerUpdater = AtomicIntegerFieldUpdater.newUpdater(User1.class, "age");
        User1 user1 = new User1("Java", 22);
        // 获取当前对象被管理字段的值并原子自增
        System.out.println(integerUpdater.getAndIncrement(user1));
        // 获取当前对象被管理字段的值
        System.out.println(integerUpdater.get(user1));

        // 引用类型字段的更新器
        // 使用静态方法创建更新器，第一个参数为对象类型，第二个参数为要更新的字段类型，第三个参数为要更新的字段名称，该字段必须修饰为 public volatile
        AtomicReferenceFieldUpdater<User2, Integer> referenceUpdater = AtomicReferenceFieldUpdater.newUpdater(User2.class, Integer.class, "age");
        User2 user2 = new User2("Jerry", 18);
        // 获取当前对象被管理字段的值并设置新值
        System.out.println(referenceUpdater.getAndSet(user2, 20));
        // 获取当前对象被管理字段的值
        System.out.println(referenceUpdater.get(user2));
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class User1 {
    private String name;
    public volatile int age;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class User2 {
    private String name;
    public volatile Integer age;
}
```

> 输出

```bash
22
23
18
20
```

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

## ABA的问题解决

> ABA问题的产生

`CAS`算法实现一个重要前提是需要取出内存中某时刻的数据并在当下时刻比较并替换，那么在这个时间差类会导致数据的变化。比如说线程A、线程B从内存中取出V1，然后线程B进行了一些操作将值变成了V2然后又进行一些操作将值变回V1，这时候线程A进行CAS操作发现内存中仍然是V1，然后线程A操作成功。尽管线程A的CAS操作成功，但是不代表这个过程就是没有问题的。

> ABA问题的解决

只需要使用带有版本控制的原子类`AtomicStampedReference`即可

> 代码

```java
public class ABATest {
    /**
     * 普通的对象引用原子类，不能解决ABA问题
     */
    @Test
    void atomicReference() {
        AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "t1").start();
        new Thread(() -> {
            // 暂停一会t2线程，保证上面的t1线程完成了一次ABA操作
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100, 2019));
        }, "t2").start();

        // 等待线程结束
        while (Thread.activeCount() > 2) {
            // 让出当前线程，给其他线程执行
            Thread.yield();
        }

        System.out.println(atomicReference.get());
    }

    /**
     * 带版本控制的对象引用原子类，可以解决ABA的问题
     */
    @Test
    void atomicStampedReference() {
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);
        new Thread(() -> {
            // 获取初始版本
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第1次版本号：" + stamp);
            // 暂停一会t1线程，保证t2线程获取到了初始版本号
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100, 101, stamp, ++stamp);
            System.out.println(Thread.currentThread().getName() + "\t第2次版本号：" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100, stamp, ++stamp);
            System.out.println(Thread.currentThread().getName() + "\t第3次版本号：" + atomicStampedReference.getStamp());
        }, "t1").start();

        new Thread(() -> {
            // 获取初始版本
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第1次版本号：" + stamp);
            // 暂停一会t2线程，保证上面的t3线程完成了一次ABA操作
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicStampedReference.compareAndSet(100, 2019, stamp, ++stamp);
            System.out.println(Thread.currentThread().getName() + "\t修改成功否：" + result);
        }, "t2").start();

        // 等待线程结束
        while (Thread.activeCount() > 2) {
            // 让出当前线程，给其他线程执行
            Thread.yield();
        }

        System.out.println("当前最新实际版本号：" + atomicStampedReference.getStamp() + "\t当前实际最新值：" + atomicStampedReference.getReference());
    }
}
```

> 输出：

不带版本控制的原子类`AtomicReference`会出现`ABA`问题

```bash
true
2019
```

携带版本控制的原子类`AtomicStampedReference`可以解决`ABA`问题

```bash
t1	第1次版本号：1
t2	第1次版本号：1
t1	第2次版本号：2
t1	第3次版本号：3
t2	修改成功否：false
当前最新实际版本号：3	当前实际最新值：100
```
