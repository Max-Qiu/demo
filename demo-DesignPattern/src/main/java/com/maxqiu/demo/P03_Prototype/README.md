# 情景介绍

有1只羊，克隆出10只羊

# 传统方式

> 实体

```java
/**
 * 羊 实体
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sheep {
    private Integer age;
    private String color;
}
```

这里用Lombok代替get/set/toString/无参/有参。

PS：不要用`@Data`注解，这个注解会重写`hashCode()`方法

```java
public static void main(String[] args) {
    // 普通模式克隆
    Sheep sheep1 = new Sheep(1, "白色");
    Sheep sheep2 = new Sheep(sheep1.getAge(), sheep1.getColor());
    Sheep sheep3 = new Sheep(sheep1.getAge(), sheep1.getColor());
    System.out.println(sheep1 + " " + sheep1.hashCode());
    System.out.println(sheep2 + " " + sheep2.hashCode());
    System.out.println(sheep3 + " " + sheep3.hashCode());
}
```

    Sheep(age=1, color=白色) 1509886383
    Sheep(age=1, color=白色) 1854778591
    Sheep(age=1, color=白色) 2054798982

- 优点：是比较好理解，简单易操作。
- 缺点：在创建新的对象时，总是需要重新获取原始对象的属性，如果创建的对象比较复杂时，效率较低

> 解决方案

Java中`Object`类是所有类的根类，`Object`类提供了一个`clone()`方法，该方法可以将一个Java对象复制一份，但是该类必须要实现接口Cloneable，该接口表示该类能够复制且具有复制的能力即：**原型模式**

# 原型模式 Prototype Pattern

> 简介

用原型实例指定创建对象的种类，并且通过拷贝这些原型，创建新的对象，原型模式是一种创建型设计模式，允许一个对象再创建另外一个可定制的对象，无需知道如何创建的细节

> UML类图与角色

![](https://cdn.maxqiu.com/upload/93230b617c424f20a2df1bbcfea92b3c.jpg)

- `Prototype`: 原型类，声明一个克隆自己的方法
- `ConcretePrototype`: 具体的原型类, 实现一个克隆自己的操作
- `Client`: 让一个原型对象克隆自己，从而创建一个新的对象（属性一样）

## 浅拷贝

> 使用原型模式解决克隆羊问题

实体：

```java
/**
 * 羊 实体
 *
 * 实现 Cloneable 接口，并重写 clone() 方法
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sheep implements Cloneable {
    private Integer age;
    private String color;
    @Override
    public Sheep clone() throws CloneNotSupportedException {
        return (Sheep)super.clone();
    }
}
```

```java
public static void main(String[] args) throws CloneNotSupportedException {
    // 浅拷贝
    Sheep s = new Sheep(1, "白色");
    Sheep c1 = s.clone();
    Sheep c2 = c1.clone();
    System.out.println(s + " " + s.hashCode());
    System.out.println(c1 + " " + c1.hashCode());
    System.out.println(c2 + " " + c2.hashCode());
}
```

输出如下：

    Sheep(age=1, color=白色) 932607259
    Sheep(age=1, color=白色) 1740000325
    Sheep(age=1, color=白色) 1142020464

分析：

- `Object`类相当于原型类，声明了`clone()`方法
- `Cloneable`接口是为了支持`clone()`方法，后面用序列化就不需要实现此接口
- `Sheep`相当于具体的原型类
- `main()`方法相当于`Client`

> 新场景：

这只羊，交了一个朋友，于是有了一个属性`private Sheep friend;`

实体：

```java
/**
 * 羊 实体
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sheep implements Cloneable {
    private String name;
    private Integer age;
    private String color;
    private Sheep friend;
    /**
     * 额外写一个没朋友的羊的构造方法
     */
    public Sheep(String name, Integer age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        // 浅拷贝
        return super.clone();
    }
}
```

使用浅拷贝输出如下

    Sheep(name=Tom, age=1, color=白色, friend=Sheep(name=Jerry, age=2, color=黑色, friend=null)) 932607259 Sheep(name=Jerry, age=2, color=黑色, friend=null) 1740000325
    Sheep(name=Tom, age=1, color=白色, friend=Sheep(name=Jerry, age=2, color=黑色, friend=null)) 1142020464 Sheep(name=Jerry, age=2, color=黑色, friend=null) 1740000325
    Sheep(name=Tom, age=1, color=白色, friend=Sheep(name=Jerry, age=2, color=黑色, friend=null)) 1682092198 Sheep(name=Jerry, age=2, color=黑色, friend=null) 1740000325

问题分析：这只羊对应的朋友并没有被克隆

- 对于数据类型是**基本数据类型**的成员变量，浅拷贝会直接进行值传递，也就是将该属性值复制一份给新的对象。
- 对于数据类型是**引用数据类型**的成员变量，比如说成员变量是某个数组、某个类的对象等，那么浅拷贝会进行引用传递，也就是只是将该成员变量的引用值（内存地址）复制一份给新的对象。因为实际上两个对象的该成员变量都指向同一个实例。在这种情况下，在一个对象中修改该成员变量会影响到另一个对象的该成员变量值
- 前面克隆羊就是浅拷贝，浅拷贝是使用默认的`clone()`方法来实现

解决方案：使用深拷贝

## 深拷贝

> 基本介绍

1. 复制对象的所有基本数据类型的成员变量值
2. 为所有引用数据类型的成员变量申请存储空间，并复制每个引用数据类型成员变量所引用的对象，直到该对象可达的所有对象。也就是说，对象进行深拷贝要对整个对象（包括对象的引用类型）进行拷贝
3. 深拷贝实现方式
    - 重写`clone`方法来实现深拷贝
    - 通过对象序列化实现深拷贝（推荐)

### 重写clone()

```
/**
 * 羊 实体
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sheep implements Cloneable {
    private String name;
    private Integer age;
    private String color;
    private Sheep friend;
    /**
     * 额外写一个没朋友的羊的构造方法
     */
    public Sheep(String name, Integer age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }
    /**
     * 深拷贝方式1：使用 clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // 完成对基本数据类型(属性)、包装类、String的克隆
        Sheep sheep = (Sheep)super.clone();
        // 判断引用类型是否为空
        if (sheep.friend != null) {
            // 对引用类型的属性，进行单独处理
            sheep.friend = (Sheep)friend.clone();
        }
        return sheep;
    }
}
```

### 序列化


```java
/**
 * 羊 实体
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sheep implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Integer age;
    private String color;
    private Sheep friend;
    /**
     * 额外写一个没朋友的羊的构造方法
     */
    public Sheep(String name, Integer age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }
    /**
     * 深拷贝方式2：通过对象的序列化实现 (推荐)
     */
    @Override
    public Sheep clone() {
        // 创建流对象
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            // 序列化
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            // 反序列化
            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            return (Sheep)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭流
            try {
                if (bos != null) {
                    bos.close();
                }
                if (oos != null) {
                    oos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e2) {
                System.out.println(e2.getMessage());
            }
        }
    }
}
```

> 重写后每只克隆羊都有自己的克隆朋友

```java
public static void main(String[] args) {
    Sheep friendSheep = new Sheep("jerry", 2, "黑色");
    Sheep s = new Sheep("tom", 1, "白色", friendSheep);
    Sheep c1 = s.clone();
    Sheep c2 = s.clone();
    System.out.println(s + " " + s.hashCode() + " " + s.getFriend() + " " + s.getFriend().hashCode());
    System.out.println(c1 + " " + c1.hashCode() + " " + c1.getFriend() + " " + c1.getFriend().hashCode());
    System.out.println(c2 + " " + c2.hashCode() + " " + c2.getFriend() + " " + c2.getFriend().hashCode());
}
```

输出如下：

    Sheep(name=Tom, age=1, color=白色, friend=Sheep(name=Jerry, age=2, color=黑色, friend=null)) 1032607259 Sheep(name=Jerry, age=2, color=黑色, friend=null) 1740000325
    Sheep(name=Tom, age=1, color=白色, friend=Sheep(name=Jerry, age=2, color=黑色, friend=null)) 1142020464 Sheep(name=Jerry, age=2, color=黑色, friend=null) 1682092198
    Sheep(name=Tom, age=1, color=白色, friend=Sheep(name=Jerry, age=2, color=黑色, friend=null)) 1626877848 Sheep(name=Jerry, age=2, color=黑色, friend=null) 905544614

# 总结

- 创建新的对象比较复杂时，可以利用原型模式简化对象的创建过程，同时也能够提高效率
- 不用重新初始化对象，而是动态地获得对象运行时的状态
- 如果原始对象发生变化(增加或者减少属性)，其它克隆对象的也会发生相应的变化，无需修改代码
- 在实现深克隆的时候可能需要比较复杂的代码
- 缺点：需要为每一个类配备一个克隆方法，这对全新的类来说不是很难，但对已有的类进行改造时，需要修改其源代码，违背了`ocp原则`。
