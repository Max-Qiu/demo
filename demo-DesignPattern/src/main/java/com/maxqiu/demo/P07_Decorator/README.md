# 装饰者模式 Decorator Pattern

> 介绍

`动态的将新功能附加到对象上。`在对象功能扩展方面，它比继承更有弹性，装饰者模式也体现了开闭原则

> 角色

- 原始类
- 装饰器类

# 情景介绍

一家商店售卖咖啡：

- 咖啡分为咖啡豆和调味料（统称为商品，均包含价格和描述）
  - 咖啡豆分为：意大利浓咖啡`Espresso`($4.0)、脱因咖啡`Decaf`($5.0)、等等等
  - 调味料分为：牛奶`Milk`($1.5)、焦糖`Caramel`(2.5)、等等等
- 咖啡有以下售卖方式
  - 可以单独卖咖啡豆
  - 可以把咖啡豆制作成咖啡，需要加($0.5)的加工费
  - 加工时可以额外加多份的调味料
  - 调味料不单独卖
- 售卖时需要计算价格并打印咖啡豆和调味料

> UML类图

![](https://cdn.maxqiu.com/upload/8ca26394f34046ffa7ec4b700c5aea37.jpg)

PS：`PlantUML`生成的类图不太好看，直接看`IDEA`生成的：

![](https://cdn.maxqiu.com/upload/94d61d5064fe45a1a52affc0074c1bcf.jpg)

> 代码实现

```java
/**
 * 商品
 */
@Getter
@Setter
public abstract class Goods {
    /**
     * 描述
     */
    private String des;
    /**
     * 价格
     */
    private Double price;
}
```

```java
/**
 * 意大利咖啡
 */
public class Espresso extends Goods {
    public Espresso() {
        setDes("意大利咖啡");
        setPrice(4.0);
    }
}
```

```java
/**
 * 脱因咖啡
 */
public class Decaf extends Goods {
    public Decaf() {
        setDes("脱因咖啡");
        setPrice(5.0);
    }
}
```

```java
/**
 * 咖啡
 */
public class Coffee extends Goods {
    private Goods goods;
    /**
     * 传入调料或者咖啡豆
     */
    public Coffee(Goods goods) {
        setDes("打包");
        setPrice(0.5);
        this.goods = goods;
    }
    @Override
    public Double getPrice() {
        // 咖啡的价格 = 咖啡豆 + 打包
        return goods.getPrice() + super.getPrice();
    }
    @Override
    public String getDes() {
        // 咖啡的描述 = 咖啡豆的描述 + 打包
        return goods.getDes() + " " + super.getDes();
    }
}
```

```java
/**
 * 牛奶
 */
public class Milk extends Coffee {
    public Milk(Coffee coffee) {
        super(coffee);
        setDes("牛奶");
        setPrice(1.5);
    }
}
```

```java
/**
 * 焦糖
 */
public class Caramel extends Coffee {
    public Caramel(Coffee coffee) {
        super(coffee);
        setDes("焦糖");
        setPrice(2.0);
    }
}
```

```java
/**
 * 商店
 */
public class Shop {
    public static void main(String[] args) {
        // 只要一份咖啡豆
        Goods espresso = new Espresso();
        System.out.println(espresso.getDes());
        System.out.println(espresso.getPrice());
        // 加工，制作成咖啡
        Coffee coffee1 = new Coffee(espresso);
        System.out.println(coffee1.getDes());
        System.out.println(coffee1.getPrice());
        // 再加一份牛奶
        coffee1 = new Milk(coffee1);
        System.out.println(coffee1.getDes());
        System.out.println(coffee1.getPrice());
        // 再加一份焦糖
        coffee1 = new Caramel(coffee1);
        System.out.println(coffee1.getDes());
        System.out.println(coffee1.getPrice());
        System.out.println("------------------------");
        // 要一份脱因咖啡打包并加两份牛奶
        Coffee coffee2 = new Milk(new Milk(new Coffee(new Decaf())));
        System.out.println(coffee2.getDes());
        System.out.println(coffee2.getPrice());
    }
}
```

运行结果如下

    意大利咖啡
    4.0
    意大利咖啡 打包
    4.5
    意大利咖啡 打包 牛奶
    6.0
    意大利咖啡 打包 牛奶 焦糖
    8.0
    ------------------------
    脱因咖啡 打包 牛奶 牛奶
    8.5

> 类分析

- `Goods`：装饰类和被装饰类的父类
- `Espresso`、`Decaf`：被装饰类（原始类）
- `Coffee`（理解为加工/打包更合理）：装饰类
  - 加工时必须要有东西（可以使原料，也可以是被加工后的商品），所以有一个`Goods`属性且构造方法必须包含该属性
  - 加工也有描述和价格，所以在构造方法中设置描述和价格
  - 加工后的商品计算价格为`被装饰类的价格`+`当前装饰类的价格`，所以需要重新，描述同理
- `Milk`、`Caramel`：装饰类
  - 牛奶、焦糖均属于加工的一种，所以继承`Coffee`，省去了价格和描述的重写
  - 修改构造方法，仅可传入`Coffee`，即仅可装饰被加工后的商品

> 使用方式分析

`Coffee coffee2 = new Milk(new Milk(new Coffee(new Decaf())));`

看图理解：

![](https://cdn.maxqiu.com/upload/afb1a8a085404b27a85fcd2f05ee4c8e.jpg)

关键字：`递归`