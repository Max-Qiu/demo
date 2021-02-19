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

> 内部状态和外部状态

比如围棋、五子棋、跳棋，它们都有大量的棋子对象，围棋和五子棋只有黑白两色，跳棋颜色多一点，所以棋子颜
色就是棋子的内部状态；而各个棋子之间的差别就是位置的不同，当我们落子后，落子颜色是定的，但位置是变化
的，所以棋子坐标就是棋子的外部状态

享元模式提出了两个要求：细粒度和共享对象。这里就涉及到内部状态和外部状态，即将对象的信息分为两个部分：内部状态和外部状态

- 内部状态：指对象共享出来的信息，存储在享元对象内部且不会随环境的改变而改变
- 外部状态：指对象得以依赖的一个标记，是随环境改变而改变的、不可共享的状态。

举个例子：围棋理论上有361个空位可以放棋子，每盘棋都有可能有两三百个棋子对象产生，因为内存空间有限，一台服务器很难支持更多的玩家玩围棋游戏，如果用享元模式来处理棋子，那么棋子对象就可以减少到只
有两个实例，这样就很好的解决了对象的开销问题

# 示例

## 情景介绍

小型的外包项目，给客户A做一个产品展示网站，客户A的朋友感觉效果不错，也希望做这样的产品展示网站，但是要求都有些不同：

- 有客户要求以新闻的形式发布
- 有客户要求以博客的形式发布
- 有客户要求以。。。。。。

## 传统方式

直接复制一份，根据需求进行定制修改

## 改进方式

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

# 总结

1. 在享元模式里，“享”就表示共享，“元”表示对象
2. 系统中有大量对象，这些对象消耗大量内存，并且对象的状态大部分可以外部化时，我们就可以考虑选用享元
模式
3. 用唯一标识码判断，如果在内存中有，则返回这个唯一标识码所标识的对象，用`HashMap/HashTable`存储
4. 享元模式大大减少了对象的创建，降低了程序内存的占用，提高效率
5. 享元模式提高了系统的复杂度。需要分离出内部状态和外部状态，而外部状态具有固化特性，不应该随着内部状态的改变而改变。
6. 使用享元模式时，注意划分内部状态和外部状态，并且需要有一个工厂类加以控制。
7. 享元模式经典的应用场景是需要缓冲池的场景，比如String常量池、数据库连接池
