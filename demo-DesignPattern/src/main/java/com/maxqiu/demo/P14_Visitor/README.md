# 访问者模式 Visitor Pattern

> 介绍

封装一些作用于某种数据结构的各元素的操作，它可以在不改变数据结构的前提下定义作用于这些元素的新的操作。主要将数据结构与数据操作分离，解决数据结构和操作耦合性问题

- 基本工作原理：在被访问的类里面加一个对外提供接待访问者的接口
- 主要应用场景：需要对一个对象结构中的对象进行很多不同操作（这些操作彼此没有关联），同时需要避免让这些操作“污染”这些对象的类，可以选用访问者模式解决

> UML类图与角色

![](https://cdn.maxqiu.com/upload/ab5cc26056ab4085a8aca69bc22f4a38.jpg)

- Visitor：是抽象访问者，为该对象结构中的 ConcreteElement 的每一个类声明一个 visit 操作
- ConcreteVisitor：是一个具体的访问值，实现每个有 Visitor 声明的操作，是每个操作实现的部分
- ObjectStructure：能枚举它的元素，可以提供一个高层的接口，用来允许访问者访问元素
- Element：定义一个 accept 方法，接收一个访问者对象
- ConcreteElement：为具体元素，实现了 accept 方法

# 示例

> 情景介绍

将观众分为男人和女人，对歌手进行测评，当看完某个歌手表演后，得到他们对该歌手不同的评价（评价有不同的种类，比如成功、失败等）

> UML类图

![](https://cdn.maxqiu.com/upload/dc3248c4195e405382926251df8bcb72.jpg)

> 代码实现

```java
/**
 * 人（被访问者）
 */
public abstract class Person {
    /**
     * 提供一个方法，让访问者可以访问
     *
     * @param action
     */
    public abstract void accept(Action action);
}
```

```java
/**
 * 女人
 *
 * 说明：
 * 1. 这里我们使用到了双分派, 即首先在客户端程序中，将具体状态作为参数传递Woman中(第一次分派)
 * 2. 然后Woman 类调用作为参数的 "具体方法" 中方法getWomanResult, 同时将自己(this)作为参数传入，完成第二次的分派
 */
public class Woman extends Person {
    @Override
    public void accept(Action action) {
        action.getResult(this);
    }
}
```

```java
/**
 * 男性
 *
 * @author Max_Qiu
 */
public class Man extends Person {
    @Override
    public void accept(Action action) {
        action.getResult(this);
    }
}
```

```java
/**
 * 行动（访问者）
 */
public abstract class Action {

    /**
     * 得到男性 的测评
     */
    public abstract void getResult(Man man);

    /**
     * 得到女性 测评
     */
    public abstract void getResult(Woman woman);

}
```

```java
/**
 * 评价成功
 */
public class Success extends Action {
    @Override
    public void getResult(Man man) {
        System.out.println("男人给的评价该歌手很成功！");
    }
    @Override
    public void getResult(Woman woman) {
        System.out.println("女人给的评价该歌手很成功！");
    }
}
```

```java
/**
 * 评价失败
 */
public class Fail extends Action {
    @Override
    public void getResult(Man man) {
        System.out.println("男人给的评价该歌手失败！");
    }
    @Override
    public void getResult(Woman woman) {
        System.out.println("女人给的评价该歌手失败！");
    }
}
```

```java
/**
 * 评价待定
 */
public class Wait extends Action {
    @Override
    public void getResult(Man man) {
        System.out.println("男人给的评价是该歌手待定。。。");
    }
    @Override
    public void getResult(Woman woman) {
        System.out.println("女人给的评价是该歌手待定。。。");
    }
}
```

```java
/**
 * 数据结构，管理很多人（Man , Woman）
 */
public class ObjectStructure {
    // 维护了一个集合
    private List<Person> persons = new LinkedList<>();
    /**
     * 增加
     */
    public void attach(Person p) {
        persons.add(p);
    }
    /**
     * 移除
     */
    public void detach(Person p) {
        persons.remove(p);
    }
    /**
     * 显示测评情况
     */
    public void display(Action action) {
        for (Person p : persons) {
            p.accept(action);
        }
    }
}
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        // 创建ObjectStructure
        ObjectStructure objectStructure = new ObjectStructure();
        objectStructure.attach(new Man());
        objectStructure.attach(new Woman());
        // 成功
        Success success = new Success();
        objectStructure.display(success);
        System.out.println("===============");
        // 失败
        Fail fail = new Fail();
        objectStructure.display(fail);
        System.out.println("===============");
        // 待定
        Wait wait = new Wait();
        objectStructure.display(wait);
    }
}
```

# 总结

- 优点
    1. 访问者模式符合单一职责原则、让程序具有优秀的扩展性、灵活性非常高
    2. 访问者模式可以对功能进行统一，可以做报表、UI、拦截器与过滤器，适用于数据结构相对稳定的系统
- 缺点
    1. 具体元素对访问者公布细节，也就是说访问者关注了其他类的内部细节，这是迪米特法则所不建议的，这样造成了具体元素变更比较困难
    2. 违背了依赖倒转原则。访问者依赖的是具体元素，而不是抽象元素
    3. 如果一个系统有比较稳定的数据结构，又有经常变化的功能需求，那么访问者模式就是比较合适的
