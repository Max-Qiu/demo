# 模板方法模式 Template Method Pattern

> 基本介绍

又叫`模板模式(Template Pattern)`，在一个抽象类公开定义了执行它的方法的模板。它的子类可以按需要重写方法实现，但调用将以抽象类中定义的方式进行。简单说，模板方法模式定义一个操作中的算法的骨架，而将一些步骤延迟到子类中，使得子类可以不改变一个算法的结构，就可以重定义该算法的某些特定步骤。这种类型的设计模式属于行为型模式。

> UML类图与角色

![](https://cdn.maxqiu.com/upload/6e315ae1caeb4b33a7a182a23072e1ff.jpg)

- AbstractClass：抽象类中实现了模板方法(template)，定义了算法的骨架，具体子类需要去实现其它的抽象方法operationr1,2
- ConcreteClass：实现抽象方法operationr1,2，以完成算法中特点子类的步骤
- 在模板方法模式的父类中，我们可以定义一个方法，它默认不做任何事，子类可以视情况要不要覆盖它，该方法称为“钩子”。（示例见下文）

# 示例

## 一般模式

> 情景介绍

- 制作豆浆的流程：选材--->添加配料--->浸泡--->榨汁
- 通过添加不同的配料，可以制作出不同口味的豆浆
- 选材、浸泡和榨汁这几个步骤对于制作每种口味的豆浆都是一样的

> UML类图

![](https://cdn.maxqiu.com/upload/8608b5d088d94c45b6692a0abb411df1.jpg)

> 代码实现

```java
/**
 * 豆浆
 */
public abstract class SoybeanMilk {
    /**
     * 模板方法（模板方法添加final，不让子类去覆盖）
     */
    final void make() {
        select();
        addCondiments();
        soak();
        beat();

    }
    /**
     * 选材料
     */
    void select() {
        System.out.println("选择新鲜黄豆");
    }
    /**
     * 添加不同的配料（抽象方法，子类具体实现）
     */
    abstract void addCondiments();
    /**
     * 浸泡
     */
    void soak() {
        System.out.println("浸泡，需要3小时");
    }
    /**
     * 榨汁
     */
    void beat() {
        System.out.println("榨汁");
    }
}
```

```java
/**
 * 红豆豆浆
 */
public class RedBeanSoybeanMilk extends SoybeanMilk {
    @Override
    void addCondiments() {
        System.out.println("加入上好的红豆");
    }
}
```

```java
/**
 * 花生豆浆
 */
public class PeanutSoybeanMilk extends SoybeanMilk {
    @Override
    void addCondiments() {
        System.out.println("加入上好的花生");
    }
}
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("----制作红豆豆浆----");
        RedBeanSoybeanMilk redBeanSoybeanMilk = new RedBeanSoybeanMilk();
        redBeanSoybeanMilk.make();
        System.out.println("----制作花生豆浆----");
        SoybeanMilk peanutSoybeanMilk = new PeanutSoybeanMilk();
        peanutSoybeanMilk.make();
    }
}
```

## 添加钩子方法

> 情景介绍

比如，还需要制作纯豆浆，不添加任何的配料，请使用钩子方法对前面的模板方法进行改造

> UML类图

![](https://cdn.maxqiu.com/upload/ae096200d3d6402f8241b57fa8e8350e.jpg)

> 代码实现

```java
/**
 * 豆浆
 */
public abstract class SoybeanMilk {
    /**
     * 模板方法（模板方法添加final，不让子类去覆盖）
     */
    final void make() {
        select();
        if (customerWantCondiments()) {
            addCondiments();
        }
        soak();
        beat();
    }
    /**
     * 选材料
     */
    void select() {
        System.out.println("选择新鲜黄豆");
    }
    /**
     * 添加不同的配料（抽象方法，子类具体实现）
     */
    abstract void addCondiments();
    /**
     * 浸泡
     */
    void soak() {
        System.out.println("浸泡，需要3小时");
    }
    /**
     * 榨汁
     */
    void beat() {
        System.out.println("榨汁");
    }
    /**
     * 决定是否需要添加配料（钩子方法）
     */
    boolean customerWantCondiments() {
        return true;
    }
}
```

红豆豆浆和花生豆浆不变，代码略

```java
/**
 * 纯豆浆
 */
public class PureSoybeanMilk extends SoybeanMilk {
    @Override
    void addCondiments() {
        // 空实现
    }
    @Override
    boolean customerWantCondiments() {
        return false;
    }
}
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("----制作红豆豆浆----");
        RedBeanSoybeanMilk redBeanSoybeanMilk = new RedBeanSoybeanMilk();
        redBeanSoybeanMilk.make();
        System.out.println("----制作花生豆浆----");
        SoybeanMilk peanutSoybeanMilk = new PeanutSoybeanMilk();
        peanutSoybeanMilk.make();
        System.out.println("----制作纯豆浆----");
        SoybeanMilk pureSoybeanMilk = new PureSoybeanMilk();
        pureSoybeanMilk.make();
    }
}
```

# 总结

1. 基本思想是：算法只存在于一个地方，也就是在父类中，容易修改。需要修改算法时，只要修改父类的模板方法或者已经实现的某些步骤，子类就会继承这些修改
2. 实现了最大化代码复用。父类的模板方法和已实现的某些步骤会被子类继承而直接使用
3. 既统一了算法，也提供了很大的灵活性。父类的模板方法确保了算法的结构保持不变，同时由子类提供部分步骤的实现。
4. 该模式的不足之处：每一个不同的实现都需要一个子类实现，导致类的个数增加，使得系统更加庞大
5. 一般模板方法都加上`final`关键字，防止子类重写模板方法
6. 模板方法模式使用场景：当要完成在某个过程，该过程要执行一系列步骤，这一系列的步骤基本相同，但其个别步骤在实现时可能不同，通常考虑用模板方法模式来处理
