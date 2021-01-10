> 本文档整理自视频教程：

[尚硅谷_图解Java设计模式](http://www.atguigu.com/download_detail.shtml?v=202)

> 示例代码：
GitHub：[https://github.com/Max-Qiu/demo/tree/main/demo-DesignPrinciple](https://github.com/Max-Qiu/demo/tree/main/demo-DesignPrinciple)
Gitee：[https://gitee.com/Max-Qiu/demo/tree/main/demo-DesignPrinciple](https://gitee.com/Max-Qiu/demo/tree/main/demo-DesignPrinciple)

# 设计原则核心思想

1. 找出应用中可能需要变化之处，把它们独立出来，不要和那些不需要变化的代码混在一起。
2. 针对接口编程，而不是针对实现编程。
3. 为了交互对象之间的`松耦合设计`而努力

# 单一职责原则 Single Responsibility Principle

> 一个类应该只负责一项职责。

如类A负责两个不同职责：职责1，职责2。当职责1需求变更而改变A时，可能造成职责2执行错误，所以需要将类A的粒度分解为A1、A2。

## 问题示例

![](https://cdn.maxqiu.com/upload/52473f148ad644698d659ef9a72cee9c.jpg)

```java
/**
 * 交通工具类
 */
class Vehicle {
    /**
     * 职责：运行
     */
    public void run(String vehicle) {
        System.out.println(vehicle + " 在公路上运行....");
    }
}
```

```java
public static void main(String[] args) {
    Vehicle vehicle = new Vehicle();
    vehicle.run("摩托车");
    vehicle.run("汽车");
    vehicle.run("飞机");
}
```

问题：

1. run方法中，违反了单一职责原则
2. 解决方案：根据交通工具运行方法不同，分解成不同类即可

## 改进方案1

![](https://cdn.maxqiu.com/upload/1403f919393d4bc8bd14839a35ef4ffd.jpg)

```java
class RoadVehicle {
    public void run(String vehicle) {
        System.out.println(vehicle + "公路运行");
    }
}

class AirVehicle {
    public void run(String vehicle) {
        System.out.println(vehicle + "天空运行");
    }
}

class WaterVehicle {
    public void run(String vehicle) {
        System.out.println(vehicle + "水中运行");
    }
}
```

```java
public static void main(String[] args) {
    RoadVehicle roadVehicle = new RoadVehicle();
    roadVehicle.run("摩托车");
    roadVehicle.run("汽车");

    AirVehicle airVehicle = new AirVehicle();
    airVehicle.run("飞机");

    WaterVehicle waterVehicle = new WaterVehicle();
    waterVehicle.run("轮船");
}
```

方案1的分析

1. 遵守单一职责原则
2. 问题：改动太大，即将类分解，同时修改客户端
3. 改进：直接修改类，改动的代码会比较少=>方案2

## 改进方案2

![](https://cdn.maxqiu.com/upload/c8ce28b0fd8c4b25bd4cf4d339326a03.jpg)

```java
class Vehicle {
    public void run(String vehicle) {
        // 如果需要对某一个职责处理，仅需要修改方法即可
        System.out.println(vehicle + " 在公路上运行....");

    }

    public void runAir(String vehicle) {
        System.out.println(vehicle + " 在天空上运行....");
    }

    public void runWater(String vehicle) {
        System.out.println(vehicle + " 在水中行....");
    }
}
```

```java
public static void main(String[] args) {
    Vehicle vehicle = new Vehicle();
    vehicle.run("汽车");
    vehicle.runWater("轮船");
    vehicle.runAir("飞机");
}
```

方案2的分析

1. 没有对原来的类做大的修改，只是增加方法
2. 没有在类这个级别上遵守单一职责原则，但是在方法级别上，仍然是遵守单一职责

# 接口隔离原则 Interface Segregation Principle

> 客户端不应该依赖它不需要的接口，即**一个类对另一个类的依赖应该建立在最小的接口上**

## 问题示例

![](https://cdn.maxqiu.com/upload/e96894dc99ad4da1acdff15740e4ae67.jpg)

```java
/**
 * 交通工具接口
 */
interface VehicleInterface {
    /**
     * 运输货物
     */
    void carryGoods();

    /**
     * 在天上运行
     */
    void runningInTheSky();

    /**
     * 在水上运行
     */
    void runningOnWater();
}

/**
 * C919大飞机
 */
class C919Plane implements VehicleInterface {
    @Override
    public void carryGoods() {
        System.out.println("C919大飞机运输货物");
    }

    @Override
    public void runningInTheSky() {
        System.out.println("C919大飞机在天上运行");
    }

    @Override
    public void runningOnWater() {
        System.out.println("C919大飞机不能在水上运行！！！");
    }
}

/**
 * 泰坦尼克号
 */
class Titanic implements VehicleInterface {
    @Override
    public void carryGoods() {
        System.out.println("泰坦尼克号运输货物");
    }

    @Override
    public void runningInTheSky() {
        System.out.println("泰坦尼克号不能在天上运行！！！");
    }

    @Override
    public void runningOnWater() {
        System.out.println("泰坦尼克号在水上运行");
    }
}

/**
 * 飞机驾驶员
 */
class AirPilot {
    public void carryGoods(VehicleInterface i) {
        i.carryGoods();
    }

    public void runningInTheSky(VehicleInterface i) {
        i.runningInTheSky();
    }

    public void runningOnWater(VehicleInterface i) {
        i.runningOnWater();
    }
}

/**
 * 船长
 */
class Captain {
    public void carryGoods(VehicleInterface i) {
        i.carryGoods();
    }

    public void runningInTheSky(VehicleInterface i) {
        i.runningInTheSky();
    }

    public void runningOnWater(VehicleInterface i) {
        i.runningOnWater();
    }
}
```

```java
public static void main(String[] args) {
    AirPilot airPilot = new AirPilot();
    VehicleInterface plane = new C919Plane();
    // AirPilot类通过接口去依赖(使用)C919Plane类
    airPilot.carryGoods(plane);
    airPilot.runningInTheSky(plane);
    airPilot.runningOnWater(plane);

    Captain captain = new Captain();
    VehicleInterface steamship = new Titanic();
    // Captain类通过接口去依赖(使用)Titanic类
    captain.carryGoods(steamship);
    captain.runningInTheSky(steamship);
    captain.runningOnWater(steamship);
}
```

问题：飞机驾驶员类通过运输工具接口依赖C919大飞机类，船长类通过运输工具接口依赖泰坦尼克类，如果运输工具接口对于类飞机驾驶员类和船长类来说不是最小接口，那么C919大飞机类和泰坦尼克类必须去实现他们不需要的方法。

## 改进方案

按隔离原则应当这样处理：将接口拆分为独立的几个接口（运输工具类、飞机类、轮船类），飞机驾驶员类和船长类分别与他们需要的接口建立依赖关系。也就是采用接口隔离原则

![](https://cdn.maxqiu.com/upload/f7d39d0b8667436abc01e66ddb704ed3.jpg)

```java
/**
 * 运输工具
 */
interface VehicleInterface {
    /**
     * 运输货物
     */
    void carryGoods();
}

/**
 * 飞机接口
 */
interface PlaneInterface extends VehicleInterface {
    /**
     * 在天上运行
     */
    void runningInTheSky();
}

/**
 * 轮船接口
 */
interface Steamship extends VehicleInterface {
    /**
     * 在水上运行
     */
    void runningOnWater();
}

/**
 * C919大飞机
 */
class C919Plane implements PlaneInterface {
    @Override
    public void carryGoods() {
        System.out.println("C919大飞机运输货物");
    }

    @Override
    public void runningInTheSky() {
        System.out.println("C919大飞机在天上运行");
    }
}

/**
 * 泰坦尼克号
 */
class Titanic implements Steamship {
    @Override
    public void carryGoods() {
        System.out.println("泰坦尼克号运输货物");
    }

    @Override
    public void runningOnWater() {
        System.out.println("泰坦尼克号在水上运行");
    }
}

/**
 * 飞机驾驶员
 */
class AirPilot {
    public void carryGoods(PlaneInterface i) {
        i.carryGoods();
    }

    public void runningInTheSky(PlaneInterface i) {
        i.runningInTheSky();
    }
}

/**
 * 船长
 */
class Captain {
    public void carryGoods(Steamship i) {
        i.carryGoods();
    }

    public void runningOnWater(Steamship i) {
        i.runningOnWater();
    }
}
```

```java
public static void main(String[] args) {
    AirPilot airPilot = new AirPilot();
    PlaneInterface plane = new C919Plane();
    // AirPilot类通过接口去依赖(使用)C919Plane类
    airPilot.carryGoods(plane);
    airPilot.runningInTheSky(plane);

    Captain captain = new Captain();
    Steamship steamship = new Titanic();
    // Captain类通过接口去依赖(使用)Titanic类
    captain.carryGoods(steamship);
    captain.runningOnWater(steamship);
}
```

改造后：飞机不会有runningOnWater()，轮船不会有runningInTheSky()

# 依赖倒转原则 Dependence Inversion Principle

1. 高层模块不应该依赖低层模块，二者都应该依赖其抽象
2. 抽象不应该依赖细节，细节应该依赖抽象
3. 依赖倒转(倒置)的中心思想是 面向接口编程
4. 依赖倒转原则是基于这样的设计理念：相对于细节的多变性，抽象的东西要稳定的多。以抽象为基础搭建的架构比以细节为基础的架构要稳定的多。在 java 中，抽象指的是接口或抽象类，细节就是具体的实现类
5. 使用 接口或抽象类的目的是制定好 规范，而不涉及任何具体的操作，把 展现细节的任务交给他们的实现类去完成

## 问题示例

![](https://cdn.maxqiu.com/upload/43f62225e7654b0ab118732a5a9ae5bb.jpg)

```java
/**
 * 电子邮件消息
 */
class EmailMessage {
    public String printInfo() {
        return "电子邮件信息: hello,world";
    }
}

class Person {
    /**
     * 接收消息
     */
    public void receiveMessage(EmailMessage emailMessage) {
        System.out.println(emailMessage.printInfo());
    }
}
```

```java
public static void main(String[] args) {
    Person person = new Person();
    person.receiveMessage(new EmailMessage());
}
```

问题分析

1. 简单，比较容易想到
2. 如果我们获取的对象是微信消息，短信消息等等，则新增类，同时Peron也要增加相应的接收方法

解决思路：

引入一个抽象的接口IMessage, 表示消息, 这样Person类与接口IMessage发生依赖，因为EmailMessage, WeiXinMessage 等等属于消息的范围，他们各自实现IMessage接口就ok, 这样我们就符合依赖倒转原则

## 改进方案

![](https://cdn.maxqiu.com/upload/6d98c85157c641e2a250a4048cd74186.jpg)

```java
/**
 * 消息接口
 */
interface IMessage {
    String printInfo();
}

/**
 * 电子邮件消息
 */
class EmailMessage implements IMessage {
    @Override
    public String printInfo() {
        return "电子邮件信息: hello,world";
    }
}

/**
 * 微信消息
 */
class WeiXinMessage implements IMessage {
    @Override
    public String printInfo() {
        return "微信信息: hello,ok";
    }
}

class Person {
    // 依赖接口
    public void receive(IMessage message) {
        System.out.println(message.printInfo());
    }
}
```

```java
public static void main(String[] args) {
    // 客户端无需改变
    Person person = new Person();
    person.receive(new EmailMessage());
    person.receive(new WeiXinMessage());
}
```

## 依赖关系传递的三种方式

示例公用类：

```java
/**
 * 电视机接口
 *
 * @author Max_Qiu
 */
public interface ITv {
    /**
     * 播放
     */
    void play();
}

/**
 * 电视机实例
 *
 * @author Max_Qiu
 */
public class ChangHongTv implements ITv {
    @Override
    public void play() {
        System.out.println("长虹电视机，打开");
    }
}
```

### 接口传递

![](https://cdn.maxqiu.com/upload/2317fac1cfc944789084fd180baba13b.jpg)

```java
// 开关的接口
interface IOpenAndClose {
    // 抽象方法,接收接口
    void open(ITv tv);
}

// 实现接口
class OpenAndClose implements IOpenAndClose {
    @Override
    public void open(ITv tv) {
        tv.play();
    }
}
```

```java
public static void main(String[] args) {
    ChangHongTv changHong = new ChangHongTv();
    IOpenAndClose iOpenAndClose = new OpenAndClose();
    iOpenAndClose.open(changHong);
}
```

### 构造方法传递

![](https://cdn.maxqiu.com/upload/89820e41462043dda7f70c58f282e06d.jpg)

```java
interface IOpenAndClose {
    // 抽象方法
    void open();
}

class OpenAndClose implements IOpenAndClose {
    // 成员变量
    private ITv tv;

    // 构造器
    public OpenAndClose(ITv tv) {
        this.tv = tv;
    }

    @Override
    public void open() {
        this.tv.play();
    }
}
```

```java
public static void main(String[] args) {
    ChangHongTv changHong = new ChangHongTv();
    IOpenAndClose openAndClose = new OpenAndClose(changHong);
    openAndClose.open();
}
```

### setter方法传递

![](https://cdn.maxqiu.com/upload/18714b768644426cbd3ec22d434a990f.jpg)

```java
interface IOpenAndClose {
    // 抽象方法
    void open();

    void setTv(ITv tv);
}

class OpenAndClose implements IOpenAndClose {
    private ITv tv;

    @Override
    public void setTv(ITv tv) {
        this.tv = tv;
    }

    @Override
    public void open() {
        this.tv.play();
    }
}
```

```java
public static void main(String[] args) {
    ChangHongTv changHong = new ChangHongTv();
    IOpenAndClose openAndClose = new OpenAndClose();
    openAndClose.setTv(changHong);
    openAndClose.open();
}
```

# 里氏替换原则 Liskov Substitution Principle

OO中的继承性的思考和说明

1. 继承包含这样一层含义：父类中凡是已经实现好的方法，实际上是在设定规范和契约，虽然它不强制要求所有的子类必须遵循这些契约，但是如果子类对这些已经实现的方法任意修改，就会对整个继承体系造成破坏。
2. 继承在给程序设计带来便利的同时也带来了弊端。比如使用继承会给程序带来侵入性，程序的可移植性降低，增加对象间的耦合性，如果一个类被其他的类所继承，则当这个类需要修改时，必须考虑到所有的子类，并且父类修改后，所有涉及到子类的功能都有可能产生故障

问题：在编程中，如何正确的使用继承? `=>`里氏替换原则

1. 如果对每个类型为T1的对象o1，都有类型为T2的对象o2，使得以T1定义的所有程序P在所有的对象o1都代换成o2时，程序P的行为没有发生变化，那么类型T2是类型T1的子类型。 换句话说，所有引用基类的地方必须能透明地使用其子类的对象。
2. 在使用继承时，遵循里氏替换原则，在子类中尽量不要重写父类的方法
3. 里氏替换原则告诉我们，继承实际上让两个类耦合性增强了，在适当的情况下可以通过聚合、组合、赖依来解决问题。

## 问题示例

![](https://cdn.maxqiu.com/upload/78d5e15e63404e1890cd611fd352bc7f.jpg)

```java
// A类
class ClassA {
    // 返回两个数的差
    public int func1(int num1, int num2) {
        return num1 - num2;
    }
}

// B类继承了A
class ClassB extends ClassA {
    // 这里，重写了A类的方法, 可能是无意识
    @Override
    public int func1(int a, int b) {
        return a + b;
    }

    // 增加了一个新功能：完成两个数相减,然后和9求和
    public int func2(int a, int b) {
        return func1(a, b) + 9;
    }
}
```

```java
public static void main(String[] args) {
    ClassA classA = new ClassA();
    System.out.println("11-3=" + classA.func1(11, 3)); // 11-3=8
    System.out.println("-----------------------");
    ClassB classB = new ClassB();
    // 这里本意是求出11-3，但是结果却是14
    System.out.println("11-3=" + classB.func1(11, 3)); // 11-3=14
    System.out.println("11-3+9=" + classB.func2(11, 3)); // 11-3+9=23
}
```

1. 我们发现原来运行正常的相减功能发生了错误。原因就是类B无意中重写了父类的方法，造成原有功能出现错误。在实际编程中，我们常常会通过重写父类的方法完成新的功能，这样写起来虽然简单，但整个继承体系的复用性会比较差。特别是运行多态比较频繁的时候
2. 通用的做法是：原来的父类和子类都继承一个更基础的基类，原有的继承关系去掉，采用依赖、聚合、组合等关系代替。

## 改进方案

![](https://cdn.maxqiu.com/upload/42d9af1cee414ed59f38cd8d32e68798.jpg)

```java
// 创建一个更加基础的基类
class Base {
    // 把更加基础的方法和成员写到Base类
}

// A类
class ClassA extends Base {
    // 返回两个数的差
    public int func1(int num1, int num2) {
        return num1 - num2;
    }
}

// B类继承了A
class ClassB extends Base {
    // 如果B需要使用A类的方法,使用组合关系
    private ClassA classA = new ClassA();

    public int func1(int a, int b) {
        return a + b;
    }

    // 增加了一个新功能：完成两个数相加,然后和9求和
    public int func2(int a, int b) {
        return func1(a, b) + 9;
    }

    // 使用A的方法
    public int func3(int a, int b) {
        return this.classA.func1(a, b);
    }
}
```

```java
public static void main(String[] args) {
    ClassA classA = new ClassA();
    System.out.println("11-3=" + classA.func1(11, 3)); // 11-3=8
    System.out.println("-----------------------");
    ClassB classB = new ClassB();
    // 因为B类不再继承A类，所以调用者不会再认为func1是求减法，调用的功能就会很明确
    System.out.println("11+3=" + classB.func1(11, 3)); // 11+3=14
    System.out.println("11+3+9=" + classB.func2(11, 3)); // 11+3+9=23
    // 使用组合仍然可以使用到A类相关方法
    System.out.println("11-3=" + classB.func3(11, 3)); // 11-3=8
}
```

# 开闭原则 Open Closed Principle

1. 一个软件实体如类，模块和函数应该对扩展开放（对提供方），对修改关闭（对使用方）。用抽象构建框架，用实现扩展细节。
2. 当软件需要变化时，尽量通过扩展软件实体的行为来实现变化，而不是通过修改已有的代码来实现变化。
3. 编程中遵循其它原则，以及使用设计模式的目的就是遵循开闭原则。

## 问题示例

![](https://cdn.maxqiu.com/upload/a687f73046ef430394a5970f1ff54921.jpg)

```java
// 这是一个用于绘图的类[使用方]
class GraphicEditor {
    // 接收Shape对象，然后根据类型，来绘制不同的图形
    public void drawShape(Shape s) {
        if (s.getType() == 1) {
            drawRectangle(s);
        } else if (s.getType() == 2) {
            drawCircle(s);
        }
        // 新增三角形类型判断
        else if (s.getType() == 3) {
            drawTriangle(s);
        }
    }

    // 绘制矩形
    public void drawRectangle(Shape r) {
        System.out.println(" 绘制矩形 ");
    }

    // 绘制圆形
    public void drawCircle(Shape r) {
        System.out.println(" 绘制圆形 ");
    }

    // 新增绘制三角形
    public void drawTriangle(Shape r) {
        System.out.println(" 绘制三角形 ");
    }
}

// Shape类，基类
class Shape {
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

class Rectangle extends Shape {
    public Rectangle() {
        setType(1);
    }
}

class Circle extends Shape {
    public Circle() {
        setType(2);
    }
}

// 新增画三角形
class Triangle extends Shape {
    public Triangle() {
        setType(3);
    }
}
```

```java
public static void main(String[] args) {
    GraphicEditor graphicEditor = new GraphicEditor();
    graphicEditor.drawShape(new Rectangle());
    graphicEditor.drawShape(new Circle());
    graphicEditor.drawShape(new Triangle());
}
```

1. 优点是比较好理解，简单易操作。
2. 缺点是违反了设计模式的OCP原则，即对扩展开放(提供方)，对修改关闭(使用方)。即当我们给类增加新功能的时候，尽量不修改代码，或者尽可能少修改代码。

比如我们这时要新增加一个图形种类三角形，我们需要修改的地方较多

## 改进方案

> 思路：

把创建Shape类做成抽象类，并提供一个抽象的draw方法，让子类去实现即可，这样我们有新的图形种类时，只需要让新的图形类继承Shape，并实现draw方法即可，修使用方的代码就不需要修`->`满足了开闭原则

![](https://cdn.maxqiu.com/upload/5b7626ec8bb243c4a425bb82f2fd6590.jpg)

```java
// 这是一个用于绘图的类 [使用方]
class GraphicEditor {
    // 接收Shape对象，调用draw方法
    public void drawShape(Shape s) {
        s.draw();
    }
}

// Shape类，基类
abstract class Shape {
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    // 抽象方法
    public abstract void draw();
}

class Rectangle extends Shape {
    public Rectangle() {
        setType(1);
    }

    @Override
    public void draw() {
        System.out.println(" 绘制矩形 ");
    }
}

class Circle extends Shape {
    public Circle() {
        setType(2);
    }

    @Override
    public void draw() {
        System.out.println(" 绘制圆形 ");
    }
}

// 新增画三角形
class Triangle extends Shape {
    public Triangle() {
        setType(3);
    }

    @Override
    public void draw() {
        System.out.println(" 绘制三角形 ");
    }
}
```

```java
public static void main(String[] args) {
    GraphicEditor graphicEditor = new GraphicEditor();
    graphicEditor.drawShape(new Rectangle());
    graphicEditor.drawShape(new Circle());
    graphicEditor.drawShape(new Triangle());
}
```

# 迪米特法则 Demeter Principle

> 又叫`最少知道原则`

一个类对自己依赖的类知道的越少越好。也就是说：对于被依赖的类不管多么复杂，都尽量将逻辑封装在类的内部，对外仅提供的public方法

迪米特法则另一个定义：只与`直接的朋友`通信

直接的朋友：每个对象都会与其他对象有耦合关系，只要两个对象之间有耦合关系，我们就说这两个对象之间是朋友关系。耦合的方式很，多依赖、关联、组合、聚合等。其中，我们称出现成员变量、方法参数、方法返回值中的类为直接的朋友，而出现在局部变量中的类不是直接的朋友。也就是说，陌生的类最好不要以局部变量的形式出现在类的内部。

> 示例代码场景：

有一个学校，下属有总部和各个学院，现要求打印出学校总部员工ID和学院员工的ID

```java
/**
 * 学校总部员工类
 */
public class Employee {
    private String id;

    public Employee setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }
}

/**
 * 学院的员工类
 */
public class CollegeEmployee {
    private String id;

    public CollegeEmployee setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }
}
```

## 问题示例

```java
// 学院管理类
class CollegeManager {
    // 返回学院的所有员工
    public List<CollegeEmployee> getAllEmployee() {
        List<CollegeEmployee> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CollegeEmployee emp = new CollegeEmployee();
            emp.setId("学院员工id= " + i);
            list.add(emp);
        }
        return list;
    }
}

// 学校管理类
class SchoolManager {
    // 返回学校总部的员工
    public List<Employee> getAllEmployee() {
        List<Employee> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {   
            Employee emp = new Employee();
            emp.setId("学校总部员工id= " + i);
            list.add(emp);
        }
        return list;
    }

    // 该方法完成输出学校总部和学院员工信息(id)
    public void printAllEmployee(CollegeManager collegeManager) {
        // 获取到学院员工
        List<CollegeEmployee> list1 = collegeManager.getAllEmployee();
        System.out.println("------------学院员工------------");
        for (CollegeEmployee e : list1) {
            System.out.println(e.getId());
        }
        // 获取到学校总部员工
        List<Employee> list2 = this.getAllEmployee();
        System.out.println("------------学校总部员工------------");
        for (Employee e : list2) {
            System.out.println(e.getId());
        }
    }
}
```

```java
public static void main(String[] args) {
    // 创建了一个 SchoolManager 对象
    SchoolManager schoolManager = new SchoolManager();
    // 输出学院的员工id 和 学校总部的员工信息
    schoolManager.printAllEmployee(new CollegeManager());
}
```

问题：

在`printAllEmployee()`方法中，CollegeEmployee不是SchoolManager的直接朋友，CollegeEmployee是以局部变量方式出现在SchoolManager中，违反了迪米特法则

## 改进方案

```java
// 管理学院员工的管理类
class CollegeManager {
    // 返回学院的所有员工
    public List<CollegeEmployee> getAllEmployee() {
        List<CollegeEmployee> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CollegeEmployee emp = new CollegeEmployee();
            emp.setId("学院员工id= " + i);
            list.add(emp);
        }
        return list;
    }

    // 输出学院员工的信息
    public void printEmployee() {
        // 获取到学院员工
        List<CollegeEmployee> list1 = getAllEmployee();
        System.out.println("------------学院员工------------");
        for (CollegeEmployee e : list1) {
            System.out.println(e.getId());
        }
    }
}

// 学校管理类
class SchoolManager {
    // 返回学校总部的员工
    public List<Employee> getAllEmployee() {
        List<Employee> list = new ArrayList<>();
        // 这里我们增加了5个员工到 list
        for (int i = 0; i < 5; i++) {
            Employee emp = new Employee();
            emp.setId("学校总部员工id= " + i);
            list.add(emp);
        }
        return list;
    }

    // 该方法完成输出学校总部和学院员工信息(id)
    void printAllEmployee(CollegeManager collegeManager) {

        // 将输出学院的员工方法，封装到CollegeManager
        collegeManager.printEmployee();

        // 获取到学校总部员工
        List<Employee> list2 = this.getAllEmployee();
        System.out.println("------------学校总部员工------------");
        for (Employee e : list2) {
            System.out.println(e.getId());
        }
    }
}
```

```java
public static void main(String[] args) {
    System.out.println("~~~使用迪米特法则的改进~~~");
    // 创建了一个 SchoolManager 对象
    SchoolManager schoolManager = new SchoolManager();
    // 输出学院的员工id 和 学校总部的员工信息
    schoolManager.printAllEmployee(new CollegeManager());
}
```

# 合成复用原则 Composite Reuse Principle

> 尽量使用合成/聚合的方式，而不是使用继承