# 中介模式 Mediator Pattern

> 简介

用一个中介对象来封装一系列的对象交互。中介者使各个对象不需要显式地相互引用，从而使其耦合松散，而且可以独立地改变它们之间的交互。中介者模式属于行为型模式，使代码易于维护。比如 MVC 模式，C（Controller 控制器）是 M（Model 模型）和 V（View 视图）的中介者，在前后端交互时起到了中间人的作用

> UML类图与角色

![](https://cdn.maxqiu.com/upload/7f11ef770872431eaafd6136dc61a673.jpg)

- Mediator：就是抽象中介者，定义了同事对象到中介者对象的接口
- Colleague：是抽象同事类
- ConcreteMediator：具体的中介者对象，实现抽象方法，他需要知道所有的具体的同事类，即以一个集合来管理HashMap，并接受某个同事对象消息，完成相应的任务
- ConcreteColleague：具体的同事类，会有很多，每个同事只知道自己的行为，而不了解其他同事类的行为(方法)，但是他们都依赖中介者对象

# 示例

> 情景介绍

智能家庭项目：

- 智能家庭包括各种设备，闹钟、咖啡机、电视机、窗帘等
- 主人要看电视时，各个设备可以协同工作，自动完成看电视的准备工作，比如流程为：闹铃响起->咖啡机开始做咖啡->窗帘自动落下->电视机开始播放

## 传统方案

- 当各电器对象有多种状态改变时，相互之间的调用关系会比较复杂
- 各个电器对象彼此联系，你中有我，我中有你，不利于松耦合。
- 各个电器对象之间所传递的消息（参数），容易混乱
- 当系统增加一个新的电器对象时，或者执行流程改变时，代码的可维护性、扩展性都不理想

> 代码略

## 改进方案

> UML类图

![](https://cdn.maxqiu.com/upload/bb6490ec35e744d59ed691b0800c994f.jpg)

> 代码实现

```java
/**
 * 中介着
 */
public abstract class Mediator {
    /**
     * 将给中介者对象，加入到集合中
     */
    public abstract void register(Colleague colleague);
    /**
     * 接收消息, 具体的同事对象发出
     */
    public abstract void getMessage(int stateChange, int id);
}
```

```java
/**
 * 同事抽象类
 */
public abstract class Colleague {
    protected Integer id;
    private Mediator mediator;
    public Colleague(Integer id, Mediator mediator) {
        this.id = id;
        this.mediator = mediator;
    }
    public Mediator getMediator() {
        return this.mediator;
    }
    public abstract void sendMessage(int stateChange);
}
```

```java
/**
 * 具体的中介者类
 */
public class ConcreteMediator extends Mediator {
    /**
     * 集合，放入所有的同事对象
     */
    private HashMap<Integer, Colleague> colleagueMap = new HashMap<>();
    @Override
    public void register(Colleague colleague) {
        colleagueMap.put(colleague.id, colleague);
    }
    /**
     * 具体中介者的核心方法
     * 1. 根据得到消息，完成对应任务
     * 2. 中介者在这个方法，协调各个具体的同事对象，完成任务
     */
    @Override
    public void getMessage(int stateChange, int id) {
        // 处理闹钟发出的消息
        if (colleagueMap.get(id) instanceof Alarm) {
            if (stateChange == 0) {
                ((CoffeeMachine)(colleagueMap.get(1))).startCoffee();
                ((Television)(colleagueMap.get(4))).startTv();
            } else if (stateChange == 1) {
                ((Television)(colleagueMap.get(4))).stopTv();
            }
        } else if (colleagueMap.get(id) instanceof CoffeeMachine) {
            ((Curtains)(colleagueMap.get(2))).upCurtains();
        } else if (colleagueMap.get(id) instanceof Television) {
            // 如果电视发出消息，这里处理
        } else if (colleagueMap.get(id) instanceof Curtains) {
            // 如果窗帘发出消息，这里处理
        }
    }
}
```

```java
/**
 * 闹钟（具体的同事类）
 */
public class Alarm extends Colleague {
    public Alarm(Mediator mediator) {
        super(3, mediator);
        // 在创建Alarm 同事对象时，将自己放入到ConcreteMediator 对象中[集合]
        mediator.register(this);
    }
    @Override
    public void sendMessage(int stateChange) {
        // 调用的中介者对象的getMessage
        this.getMediator().getMessage(stateChange, this.id);
    }
}
```

```java
/**
 * 咖啡机（具体的同事类）
 */
public class CoffeeMachine extends Colleague {
    public CoffeeMachine(Mediator mediator) {
        super(1, mediator);
        mediator.register(this);
    }
    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.id);
    }
    public void startCoffee() {
        System.out.println("It's time to startCoffee!");
    }
    public void finishCoffee() {
        System.out.println("After 5 minutes!");
        System.out.println("Coffee is ok!");
        sendMessage(1);
    }
}
```

```java
/**
 * 窗帘（具体的同事类）
 */
public class Curtains extends Colleague {
    public Curtains(Mediator mediator) {
        super(2, mediator);
        mediator.register(this);
    }
    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.id);
    }
    public void upCurtains() {
        System.out.println("I am holding Up Curtains!");
    }
}
```

```java
/**
 * 电视剧（具体的同事类）
 */
public class Television extends Colleague {
    public Television(Mediator mediator) {
        super(4, mediator);
        mediator.register(this);
    }
    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.id);
    }
    public void startTv() {
        System.out.println("It's time to StartTv!");
    }
    public void stopTv() {
        System.out.println("StopTv!");
    }
}
```

# 总结

1. 多个类相互耦合，会形成网状结构，使用中介者模式将网状结构分离为星型结构，进行解耦
2. 减少类间依赖，降低了耦合，符合迪米特原则
3. 中介者承担了较多的责任，一旦中介者出现了问题，整个系统就会受到影响
4. 如果设计不当，中介者对象本身变得过于复杂，这点在实际使用时，要特别注意
