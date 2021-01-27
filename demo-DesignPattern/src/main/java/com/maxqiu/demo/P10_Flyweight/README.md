# 享元模式 Flyweight Pattern

> 基本介绍

- 运用共享技术有效地支持大量细粒度的对象
- 常用于系统底层开发，解决系统的性能问题。像数据库连接池，里面都是创建好的连接对象，在这些连接对象中有我们需要的则直接拿来用，避免重新创建，如果没有我们需要的，则创建一个
- 享元模式能够解决重复对象的内存浪费的问题，当系统中有大量相似对象，需要缓冲池时。不需总是创建新对象，可以从缓冲池里拿。这样可以降低系统内存，同时提高效率
- 享元模式经典的应用场景就是池技术了，String常量池、数据库连接池、缓冲池等等都是享元模式的应用，享元模式是池技术的重要实现方式

> UML类图与角色

![](https://cdn.maxqiu.com/upload/c5c1f4ec408c4c88917ec46b5e9e65d3.jpg)

- FlyWeight：是抽象的享元角色，他是产品的抽象类，同时定义出对象的外部状态和内部状态的接口或实现
  - 享元模式提出了两个要求：细粒度和共享对象。这里就涉及到内部状态和外部状态，即将对象的信息分为两个部分：内部状态和外部状态
  - 内部状态：指对象共享出来的信息，存储在享元对象内部且不会随环境的改变而改变
  - 外部状态：指对象得以依赖的一个标记，是随环境改变而改变的、不可共享的状态。
- ConcreteFlyWeight：是具体的享元角色，是具体的产品类，实现抽象角色定义相关业务
- UnSharedConcreteFlyWeight：是不可共享的角色，一般不会出现在享元工厂。
- FlyWeightFactory：享元工厂类，用于构建一个池容器(集合)，同时提供从池中获取对象方法

# 示例

## 情景介绍

小型的外包项目，给客户A做一个产品展示网站，客户A的朋友感觉效果不错，也希望做这样的产品展示网站，但是要求都有些不同：

- 有客户要求以新闻的形式发布
- 有客户要求以博客的形式发布
- 有客户要求以。。。。。。

## 传统方式

直接复制一份，根据需求进行定制修改

## 享元模式

> UML类图

![](https://cdn.maxqiu.com/upload/73dba8ba14f44dcca513f66cf2fe781c.jpg)

> 代码实现

```java
/**
 * 网站
 */
public abstract class WebSite {
    /**
     * 抽象方法
     */
    public abstract void use(User user);
}
```

```java
/**
 * 具体网站
 */
public class ConcreteWebSite extends WebSite {
    /**
     * 网站类型（共享的部分，内部状态）
     */
    private String type;
    @Override
    public void use(User user) {
        System.out.println("网站形式为：" + type + " 在使用中。。。使用者是" + user.getName());
    }
    public ConcreteWebSite(String type) {
        this.type = type;
    }
}
```

```java
/**
 * 网站工厂类，根据需要返回压一个网站
 */
public class WebSiteFactory {
    /**
     * 集合， 充当池的作用
     */
    private HashMap<String, ConcreteWebSite> pool = new HashMap<>();
    /**
     * 根据网站的类型，返回一个网站, 如果没有就创建一个网站，并放入到池中，然后返回
     */
    public WebSite getWebSiteCategory(String type) {
        if (!pool.containsKey(type)) {
            // 创建一个网站，并放入到池中
            pool.put(type, new ConcreteWebSite(type));
        }
        return pool.get(type);
    }
    /**
     * 获取网站分类的总数 (池中有多少个网站类型)
     */
    public int getWebSiteCount() {
        return pool.size();
    }
}
```

```
/**
 * 用户（外部状态）
 */
@Getter
@Setter
@AllArgsConstructor
public class User {
    private String name;
}
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        // 创建一个工厂类
        WebSiteFactory factory = new WebSiteFactory();
        // 客户要一个以新闻形式发布的网站
        WebSite webSite1 = factory.getWebSiteCategory("新闻");
        webSite1.use(new User("tom"));
        // 客户要一个以博客形式发布的网站
        WebSite webSite2 = factory.getWebSiteCategory("博客");
        webSite2.use(new User("jack"));
        // 客户要一个以博客形式发布的网站
        WebSite webSite3 = factory.getWebSiteCategory("博客");
        webSite3.use(new User("smith"));
        // 客户要一个以博客形式发布的网站
        WebSite webSite4 = factory.getWebSiteCategory("博客");
        webSite4.use(new User("king"));
        // 打印共享的元
        System.out.println("网站的分类总数：" + factory.getWebSiteCount());
    }
}
```

输出如下：

    网站形式为：新闻 在使用中。。。使用者是tom
    网站形式为：博客 在使用中。。。使用者是jack
    网站形式为：博客 在使用中。。。使用者是smith
    网站形式为：博客 在使用中。。。使用者是king
    网站的分类总数：2
