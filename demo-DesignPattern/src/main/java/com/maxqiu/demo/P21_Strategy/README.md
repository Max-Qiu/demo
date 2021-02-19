> 推荐阅读：[还在使用if else写代码？试试 “策略模式” 吧！](https://mp.weixin.qq.com/s/gB1nM4q9PculNVZJr9NSHA)

PS：尚硅谷的教程并未涉及到`if...else`的问题，只在总结时说到了

# 情景介绍

编写鸭子项目，具体要求如下:

1. 有各种鸭子，比如野鸭、水鸭、玩具鸭等
2. 鸭子有各种行为，比如叫、飞行等
3. 显示鸭子的信息

## 传统方式

> UML类图

![](https://cdn.maxqiu.com/upload/10533bccd3c949f4b2fc158cf51cff4c.jpg)

> 代码实现

```java
/**
 * 抽象鸭子
 */
public abstract class Duck {
    /**
     * 显示鸭子信息
     */
    public abstract void display();
    public void quack() {
        System.out.println("鸭子嘎嘎叫~~");
    }
    public void swim() {
        System.out.println("鸭子会游泳~~");
    }
    public void fly() {
        System.out.println("鸭子会飞翔~~~");
    }
}
```

```java
/**
 * 野鸭
 */
public class WildDuck extends Duck {
    @Override
    public void display() {
        System.out.println("野鸭");
    }
}
```

```java
/**
 * 玩具鸭
 *
 * 需要重写父类的所有方法
 */
public class ToyDuck extends Duck {
    @Override
    public void display() {
        System.out.println("玩具鸭");
    }
    /**
     * 需要重写父类的所有方法
     */
    @Override
    public void quack() {
        System.out.println("玩具鸭不能叫~~");
    }
    @Override
    public void swim() {
        System.out.println("玩具鸭不会游泳~~");
    }
    @Override
    public void fly() {
        System.out.println("玩具鸭不会飞翔~~");
    }
}
```

```java
/**
 * 客户端
 */
public class Client {

    public static void main(String[] args) {
        WildDuck wildDuck = new WildDuck();
        wildDuck.display();
        wildDuck.fly();

        ToyDuck toyDuck = new ToyDuck();
        toyDuck.display();
        toyDuck.fly();
    }

}
```

输出如下：

    野鸭
    鸭子会飞翔~~~
    玩具鸭
    玩具鸭不会飞翔~~

> 问题分析

1. 其它鸭子，都继承了 Duck 类，所以 fly 让所有子类都会飞了，这是不正确的
2. 上面说的 1 的问题，其实是继承带来的问题：对类的局部改动，尤其超类的局部改动，会影响其他部分。会有溢出效应
3. 为了改进 1 问题，我们可以通过覆盖 fly 方法来解决 => 覆盖解决
4. 问题又来了，如果我们有一个玩具鸭子 ToyDuck, 这样就需要 ToyDuck 去覆盖 Duck 法的所有实现的方法
5. 解决思路 -> 策略模式

# 策略模式 Strategy Pattern

> 基本介绍

策略模式中，定义算法族（策略组），分别封装起来，让他们之间可以互相替换，此模式让算法的变化独立于使用算法的客户。这算法体现了几个设计原则：

1. 把变化的代码从不变的代码中分离出来
2. 针对接口编程而不是具体类（定义了策略接口）
3. 多用组合/聚合，少用继承（客户通过组合方式使用策略）

> UML类图

![](https://cdn.maxqiu.com/upload/6059831a8c804c3a915c3ec2a7d47757.jpg)

说明：从上图可以看到，客户 context 有成员变量 strategy 或者其他的策略接口，至于需要使用到哪个策略，可以在构造器中指定

## 改进方式

> UML类图

![](https://cdn.maxqiu.com/upload/a1fe383285464c779551aefe4a221746.jpg)

> 代码实现

```java
/**
 * 飞翔策略
 */
public interface FlyBehavior {
    /**
     * 子类具体实现
     */
    void fly();
}
```

```java
/**
 * 飞翔很棒的策略
 */
public class GoodFlyBehavior implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("飞翔技术高超！！！");
    }
}
```

```java
/**
 * 不可以飞的策略
 */
public class NoFlyBehavior implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("不会飞翔");
    }
}
```

```java
/**
 * 抽象鸭子
 */
public abstract class Duck {
    /**
     * 飞的属性（策略接口）
     */
    FlyBehavior flyBehavior;
    /**
     * 显示鸭子信息
     */
    public abstract void display();
    public void fly() {
        // 改进
        if (flyBehavior != null) {
            flyBehavior.fly();
        }
    }
}
```

```java
/**
 * 玩具鸭
 */
public class ToyDuck extends Duck {
    public ToyDuck() {
        flyBehavior = new NoFlyBehavior();
    }
    @Override
    public void display() {
        System.out.println("玩具鸭");
    }
}
```

```java
/**
 * 野鸭
 */
public class WildDuck extends Duck {
    /**
     * 构造器，决定具体的 flyBehavior 的对象
     */
    public WildDuck() {
        flyBehavior = new GoodFlyBehavior();
    }
    @Override
    public void display() {
        System.out.println("野鸭");
    }
}
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        WildDuck wildDuck = new WildDuck();
        wildDuck.display();
        wildDuck.fly();
        ToyDuck toyDuck = new ToyDuck();
        toyDuck.display();
        toyDuck.fly();
    }
}
```

输出如下：

    野鸭
    飞翔技术高超！！！
    玩具鸭
    不会飞翔

# 总结

1. 策略模式的关键是：分析项目中变化部分与不变部分
2. 策略模式的核心思想是：多用组合/聚合，少用继承；用行为类组合，而不是行为的继承。更有弹性
3. 体现了“对修改关闭，对扩展开放”原则，客户端增加行为不用修改原有代码，只要添加一种策略（或者行为）即可，避免了使用多重转移语句（if..else if..else）
4. 提供了可以替换继承关系的办法：策略模式将算法封装在独立的`Strategy`类中使得你可以独立于其`Context`改变它，使它易于切换、易于理解、易于扩展
5. 需要注意的是：每添加一个策略就要增加一个类，当策略过多是会导致类数目庞
