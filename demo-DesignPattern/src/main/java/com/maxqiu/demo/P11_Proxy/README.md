> 推荐阅读：[漫画：什么是 “代理模式” ？](https://mp.weixin.qq.com/s/O8_A2Ms9MPKEe9m6Y6t2-g)

# 代理模式 Proxy Pattern

> 介绍

- 为一个对象提供一个替身，以控制对这个对象的访问。即通过代理对象访问目标对象。这样做的好处是可以在目标对象实现的基础上，增强额外的功能操作，即扩展目标对象的功能。
- 被代理的对象可以是远程对象、创建开销大的对象或需要安全控制的对象
- 代理模式有不同的形式，主要有两种
  - 静态代理
  - 动态代理，动态代理又分两种
    - JDK代理（接口代理）
    - Cglib代理（可以在内存动态的创建对象，而不需要实现接口）

详细介绍见示例

> UML类图

![](https://cdn.maxqiu.com/upload/23d485d2439843a6bd485a0d9e47390d.jpg)

# 示例

## 情景介绍

代课老师替代老师完成上课的功能

## 静态代理

> 介绍

静态代理在使用时，需要定义接口或者父类，被代理对象（即目标对象）与代理对象一起实现相同的接口或者是继承相同父类

> UML类图

![](https://cdn.maxqiu.com/upload/f1baa490f7814a67820b9aa5c9aaf5dc.jpg)

> 代码实现

```java
/**
 * 接口
 */
public interface ITeacherDao {
    /**
     * 授课的方法
     */
    void teach();
}
```

```java
/**
 * 被代理对象
 */
public class TeacherDao implements ITeacherDao {
    @Override
    public void teach() {
        System.out.println("老师授课中。。。。。");
    }
}
```

```java
/**
 * 代理对象,静态代理
 */
public class TeacherDaoProxy implements ITeacherDao {
    /**
     * 目标对象，通过接口来聚合
     */
    private ITeacherDao target;
    /**
     * 构造器
     *
     * @param target
     */
    public TeacherDaoProxy(ITeacherDao target) {
        this.target = target;
    }
    @Override
    public void teach() {
        System.out.println("静态代理开始");
        target.teach();
        System.out.println("静态代理结束");
    }
}
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        // 创建目标对象（被代理对象）
        TeacherDao teacherDao = new TeacherDao();
        // 创建代理对象, 同时将被代理对象传递给代理对象
        TeacherDaoProxy teacherDaoProxy = new TeacherDaoProxy(teacherDao);
        // 通过代理对象，调用到被代理对象的方法。即：执行的是代理对象的方法，代理对象再去调用目标对象的方法
        teacherDaoProxy.teach();
    }
}
```

> 问题分析

- 优点：在不修改目标对象的功能前提下，能通过代理对象对目标功能扩展
- 缺点：因为代理对象需要与目标对象实现一样的接口，所以会有很多代理类，一旦接口增加方法，目标对象与代理对象都要维护

## 动态代理

### JDK方式

> 介绍

- 代理对象，不需要实现接口，但是目标对象要实现接口
- 代理对象的生成是利用`JDK`的`API`，动态的在内存中构建代理对象
- 代理类所在包:`java.lang.reflect.Proxy`

JDK实现代理只需要使用`newProxyInstance`方法，但是该方法需要接收三个参数，完整的写法是：

    static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces,InvocationHandler h )

> UML类图

![](https://cdn.maxqiu.com/upload/6a8d2f351f2c437c8e9c93485a5777ec.jpg)

> 代码实现

```java
/**
 * 接口
 */
public interface ITeacherDao {
    /**
     * 授课的方法
     */
    void teach();
}
```

```
/**
 * 被代理对象
 */
public class TeacherDao implements ITeacherDao {
    @Override
    public void teach() {
        System.out.println("老师授课中。。。。。");
    }
}
```

```java
/**
 * 代理工厂（JDK动态代理）
 */
public class ProxyFactory {
    /**
     * 维护一个目标对象
     */
    private Object target;
    /**
     * 构造器，对target进行初始化
     */
    public ProxyFactory(Object target) {
        this.target = target;
    }
    /**
     * 给目标对象生成一个代理对象
     */
    public Object getProxyInstance() {
        // public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
        // 1. ClassLoader loader ： 指定当前目标对象使用的类加载器, 获取加载器的方法固定
        // 2. Class<?>[] interfaces: 目标对象实现的接口类型，使用泛型方法确认类型
        // 3. InvocationHandler h : 事情处理，执行目标对象的方法时，会触发事情处理器方法, 会把当前执行的目标对象方法作为参数传入
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
            new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("JDK代理开始");
                    // 反射机制调用目标对象的方法
                    Object returnVal = method.invoke(target, args);
                    System.out.println("JDK代理结束");
                    return returnVal;
                }
            });
    }
}
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        // 创建目标对象
        ITeacherDao target = new TeacherDao();
        // 给目标对象，创建代理对象, 可以转成 ITeacherDao
        ITeacherDao proxyInstance = (ITeacherDao)new ProxyFactory(target).getProxyInstance();
        // proxyInstance=class com.sun.proxy.$Proxy0 内存中动态生成了代理对象
        System.out.println("proxyInstance=" + proxyInstance.getClass());
        // 通过代理对象，调用目标对象的方法
        proxyInstance.teach();
    }
}
```

### cglib代理

> 介绍

- 静态代理和JDK代理模式都要求目标对象是实现一个接口，但是有时候目标对象只是一个单独的对象，并没有实现任何的接口，这个时候可使用目标对象子类来实现代理 -> 这就是Cglib代理
- cglib代理也叫作`子类代理`，它是在内存中构建一个子类对象从而实现对目标对象功能扩展
- cglib是一个强大的高性能的代码生成包，它可以在运行期扩展java类与实现java接口。它广泛的被许多AOP的框架使用，例如`SpringAOP`实现方法拦截
- 在AOP编程中如何选择代理模式：
  1. 目标对象需要实现接口，用JDK代理
  2. 目标对象不需要实现接口，用cglib代理
- cglib包的底层是通过使用字节码处理框架，ASM来转换字节码并生成新的类

> 注意事项

- 需要引入cglib依赖
- 在内存中动态构建子类，注意代理的类不能为final，否则报错java.lang.IllegalArgumentException:
- 目标对象的方法如果为final/static，那么就不会被拦截，即不会执行目标对象额外的业务方法。

> UML类图

![](https://cdn.maxqiu.com/upload/1e2c391e56a446b1aeacadcb3f3fa4c2.jpg)

> 代码实现

```java
/**
 * 被代理对象
 */
public class TeacherDao {
    public void teach() {
        System.out.println("老师授课中。。。。。我是cglib代理，不需要实现接口");
    }
}
```

```java
/**
 * 代理工厂（cglib代理）
 */
public class ProxyFactory implements MethodInterceptor {
    /**
     * 维护一个目标对象
     */
    private Object target;
    /**
     * 构造器，传入一个被代理的对象
     */
    public ProxyFactory(Object target) {
        this.target = target;
    }
    /**
     * 返回一个代理对象: 是 target 对象的代理对象
     */
    public Object getProxyInstance() {
        // 1. 创建一个工具类
        Enhancer enhancer = new Enhancer();
        // 2. 设置父类
        enhancer.setSuperclass(target.getClass());
        // 3. 设置回调函数
        enhancer.setCallback(this);
        // 4. 创建子类对象，即代理对象
        return enhancer.create();
    }
    /**
     * 重写 intercept 方法，会调用目标对象的方法
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Cglib代理开始");
        Object returnVal = method.invoke(target, args);
        System.out.println("Cglib代理结束");
        return returnVal;
    }
}
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        // 创建目标对象
        TeacherDao target = new TeacherDao();
        // 获取到代理对象，并且将目标对象传递给代理对象
        TeacherDao proxyInstance = (TeacherDao)new ProxyFactory(target).getProxyInstance();
        // 执行代理对象的方法，触发 intercept 方法，从而实现对目标对象的调用
        proxyInstance.teach();
    }
}
```
