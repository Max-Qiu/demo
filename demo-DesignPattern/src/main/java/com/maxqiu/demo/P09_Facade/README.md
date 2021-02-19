推荐阅读：[漫画：设计模式之 “外观模式”](https://mp.weixin.qq.com/s/b2N4kkX4_KPffl7Kt5x4iA)

# 外观模式 Facade Pattern

> 简介

另称为“**门面模式**”。

- 外观模式为子系统中的一组接口提供一个一致的界面，此模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。
- 外观模式通过定义一个一致的接口，用以屏蔽内部子系统的细节，使得调用端只需跟这个接口发生调用，而无需关心这个子系统的内部细节

> 角色

1. 外观类：为调用端提供统一的调用接口，外观类知道哪些子系统负责处理请求，从而将调用端的请求代理给适当子系统对象
2. 调用者：外观接口的调用者
3. 子系统：指模块或者子系统，处理外观类指派的任务，他是功能的实际提供者

# 示例

## 情景介绍

家庭影院包含灯光、投影仪、播放器，每个设备有对应的打开关闭等操作。

```java
/**
 * 灯光
 */
public class Light {
    public void on() {
        System.out.println("灯光\t打开");
    }
    public void off() {
        System.out.println("灯光\t关闭");
    }
    public void faint() {
        System.out.println("灯光\t微光");
    }
}
```

```java
/**
 * 播放器
 */
public class Player {
    public void on() {
        System.out.println("播放器\t打开");
    }
    public void off() {
        System.out.println("播放器\t关闭");
    }
    public void play() {
        System.out.println("播放器\t播放");
    }
    public void pause() {
        System.out.println("播放器\t暂停");
    }
}
```

```java
/**
 * 投影仪
 */
public class Projector {
    public void on() {
        System.out.println("投影仪\t打开");
    }
    public void focus() {
        System.out.println("投影仪\t校正");
    }
    public void off() {
        System.out.println("投影仪\t关闭");
    }
}
```

现在需要实现如下输出

    --- 准备播放 ---
    投影仪	打开
    播放器	打开
    投影仪	校正
    --- 开始播放 ---
    灯光	关闭
    播放器	播放
    --- 暂停播放 ---
    灯光	微光
    播放器	暂停
    --- 继续播放 ---
    灯光	关闭
    播放器	播放
    --- 关闭播放 ---
    灯光	打开
    播放器	关闭
    投影仪	关闭

## 传统模式

客户端调用时分别`new`出对应的对象，并调用对应的方法。

代码略

## 外观模式

> 基本思路

新建一个外观类统一管理子系统

> UML类图

![](https://cdn.maxqiu.com/upload/ae6daf110eff4513a8d11a12db312d87.jpg)

> 代码实现

```java
/**
 * 家庭影院（外观类）
 */
public class HomeTheatre {
    private Light light;
    private Player player;
    private Projector projector;
    public void ready() {
        projector.on();
        player.on();
        projector.focus();
    }
    public void play() {
        light.off();
        player.play();
    }
    public void pause() {
        light.faint();
        player.pause();
    }
    public void end() {
        light.on();
        player.off();
        projector.off();;
    }
    public HomeTheatre() {
        light = new Light();
        player = new Player();
        projector = new Projector();
    }
}
```

```java
/**
 * 使用者
 */
public class Client {
    public static void main(String[] args) {
        HomeTheatre theatre = new HomeTheatre();
        System.out.println("--- 准备播放 ---");
        theatre.ready();
        System.out.println("--- 开始播放 ---");
        theatre.play();
        System.out.println("--- 暂停播放 ---");
        theatre.pause();
        System.out.println("--- 继续播放 ---");
        theatre.play();
        System.out.println("--- 关闭播放 ---");
        theatre.end();
    }
}
```

# 总结

- 外观模式对外屏蔽了子系统的细节，因此外观模式降低了客户端对子系统使用的复杂性
- 外观模式对客户端与子系统的耦合关系 - 解耦，让子系统内部的模块更易维护和扩展
- 通过合理的使用外观模式，可以帮我们更好的划分访问的层次
- 当系统需要进行分层设计时，可以考虑使用外观模式
- 在维护一个遗留的大型系统时，可能这个系统已经变得非常难以维护和扩展，此时可以考虑为新系统开发一个外观类，来提供遗留系统的比较清晰简单的接口，让新系统与外观类交互，提高复用性
- 不能过多的或者不合理的使用外观模式，使用外观模式好，还是直接调用模块好。要以让系统有层次，利于维护为目的。
