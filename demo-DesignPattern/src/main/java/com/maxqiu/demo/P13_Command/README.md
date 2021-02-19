# 命令模式 Command Pattern

> 介绍

- 在软件设计中，我们经常需要向某些对象发送请求，但是并不知道请求的接收者是谁，也不知道被请求的操作是哪个，我们只需在程序运行时指定具体的请求接收者即可，此时，可以使用命令模式来进行设计
- 命名模式使得请求发送者与请求接收者消除彼此之间的耦合，让对象之间的调用关系更加灵活，实现解耦。
- 在命名模式中，会将一个请求封装为一个对象，以便使用不同参数来表示不同的请求（即命名），同时命令模式也支持可撤销的操作。
- 通俗易懂的理解：将军发布命令，士兵去执行。其中有几个角色：将军（命令发布者）、士兵（命令的具体执行者）、命令(连接将军和士兵)。
  - Invoker 是调用者（将军）
  - Receiver 是被调用者（士兵）
  - MyCommand 是命令，实现了 Command 接口，持有接收对象

> UML类图与角色

![](https://cdn.maxqiu.com/upload/c2deec17a29e45ff9214f596a329b82e.jpg)

- Invoker：是调用者角色
- Command：是命令角色，需要执行的所有命令都在这里，可以是接口或抽象类
- Receiver：接受者角色，知道如何实施和执行一个请求相关的操作
- ConcreteCommand：将一个接受者对象与一个动作绑定，调用接受者相应的操作，实现 execute

# 示例

> 情景介绍

- 我们买了一套智能家电，有照明灯、风扇、冰箱、洗衣机，我们只要在手机上安装 app 就可以控制对这些家电工作。
- 这些智能家电来自不同的厂家，我们不想针对每一种家电都安装一个App，分别控制，我们希望只要一个 app 就可以控制全部智能家电。
- 要实现一个 app 控制所有智能家电的需要，则每个智能家电厂家都要提供一个统一的接口给 app 调用，这时就可以考虑使用命令模式。
- 命令模式可将“动作的请求者”从“动作的执行者”对象中解耦出来.
- 在例子中，动作的请求者是手机app，动作的执行者是每个厂商的一个家电产品

> UML类图

![](https://cdn.maxqiu.com/upload/cb9e14a6b8844164b27a2ade8b62e326.jpg)

> 代码实现


```java
/**
 * 创建命令接口
 */
public interface Command {
    /**
     * 执行操作
     */
    void execute();
    /**
     * 撤销操作
     */
    void undo();
}
```

```java
/**
 * 灯光开启命令
 */
public class LightOnCommand implements Command {
    /**
     * 聚合LightReceiver
     */
    LightReceiver light;
    /**
     * 构造器
     */
    public LightOnCommand(LightReceiver light) {
        super();
        this.light = light;
    }
    @Override
    public void execute() {
        // 调用接收者的方法
        light.on();
    }
    @Override
    public void undo() {
        // 调用接收者的方法
        light.off();
    }
}
```

```java
/**
 * 灯光关闭命令
 */
public class LightOffCommand implements Command {
    /**
     * 聚合LightReceiver
     */
    LightReceiver light;
    /**
     * 构造器
     *
     * @param light
     */
    public LightOffCommand(LightReceiver light) {
        this.light = light;
    }
    @Override
    public void execute() {
        // 调用接收者的方法
        light.off();
    }
    @Override
    public void undo() {
        // 调用接收者的方法
        light.on();
    }
}
```

```java
/**
 * 灯光接收者
 */
public class LightReceiver {
    public void on() {
        System.out.println(" 电灯打开了.. ");
    }
    public void off() {
        System.out.println(" 电灯关闭了.. ");
    }
}
```

```java
/**
 * 电视打开命令
 */
public class TvOnCommand implements Command {
    /**
     * 聚合TVReceiver
     */
    TVReceiver tv;
    /**
     * 构造器
     *
     * @param tv
     */
    public TvOnCommand(TVReceiver tv) {
        super();
        this.tv = tv;
    }
    @Override
    public void execute() {
        // 调用接收者的方法
        tv.on();
    }
    @Override
    public void undo() {
        // 调用接收者的方法
        tv.off();
    }
}
```

```java
/**
 * 电视关闭接口
 */
public class TvOffCommand implements Command {
    /**
     * 聚合TVReceiver
     */
    TVReceiver tv;
    /**
     * 构造器
     *
     * @param tv
     */
    public TvOffCommand(TVReceiver tv) {
        super();
        this.tv = tv;
    }
    @Override
    public void execute() {
        // 调用接收者的方法
        tv.off();
    }
    @Override
    public void undo() {
        // 调用接收者的方法
        tv.on();
    }
}
```

```java
/**
 * 电视接收者
 */
public class TvReceiver {
    public void on() {
        System.out.println(" 电视机打开了.. ");
    }
    public void off() {
        System.out.println(" 电视机关闭了.. ");
    }
}
```

```java
/**
 * 没有任何命令，即空执行: 用于初始化每个按钮, 当调用空命令时，对象什么都不做
 */
public class NoCommand implements Command {
    @Override
    public void execute() {}
    @Override
    public void undo() {}
}
```

```java
/**
 * 遥控器
 */
public class RemoteController {
    /**
     * 开 按钮的命令数组
     */
    Command[] offCommands;
    /**
     * 关 按钮的命令数组
     */
    Command[] onCommands;
    /**
     * 执行撤销的命令
     */
    Command undoCommand;
    /**
     * 构造器，完成对按钮初始化
     */
    public RemoteController() {
        onCommands = new Command[5];
        offCommands = new Command[5];
        for (int i = 0; i < 5; i++) {
            onCommands[i] = new NoCommand();
            offCommands[i] = new NoCommand();
        }
    }
    /**
     * 给我们的按钮设置你需要的命令
     */
    public void setCommand(int no, Command onCommand, Command offCommand) {
        onCommands[no] = onCommand;
        offCommands[no] = offCommand;
    }
    /**
     * 按下开按钮
     */
    public void onButtonWasPushed(int no) {
        // 找到你按下的开的按钮， 并调用对应方法
        onCommands[no].execute();
        // 记录这次的操作，用于撤销
        undoCommand = onCommands[no];

    }
    /**
     * 按下关按钮
     */
    public void offButtonWasPushed(int no) {
        // 找到你按下的关的按钮， 并调用对应方法
        offCommands[no].execute();
        // 记录这次的操作，用于撤销
        undoCommand = offCommands[no];

    }
    /**
     * 按下撤销按钮
     */
    public void undoButtonWasPushed() {
        undoCommand.undo();
    }
}
```

```java
/**
 * 客户端
 *
 * @author Max_Qiu
 */
public class Client {
    /**
     * 使用命令设计模式，完成通过遥控器，对电灯的操作
     */
    public static void main(String[] args) {
        // 创建电灯的对象(接受者)
        LightReceiver lightReceiver = new LightReceiver();

        // 创建电灯相关的开关命令
        LightOnCommand lightOnCommand = new LightOnCommand(lightReceiver);
        LightOffCommand lightOffCommand = new LightOffCommand(lightReceiver);

        // 需要一个遥控器
        RemoteController remoteController = new RemoteController();

        // 给我们的遥控器设置命令, 比如 no = 0 是电灯的开和关的操作
        remoteController.setCommand(0, lightOnCommand, lightOffCommand);

        System.out.println("--------按下灯的开按钮-----------");
        remoteController.onButtonWasPushed(0);
        System.out.println("--------按下灯的关按钮-----------");
        remoteController.offButtonWasPushed(0);
        System.out.println("--------按下撤销按钮-----------");
        remoteController.undoButtonWasPushed();

        System.out.println("=========使用遥控器操作电视机==========");

        TvReceiver tvReceiver = new TvReceiver();

        TvOffCommand tvOffCommand = new TvOffCommand(tvReceiver);
        TvOnCommand tvOnCommand = new TvOnCommand(tvReceiver);

        // 给我们的遥控器设置命令, 比如 no = 1 是电视机的开和关的操作
        remoteController.setCommand(1, tvOnCommand, tvOffCommand);

        System.out.println("--------按下电视机的开按钮-----------");
        remoteController.onButtonWasPushed(1);
        System.out.println("--------按下电视机的关按钮-----------");
        remoteController.offButtonWasPushed(1);
        System.out.println("--------按下撤销按钮-----------");
        remoteController.undoButtonWasPushed();
    }
}
```

输出如下

    --------按下灯的开按钮-----------
     电灯打开了..
    --------按下灯的关按钮-----------
     电灯关闭了..
    --------按下撤销按钮-----------
     电灯打开了..
    =========使用遥控器操作电视机==========
    --------按下电视机的开按钮-----------
     电视机打开了..
    --------按下电视机的关按钮-----------
     电视机关闭了..
    --------按下撤销按钮-----------
     电视机打开了..

# 总结

1. 将发起请求的对象与执行请求的对象解耦。发起请求的对象是调用者，调用者只要调用命令对象的execute()方法就可以让接收者工作，而不必知道具体的接收者对象是谁、是如何实现的，命令对象会负责让接收者执行请求的动作，也就是说：“请求发起者”和“请求执行者”之间的解耦是通过命令对象实现的，命令对象起到了纽带桥梁的作用。
2. 容易设计一个命令队列。只要把命令对象放到列队，就可以多线程的执行命令
3. 容易实现对请求的撤销和重做
4. 命令模式不足：可能导致某些系统有过多的具体命令类，增加了系统的复杂度，这点在在使用的时候要注意
5. 空命令也是一种设计模式，它为我们省去了判空的操作。在上面的实例中，如果没有用空命令，我们每按下一个按键都要判空。
