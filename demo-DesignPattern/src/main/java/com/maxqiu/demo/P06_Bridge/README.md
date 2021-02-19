> 说明

1. 尚硅谷视频用手机样式和手机品牌举例，这里使用通知类型和通知方式作为案例
2. 文章部分内容摘自`极客时间`：[49 | 桥接模式：如何实现支持不同类型和渠道的消息推送系统？](https://time.geekbang.org/column/article/202786)（该文章收费，可以试读）

# 情景介绍

针对不同类型的通知实现不同方式的消息发送

![](https://cdn.maxqiu.com/upload/586bcfd6eadd407daba77a82471040d0.jpg)

问题分析

- 扩展性问题（类爆炸）：如果再增加通知的类型，就需要增加各个通知方式的类，同样如果增加通知方式，也要在各个通知类型下增加。
- 违反了单一职责原则，当我们增加通知类型时，要同时增加所有通知方式，这样增加了代码维护成本。

解决方案 -> 桥接模式

# 桥接模式 Bridge Pattern

> 介绍

- 理解方式1：将抽象和实现解耦，让它们可以独立变化。
- 理解方式2：一个类存在多个独立变化的维度，我们通过组合的方式，让多个维度可以独立进行扩展。

> UML类图

![](https://cdn.maxqiu.com/upload/6262837861dc4c049ac5c208488a0310.jpg)

> 代码实现

```java
/**
 * 发送消息接口
 */
public interface IMessageSender {
    void send(String message);
}
```

```java
/**
 * 发送短信消息
 */
public class SmsMsgSender implements IMessageSender {
    @Override
    public void send(String message) {
        System.out.println(message + "\t使用短信发送");
    }
}
```

```java
/**
 * 发送邮件消息
 */
public class EmailMsgSender implements IMessageSender {
    @Override
    public void send(String message) {
        System.out.println(message + "\t使用邮件发送");
    }
}
```

```java
/**
 * 消息类型抽象类
 */
public abstract class AbsNotification {
    protected IMessageSender sender;
    public AbsNotification(IMessageSender sender) {
        this.sender = sender;
    }
    public abstract void notify(String message);
}
```

```java
/**
 * 严重的消息
 */
public class SevereNotification extends AbsNotification {
    public SevereNotification(IMessageSender sender) {
        super(sender);
    }
    @Override
    public void notify(String message) {
        sender.send(message + "\t严重的");
    }
}
```

```java
/**
 * 普通的消息
 */
public class NormalNotification extends AbsNotification {
    public NormalNotification(IMessageSender sender) {
        super(sender);
    }
    @Override
    public void notify(String message) {
        sender.send(message + "\t普通的");
    }
}
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        String message = "报警啦！";
        IMessageSender emailMsgSender = new EmailMsgSender();
        IMessageSender smsMsgSender = new SmsMsgSender();
        System.out.println("--------------------");
        AbsNotification severeNotification1 = new SevereNotification(emailMsgSender);
        severeNotification1.notify(message);
        AbsNotification severeNotification2 = new SevereNotification(smsMsgSender);
        severeNotification2.notify(message);
        System.out.println("--------------------");
        AbsNotification normalNotification1 = new NormalNotification(emailMsgSender);
        normalNotification1.notify(message);
        AbsNotification normalNotification2 = new NormalNotification(smsMsgSender);
        normalNotification2.notify(message);
    }
}
```

运行结果：

    --------------------
    报警啦！	严重的	使用邮件发送
    报警啦！	严重的	使用短信发送
    --------------------
    报警啦！	普通的	使用邮件发送
    报警啦！	普通的	使用短信发送

# 总结

- 对于第一种理解方式，弄懂定义中“抽象”和“实现”两个概念，是理解它的关键。
  - 定义中的“抽象”，指的并非“抽象类”或“接口”，而是被抽象出来的一套“类库”，它只包含骨架代码，真正的业务逻辑需要委派给定义中的“实现”来完成。
  - 而定义中的“实现”，也并非“接口的实现类”，而是的一套独立的“类库”。
  - “抽象”和“实现”独立开发，通过对象之间的组合关系，组装在一起。
- 对于第二种理解方式，它非常类似“组合优于继承”设计原则，通过组合关系来替代继承关系，避免继承层次的指数级爆炸。
