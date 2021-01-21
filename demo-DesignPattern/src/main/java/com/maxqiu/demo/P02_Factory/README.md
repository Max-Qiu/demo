尚硅谷的工厂模式讲的有点复杂，所以本文代码与推荐阅读的代码类似

> 推荐阅读：
1. [漫画：设计模式之 “工厂模式”](https://mp.weixin.qq.com/s/T3h6479P5kMVtKfXiLcc-Q)
2. [漫画：什么是 “抽象工厂模式” ？](https://mp.weixin.qq.com/s/K_E9pI5rnkjHU0eizg9lqg)

# 情景介绍

一家披萨商店，售卖不同口味的披萨

> 实体：

```java
/**
 * 披萨 接口
 */
public interface IPizza {
    /**
     * 每种披萨实现自己的制作方式
     */
    void make();
}
```

```java
/**
 * 奶酪披萨
 */
public class CheesePizza implements IPizza {
    @Override
    public void make() {
        System.out.println("制作奶酪披萨");
    }
}
```

```java
/**
 * 小龙虾披萨
 */
public class LobsterPizza implements IPizza {
    @Override
    public void make() {
        System.out.println("制作小龙虾披萨");
    }
}
```

# 工厂模式 Factory Pattern

## 简单工厂模式与静态工厂模式

### 简单工厂模式 Simple Factory Pattern

披萨的种类有很多，商店找了一家工厂进行生产

```java
/**
 * 简单工厂模式
 */
public class SimplePizzaFactory {
    public IPizza createPizza(String orderType) {
        IPizza pizza;
        if ("lobster".equals(orderType)) {
            pizza = new LobsterPizza();
        } else if ("cheese".equals(orderType)) {
            pizza = new CheesePizza();
        } else {
            System.out.println("生产披萨失败");
            return null;
        }
        pizza.make();
        return pizza;
    }
}
```

```java
public static void main(String[] args) {
    System.out.println("使用简单工厂模式");
    SimplePizzaFactory simplePizzaFactory = new SimplePizzaFactory();
    simplePizzaFactory.createPizza("lobster");
    simplePizzaFactory.createPizza("cheese");
    simplePizzaFactory.createPizza("other");
}
```

### 静态工厂模式

> 即：简单工厂的方法修改为静态方法

```java
/**
 * 静态工厂模式
 */
public class StaticPizzaFactory {
    public static IPizza createPizza(String orderType) {
        IPizza pizza;
        if ("lobster".equals(orderType)) {
            pizza = new LobsterPizza();
        } else if ("cheese".equals(orderType)) {
            pizza = new CheesePizza();
        } else {
            System.out.println("生产披萨失败");
            return null;
        }
        pizza.make();
        return pizza;
    }
}
```

```java
public static void main(String[] args) {
    System.out.println("使用静态工厂模式");
    StaticPizzaFactory.createPizza("lobster");
    StaticPizzaFactory.createPizza("cheese");
    StaticPizzaFactory.createPizza("other");
}
```

### 问题分析

- 优点：容易理解
- 缺点：违反了设计模式的[OCP原则](https://maxqiu.com/article/detail/58#%E5%BC%80%E9%97%AD%E5%8E%9F%E5%88%99%20Open%20Closed%20Principle)

改进方案：

> 使用工厂方法模式

## 工厂方法模式 Factory Method Pattern

商店找到了对应的工厂生产对应的披萨

```java
/**
 * 披萨工厂接口
 */
public interface IPizzaFactory {
    IPizza createPizza();
}
```

```java
/**
 * 奶酪披萨工厂
 */
public class CheesePizzaFactory implements IPizzaFactory {
    @Override
    public IPizza createPizza() {
        IPizza pizza = new CheesePizza();
        pizza.make();
        return pizza;
    }
}
```

```java
/**
 * 小龙虾披萨工厂
 */
public class LobsterPizzaFactory implements IPizzaFactory
    @Override
    public IPizza createPizza() {
        IPizza pizza = new LobsterPizza();
        pizza.make();
        return pizza;
    }
}
```

```java
public static void main(String[] args) {
    System.out.println("使用不同的工厂生产对应的披萨");
    IPizzaFactory cheesePizzaFactory = new CheesePizzaFactory();
    cheesePizzaFactory.createPizza();
    IPizzaFactory greekPizzaFactory = new LobsterPizzaFactory();
    greekPizzaFactory.createPizza();
}
```

## 抽象工厂模式 Abstract Factory Pattern

商店做大了，又需要卖汉堡包

> 实体：

```java
/**
 * 汉堡包 接口
 */
public interface IHamburger {
    /**
     * 每种汉堡包实现自己的制作方式
     */
    void make();
}
```

```java
/**
 * 奶酪汉堡包
 */
public class CheeseHamburger implements IHamburger {
    @Override
    public void make() {
        System.out.println("制作奶酪汉堡包");
    }
}
```

```java
/**
 * 小龙虾汉堡包
 */
public class LobsterHamburger implements IHamburger {
    @Override
    public void make() {
        System.out.println("制作小龙虾汉堡包");
    }
}
```

于是商店找了高级的工厂

```java
/**
 * 工厂接口，可以生产披萨、汉堡包
 */
public interface IFactory {
    IPizza createPizza();
    IHamburger createHamburger();
}
```

```java
/**
 * 奶酪工厂：生产奶酪披萨，奶酪汉堡包
 */
public class CheeseFactory implements IFactory {
    @Override
    public IPizza createPizza() {
        IPizza pizza = new CheesePizza();
        pizza.make();
        return pizza;
    }
    @Override
    public IHamburger createHamburger() {
        IHamburger hamburger = new CheeseHamburger();
        hamburger.make();
        return hamburger;
    }
}
```

```java
/**
 * 小龙虾披萨：生产小龙虾披萨，小龙虾汉堡包
 */
public class LobsterFactory implements IFactory {
    @Override
    public IPizza createPizza() {
        IPizza pizza = new LobsterPizza();
        pizza.make();
        return pizza;
    }
    @Override
    public IHamburger createHamburger() {
        IHamburger hamburger = new LobsterHamburger();
        hamburger.make();
        return hamburger;
    }
}
```

```java
public static void main(String[] args) {
    System.out.println("使用不同的工厂生产对应的商品");
    IFactory cheeseFactory = new CheeseFactory();
    IPizza cheesePizza = cheeseFactory.createPizza();
    IHamburger cheeseHamburger = cheeseFactory.createHamburger();
    IFactory lobsterFactory = new LobsterFactory();
    IPizza lobsterPizza = lobsterFactory.createPizza();
    IHamburger lobsterHamburger = lobsterFactory.createHamburger();
}
```
