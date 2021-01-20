# 情景介绍

- 出差时，国外的插座是欧标、美标等，国内的插座是国标，需要使用多功能转换插头（适配器）
- 充电时，电网是220V，手机需要5V，需要使用充电器（适配器）

# 适配器模式 Adapter Pattern

## 基本介绍

适配器模式将某个类的接口转换成客户端期望的另一个接口表示，实现兼容性，让原本因接口不匹配不能一起工作的两个类可以协同工作。其别名为包装器（Wrapper）

1. 从用户的角度看不到被适配者，是解耦的。
2. 用户调用适配器转化出来的目标接口方法，适配器再调用被适配者的相关接口方法
3. 用户收到反馈结果，感觉只是和目标接口交互

> 角色

- Adapter：适配器
- src：被适配者
- dst：目标输出

## 类适配器模式

> 详细介绍

`Adapter`类，通过继承`src`类，实现`dst`类接口，完成`src -> dst`的适配。

- 充电器本身相当于`Adapter`（即适配器）
- 220V交流电相当于`src`（即被适配者）
- 5V直流电相当于`dst`(即目标输出)

> UML类图

![](https://cdn.maxqiu.com/upload/5f96e9372cc14b969f657aa62d420441.jpg)

> 代码实现

```java
/**
 * 220V电压
 */
public class Voltage220V {
    /**
     * 输出220V的电压
     */
    public Integer output220V() {
        return 220;
    }
}
```

```java
/**
 * 适配器接口
 */
public interface IVoltage5V {
    /**
     * 适配器需要实现输出5V电压的功能
     */
    Integer output5V();
}
```

```java
/**
 * 充电器
 */
public class VoltageAdapter extends Voltage220V implements IVoltage5V {
    /**
     * 实现输出5V
     */
    @Override
    public Integer output5V() {
        // 获取到220V电压
        int srcV = output220V();
        // 转成5V
        return srcV / 44;
    }
}
```

```java
/**
 * 手机
 */
public class MobilePhone {
    /**
     * 充电（需要一个实现5V转换接口的类）
     */
    public void charging(IVoltage5V iVoltage5V) {
        if (iVoltage5V.output5V() == 5) {
            System.out.println("电压为5V, 可以充电~~");
        } else {
            System.out.println("电压不是5V, 不能充电~~");
        }
    }
}
```

```java
public class Client {
    /**
     * 充电
     */
    public static void main(String[] args) {
        // 准备一个手机
        MobilePhone phone = new MobilePhone();
        // 准备一个适配器
        IVoltage5V voltage5V = new VoltageAdapter();
        // 充电
        phone.charging(voltage5V);
    }
}
```

> 注意事项

1. Java是单继承机制，所以类适配器需要继承`src`类这一点算是一个缺点，因为这要求`dst`必须是接口，有一定局限性。
2. `src`类的方法在`Adapter`中都会暴露出来，不安全，也增加了使用的成本。
3. 由于其继承了`src`类，所以它可以根据需求重写`src`类的方法，使得`Adapter`的灵活性增强了。
4. 使用了继承，不符合**合成复用原则**，即尽量少使用继承

## 对象适配器模式

> 详细介绍

1. 基本思路和**类适配器模式**相同，只是将`Adapter`类作修改，不是继承`src`类，而是持有`src`类的实例，以解决兼容性的问题。即：持有`src`类，实现`dst`类接口，完成`src->dst`的适配
2. 根据**合成复用原则**，在系统中尽量使用**关联关系（聚合）**来替代继承关系。
3. 对象适配器模式是适配器模式常用的一种

> UML类图

![](https://cdn.maxqiu.com/upload/d3b8c81da9704c179193d81b46797589.jpg)

> 代码实现

仅需修改`VoltageAdapter`以及调用方式`Client`

```java
/**
 * 充电器
 */
public class VoltageAdapter implements IVoltage5V {
    private Voltage220V voltage220V;
    /**
     * 通过构造方法传入220V电压，即充电器要工作必须有220V电压
     */
    public VoltageAdapter(Voltage220V voltage220V) {
        this.voltage220V = voltage220V;
    }
    /**
     * 实现输出5V
     */
    @Override
    public Integer output5V() {
        int dst = 0;
        if (null != voltage220V) {
            // 获取220V 电压并转换
            dst = voltage220V.output220V() / 44;
        }
        return dst;
    }
}
```

```java
public class Client {
    /**
     * 充电
     */
    public static void main(String[] args) {
        // 准备220V电压
        Voltage220V voltage220V = new Voltage220V();
        // 准备5V充电器，并插入220电压
        IVoltage5V voltage5V = new VoltageAdapter(voltage220V);
        // 准备手机
        MobilePhone phone = new MobilePhone();
        // 充电
        phone.charging(voltage5V);
    }
}
```

> 注意事项

1. 对象适配器和类适配器其实算是同一种思想，只不过实现方式不同。根据合成复用原则，使用组合替代继承，所以它解决了类适配器必须继承`src`的局限性问题，也不再要求`dst`必须是接口。
2. 使用成本更低，更灵活。
3. 但是：从用户角度看，需要准备被适配者，一定程度上增加了耦合

PS：最后一条是我自己总结的，总觉得应该在`Voltage220V`内直接`new Voltage220V()`

## 接口适配器模式

PS：这个总结的可能不太对

> 详细介绍

又称：**默认适配器模式**`Default Adapter Pattern`或**缺省适配器模式**

核心思路：

当不需要全部实现接口提供的方法时，可先设计一个抽象类实现接口，并为该接口中每个方法提供一个默认实现（空方法），那么该抽象类的子类可有选择地覆盖父类的某些方法来实现需求，适用于一个接口不想使用其所有的方法的情况。

> UML类图

![](https://cdn.maxqiu.com/upload/8dfec357fb934751924d966cc2b7cab6.jpg)

> 代码实现

`Voltage220V`不需要修改，`MobilePhone`仅需要修改为`IVoltage`

```java
/**
 * 通用电源适配器
 */
public interface IVoltage {
    Integer output5V();
    Integer output9V();
    Integer output12V();
}
```

```java
/**
 * 通用电源适配器的默认实现
 */
public abstract class AbsAdapter implements IVoltage {
    @Override
    public Integer output5V() {
        return null;
    }
    @Override
    public Integer output9V() {
        return null;
    }
    @Override
    public Integer output12V() {
        return null;
    }
}
```

```java
public class Client {
    public static void main(String[] args) {
        MobilePhone mobilePhone = new MobilePhone();
        Voltage220V voltage220V = new Voltage220V();
        AbsAdapter mobileAdapter = new AbsAdapter() {
            @Override
            public Integer output5V() {
                // 重写电源转换方法
                return voltage220V.output220V() / 44;
            }
        };
        mobilePhone.charging(mobileAdapter);
    }
}
```

# 总结

- 三种命名方式，是根据`src`是以怎样的形式给到`Adapter`（在`Adapter`里的形式）来命名的。
  1. 类适配器：以类给到，在`Adapter`里，就是将`src`当做类，继承
  2. 对象适配器：以对象给到，在`Adapter`里，将`src`作为一个对象，持有
  3. 接口适配器：以接口给到，在`Adapter`里，将`src`作为一个接口，实现
- 适配器模式最大的作用还是将原本不兼容的接口融合在一起工作。
- 实际开发中，实现起来不拘泥于三种经典形式
