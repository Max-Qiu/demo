# 情景介绍

建房子：

- 过程：打桩、砌墙、封顶
- 类型：别墅、高楼

# 原始方案

```java
/**
 * 基础房子
 */
public abstract class AbstractHouse {
    /**
     * 打地基
     */
    public abstract void buildBasic();
    /**
     * 砌墙
     */
    public abstract void buildWalls();
    /**
     * 封顶
     */
    public abstract void roofed();
    /**
     * 建造
     */
    public void build() {
        buildBasic();
        buildWalls();
        roofed();
    }
}
```

```java
/**
 * 别墅
 */
public class Villa extends AbstractHouse {
    @Override
    public void buildBasic() {
        System.out.println(" 别墅打地基 ");
    }
    @Override
    public void buildWalls() {
        System.out.println(" 别墅砌墙 ");
    }
    @Override
    public void roofed() {
        System.out.println(" 别墅封顶 ");
    }
}
```

```java
/**
 * 高楼
 */
public class HighBuilding extends AbstractHouse {
    @Override
    public void buildBasic() {
        System.out.println(" 高楼打地基 ");
    }
    @Override
    public void buildWalls() {
        System.out.println(" 高楼砌墙 ");
    }
    @Override
    public void roofed() {
        System.out.println(" 高楼封顶 ");
    }
}
```

```java
public static void main(String[] args) {
    Villa villa = new Villa();
    villa.build();
    System.out.println("--------------------------");
    HighBuilding highBuilding = new HighBuilding();
    highBuilding.build();
}
```

> 问题分析

设计的程序结构过于简单，没有设计缓存层对象，程序的扩展和维护不好。也就是说，这种设计方案，把产品（即：房子）和创建产品的过程（即：建房子流程）封装在一起，耦合性增强了。

> 解决方案

将产品和产品建造过程解耦 -> 建造者模式

# 建造者模式 Builder Pattern

## 简介

- 建造者模式又叫生成器模式，是一种对象构建模式。它可以将复杂对象的建造过程抽象出来（抽象类别），使这个抽象过程的不同实现方法可以构造出不同表现（属性）的对象。
- 建造者模式是一步一步创建一个复杂的对象，它允许用户只通过指定复杂对象的类型和内容就可以构建它们，用户不需要知道内部的具体构建细节。

> UML类图与角色

![](https://cdn.maxqiu.com/upload/3f692d674ca64526b866dafd73bc6851.jpg)

1. Product（产品角色）：一个具体的产品对象。
2. Builder（抽象建造者）：创建一个`Product`对象的各个部件指定的`接口/抽象类`。
3. ConcreteBuilder（具体建造者）：实现接口，构建和装配各个部件。
4. Director（指挥者）：构建一个使用`Builder`接口的对象，用于创建一个复杂的对象。它主要有两个作用：一是隔离了客户与对象的生产过程；二是负责控制产品对象的生产过程。

## 示例

> UML类图与角色

![](https://cdn.maxqiu.com/upload/a3183d4c4cff4ade99c141f5cb06bc2d.jpg)

1. Product（产品角色）：`House`
2. Builder（抽象建造者）：`HouseBuilder`
3. ConcreteBuilder（具体建造者）：`VillaBuilder`、`HighBuildingBuilder`
4. Director（指挥者）：`HouseDirector`

> 代码实现

```java
/**
 * 房子
 */
@Getter
@Setter
@ToString
public class House {
    private Integer basic;
    private Integer wall;
    private String roofed;
}
```

```java
/**
 * 抽象房子建造者
 */
public abstract class HouseBuilder {
    public House house = new House();
    /**
     * 建造细节抽象方法
     */
    public abstract void buildBasic();
    public abstract void buildWalls();
    public abstract void roofed();
    /**
     * 房子建造好， 将产品（房子）返回
     */
    public House buildHouse() {
        return house;
    }
}
```

```java
/**
 * 别墅建造者
 */
public class VillaBuilder extends HouseBuilder {
    @Override
    public void buildBasic() {
        System.out.println(" 别墅打地基10米 ");
        this.house.setBasic(10);
    }
    @Override
    public void buildWalls() {
        System.out.println(" 别墅砌墙10cm ");
        this.house.setWall(10);
    }
    @Override
    public void roofed() {
        System.out.println(" 别墅三角屋顶 ");
        this.house.setRoofed("三角屋顶");
    }
}
```

```java
/**
 * 高楼建造者
 *
 * @author Max_Qiu
 */
public class HighBuildingBuilder extends HouseBuilder {
    @Override
    public void buildBasic() {
        System.out.println(" 高楼打地基100米 ");
        this.house.setBasic(100);
    }
    @Override
    public void buildWalls() {
        System.out.println(" 高楼砌墙20cm ");
        this.house.setWall(20);
    }
    @Override
    public void roofed() {
        System.out.println(" 高楼透明屋顶 ");
        this.house.setRoofed("透明屋顶");
    }
}
```

```java
/**
 * 建造者指挥官
 */
public class HouseDirector {
    HouseBuilder houseBuilder;
    /**
     * 方式1：构造器传入 HouseBuilder
     */
    public HouseDirector(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }
    /**
     * 方式2：通过 setter 传入 HouseBuilder
     */
    public void setHouseBuilder(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }
    /**
     * 如何处理建造房子的流程，交给指挥者
     *
     * @return House
     */
    public House constructHouse() {
        houseBuilder.buildBasic();
        houseBuilder.buildWalls();
        houseBuilder.roofed();
        return houseBuilder.buildHouse();
    }
}
```

```java
public static void main(String[] args) {
    // 盖别墅
    // 准备建造者
    VillaBuilder villaBuilder = new VillaBuilder();
    // 准备创建房子的指挥者
    HouseDirector houseDirector = new HouseDirector(villaBuilder);
    // 完成盖房子，返回产品（别墅）
    House villa = houseDirector.constructHouse();
    System.out.println(villa);
    System.out.println("--------------------------");
    // 盖高楼
    HighBuildingBuilder highBuildingBuilder = new HighBuildingBuilder();
    // setter方式传入建造者
    houseDirector.setHouseBuilder(highBuildingBuilder);
    // 完成盖房子，返回产品（高楼）
    House highBuilding = houseDirector.constructHouse();
    System.out.println(highBuilding);
}
```

# 总结

1. 客户端（使用程序）不必知道产品内部组成的细节，将产品本身与产品的创建过程解耦，使得相同的创建过程可以创建不同的产品对象
2. 每一个具体建造者都相对独立，而与其他的具体建造者无关，因此可以很方便地替换具体建造者或增加新的具体建造者，用户使用不同的具体建造者即可得到不同的产品对象
3. 更加精细地控制产品的创建过程。将复杂产品的创建步骤分解在不同的方法中，使得创建过程更加清晰，更方便使用程序来控制创建过程
4. 增加新的具体建造者无须修改原有类库的代码，指挥者类针对抽象建造者类编程，系统扩展方便，符合`开闭原则`
5. 建造者模式所创建的产品一般具有较多的共同点，其组成部分相似，如果产品之间的差异性很大，则不适合使用建造者模式，因此其使用范围受到一定的限制。
6. 如果产品的内部变化复杂，可能会导致需要定义很多具体建造者类来实现这种变化，导致系统变得很庞大，因此在这种情况下，要考虑是否选择建造者模式。

> 抽象工厂模式 VS 建造者模式

- 抽象工厂模式实现对产品家族的创建，一个产品家族是这样的一系列产品：具有不同分类维度的产品组合，采用抽象工厂模式不需要关心构建过程，只关心什么产品由什么工厂生产即可。
- 建造者模式则是要求按照指定的蓝图建造产品，它的主要目的是通过组装零配件而产生一个新产品
