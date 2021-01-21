> 推荐阅读：[漫画：什么是单例模式？（整合版）](https://mp.weixin.qq.com/s/2UYXNzgTCEZdEfuGIbcczA)

# 简介

单例模式`Singleton Pattern`：采取一定的方法保证在整个的软件系统中，对**某个类只能存在一个对象实例**，并且该类只提供一个取得其对象实例的方法（静态方法）。

# 1. 饿汉式（静态常量）

```java
class Singleton {

    // 1. 构造器私有化, 外部能new
    private Singleton() {}

    // 2. 本类内部创建对象实例
    private static final Singleton INSTANCE = new Singleton();

    // 3. 提供一个公有的静态方法，返回实例对象
    public static Singleton getInstance() {
        return INSTANCE;
    }
}
```

```java
public static void main(String[] args) {
    Singleton instance1 = Singleton.getInstance();
    Singleton instance2 = Singleton.getInstance();
    System.out.println(instance1 == instance2); // true
    System.out.println("instance1.hashCode=" + instance1.hashCode());
    System.out.println("instance2.hashCode=" + instance2.hashCode());
}
```

这种方式基于 classloder 机制避免了多线程的同步问题，不过 instance 在类装载时就实例化，在单例模式中大多数都是调用 getInstance 方法，但是导致类装载的原因有很多种，因此不能确定有其他的方式（或者其他的静态方法）导致类装载，这时候初始化 instance 就没有达到懒加载的效果

- 优点：这种写法比较简单，就是在类装载的时候就完成实例化。避免了线程同步问题。
- 缺点：在类装载的时候就完成实例化，没有达到懒加载的效果。如果从始至终从未使用过这个实例，则会造成内存的浪费
- 结论：**推荐使用！**，但会造成内存浪费

# 2. 饿汉式（静态代码块）

```java
class Singleton {

    // 1. 构造器私有化, 外部不能new
    private Singleton() {}

    private static final Singleton INSTANCE;

    static {
        // 2. 在静态代码块中，创建单例对象
        INSTANCE = new Singleton();
    }

    // 3. 提供一个公有的静态方法，返回实例对象
    public static Singleton getInstance() {
        return INSTANCE;
    }

}
```

```java
public static void main(String[] args) {
    Singleton instance1 = Singleton.getInstance();
    Singleton instance2 = Singleton.getInstance();
    System.out.println(instance1 == instance2); // true
    System.out.println("instance1.hashCode=" + instance1.hashCode());
    System.out.println("instance2.hashCode=" + instance2.hashCode());
}
```

这种方式和上面的方式类似，只不过将类实例化的过程放在了静态代码块中，也是在类装载的时候，就执行静态代码块中的代码，初始化类的实例。

优点、缺点、结论和上面一样

# 3. 懒汉式（线程不安全）

```java
class Singleton {
    private Singleton() {}

    private static Singleton instance;

    // 提供一个静态的公有方法，当使用到该方法时，才去创建 instance ，即懒汉式
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

```java
public static void main(String[] args) {
    // 多次运行，有时打印的hashCode不一致
    new Thread(() -> System.out.println(Singleton.getInstance().hashCode())).start();
    new Thread(() -> System.out.println(Singleton.getInstance().hashCode())).start();
}
```

- 优点：起到了懒加载的效果，但是只能在单线程下使用。
- 缺点：如果在多线程下，一个线程进入了`if (singleton == null)`判断语句块，还未来得及往下执行，另一个线程也通过了这个判断语句，这时便会产生多个实例。所以在多线程环境下不可使用这种方式
- 结论：**不要使用！**

# 4. 懒汉式（同步方法，线程安全，效率低）

```java
class Singleton {
    private static Singleton instance;

    private Singleton() {}

    // 使用同步方法，保证线程安全
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

```java
public static void main(String[] args) {
    Singleton instance1 = Singleton.getInstance();
    Singleton instance2 = Singleton.getInstance();
    System.out.println(instance1 == instance2); // true
    System.out.println("instance1.hashCode=" + instance1.hashCode());
    System.out.println("instance2.hashCode=" + instance2.hashCode());
}
```

- 优点：解决了线程安全问题
- 缺点：方法进行同步效率太低。每个线程在想获得类的实例时候，执行` getInstance()`方法都要进行同步。而这个方法只需要执行一次实例化代码，后面的直接`return`就行了。
- 结论：**不要使用！**

# 5. 懒汉式（同步代码块，线程不安全）

```java
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
```

```java
public static void main(String[] args) {
    // 多次运行，有时打印的hashCode不一致
    new Thread(() -> System.out.println(Singleton.getInstance().hashCode())).start();
    new Thread(() -> System.out.println(Singleton.getInstance().hashCode())).start();
}
```

- 结论：**不要使用！**

# 6. 懒汉式（双重检查）

> 关于为什么要加`volatile`请参考推荐阅读

```java
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
            // 使用如下输出，多次运行可以发现有多个instance = null输出，但是获取的hashCode任然一致
            // System.out.println("instance = null");
        }
        return instance;
    }
}
```

```java
public static void main(String[] args) {
    new Thread(() -> System.out.println(Singleton.getInstance().hashCode())).start();
    new Thread(() -> System.out.println(Singleton.getInstance().hashCode())).start();
    Singleton instance1 = Singleton.getInstance();
    Singleton instance2 = Singleton.getInstance();
    System.out.println(instance1 == instance2); // true
    System.out.println("instance1.hashCode=" + instance1.hashCode());
    System.out.println("instance2.hashCode=" + instance2.hashCode());
}
```

双重检查概念是多线程开发中常使用到的。如代码中所示，进行了两次`if (singleton == null)`检查，就可以保证线程安全了。这样，实例化代码只用执行一次，后面再次访问时，判断`if (singleton == null)`直接`return`实例化对象，也避免的反复进行方法同步。

- 优点：线程安全、延迟加载、效率较高
- 结论：**推荐使用！**

# 7. 懒汉式（静态内部类）

```java
class Singleton {
    // 1. 构造器私有化
    private Singleton() {}

    // 2. 写一个静态内部类,该类中有一个静态属性 Singleton
    private static class SingletonInstance {
        private static final Singleton INSTANCE = new Singleton();
    }

    // 3. 提供一个静态的公有方法，直接返回SingletonInstance.INSTANCE
    public static synchronized Singleton getInstance() {
        return SingletonInstance.INSTANCE;
    }
}
```

```java
public static void main(String[] args) {
    Singleton instance1 = Singleton.getInstance();
    Singleton instance2 = Singleton.getInstance();
    System.out.println(instance1 == instance2); // true
    System.out.println("instance1.hashCode=" + instance1.hashCode());
    System.out.println("instance2.hashCode=" + instance2.hashCode());
}
```

这种方式采用了类装载的机制来保证初始化实例时只有一个线程。静态内部类方式在`Singleton` 类被装载时并不会立即实例化，而是在需要实例化时，调用`getInstance()`方法，才会装载`SingletonInstance`类，从而完成`Singleton` 的实例化。

类的静态属性只会在第一次加载类的时候初始化，所以在这里JVM帮助我们保证了线程的安全性，在类进行初始化时，别的线程是无法进入的。

- 优点：避免了线程不安全，利用静态内部类特点实现延迟加载，效率高
- 结论：**推荐使用！**

# 8. 枚举

```java
enum Singleton {
    INSTANCE; // 属性

    public void sayOk() {
        System.out.println("ok~");
    }
}
```

```java
public static void main(String[] args) {
    Singleton instance1 = Singleton.INSTANCE;
    Singleton instance2 = Singleton.INSTANCE;
    System.out.println(instance1 == instance2);
    System.out.println(instance1.hashCode());
    System.out.println(instance2.hashCode());
    instance1.sayOk();
}
```

- 优点：借助JDK1.5中添加的枚举来实现单例模式。不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象。
- 缺点：不是懒加载
- 结论：**推荐使用**

这种方式是`Effective Java`作者`Josh Bloch`提倡的方式

# 总结

## 对比

> 仅列举推荐使用的

实现方式 | 是否线程安全 | 是否懒加载 | 是否防止反射构建
---|---|---|---
饿汉式（静态常量） | T | F | F
饿汉式（静态代码块） | T | F | F
懒汉式（双重检查） | T | T | F
懒汉式（静态内部类） | T | T | F
枚举 | T | F | T

## JDK源码示例 Runtime

```java
public class Runtime {
    private static final Runtime currentRuntime = new Runtime();

    public static Runtime getRuntime() {
        return currentRuntime;
    }

    private Runtime() {}

    ...
}
```

## 注意事项

1. 单例模式保证了系统内存中该类只存在一个对象，节省了系统资源，对于一些需要频繁创建销毁的对象，使用单例模式可以提高系统性能
2. 当想实例化一个单例类的时候，必须要记住使用相应的获取对象的方法，而不是使用`new`
3. 单例模式使用的场景：
    1. 需要频繁的进行创建和销毁的对象
    2. 创建对象时耗时过多或耗费资源过多(即：重量级对象)，但又经常用到的对象
    3. 工具类对象
    4. 频繁访问数据库或文件的对象(比如数据源、session工厂等)

## 其他

网上有看到一种方式，在构造方法内进行判断是否已经实例化来防止反射，示例如下：

```java
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
```

但是如果先用了`反射`，那么再调`getInstance()`就会抛出异常，单例的就不能正常使用了。过程如下：

```java
public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
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
```
