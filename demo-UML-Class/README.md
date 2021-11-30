> PS：本文档为个人整理，学艺不精，如有错误，请留言指出。部分内容整理自视频教程：[尚硅谷_图解Java设计模式](http://www.atguigu.com/download_detail.shtml?v=202)

# 安装UML插件

- idea安装PlantUML插件，idea插件市场：[PlantUML integration](https://plugins.jetbrains.com/plugin/7017-plantuml-integration)
- eclipse安装PlantUML插件，官方教程：[Integration with Eclipse](https://plantuml.com/zh/eclipse)

# 基础语法

PlantUML语法官方文档：[类图的语法和功能](https://plantuml.com/zh/class-diagram)

## 定义类/接口/抽象类/枚举/属性/方法

```java
public class Person {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

public interface UserService {
    boolean save(Object entity);
}

public abstract class BaseController {
    public abstract void absMethod();

    public void normalMethod() {

    }
}

public enum TimeUnit {
    DAYS, HOURS, MINUTES
}
```

```uml
@startuml
class Person {
    - id : Integer
    + getId()
    + setId(id:Integer)
}

Person2 : - id
Person2 : + getId()
Person2 : + setId(id:Integer)

interface UserService {
    {abstract} + save()
}

abstract BaseController{
    {abstract} + absMethod()
    + normalMethod()
}

enum TimeUnit {
DAYS
HOURS
MINUTES
}
@enduml
```

> 创建类、接口等建议使用`{}`形式

![](https://cdn.maxqiu.com/upload/48284fad769c4ac1b7109772e3b6c5bc.jpg)

## 访问控制符

| 符号 | Java关键字 | 图形 |
| ---- | ---------- | ---- |
| -    | private    | □    |
| #    | protected  | ◇    |
| ~    |            | △    |
| +    | public     | ○    |

> 属性为**空心**，方法为**实心**

```java
public class Visibility {
    private String field1;
    protected String field2;
    String field3;
    public String field4;

    private void method1() {}

    protected void method2() {}

    void method3() {}

    public void method4() {}
}
```

```uml
@startuml
class Visibility {
    - field1
    # field2
    ~ field3
    + field4
    - method1()
    # method2()
    ~ method3()
    + method4()
}
@enduml
```

![](https://cdn.maxqiu.com/upload/9f7efdd17ebd4f46906e5dcb73b52210.jpg)

# 关系语法

```uml
Class01 ..>  Class02  依赖  Dependency
Class03 --|> Class04  泛化  Generalization
Class05 ..|> Class06  实现  Implementation
Class07 -->  Class08  关联  Association
Class09 --o  Class10  聚合  Aggregation
Class11 --*  Class12  组合  Composition
```

![](https://cdn.maxqiu.com/upload/0686d6fc4c99400d9b14ae4de343cedb.jpg)

## 依赖 Dependency

只要是在类中用到了对方，那么他们之间就存在依赖关系。如果没有对方，连编绎都通过不了

1. 类中用到了对方
2. 类的成员属性
3. 方法的返回类型
4. 方法接收的参数类型

```java
public class PersonServiceBean {
    /**
     * 类中使用到了对方
     */
    public void modify() {
        Department department = new Department();
    }

    /**
     * 类的成员属性
     */
    private PersonDao personDao;

    /**
     * 方法的返回类型
     */
    public IdCard getIdCard(Integer personId) {
        return null;
    }

    /**
     * 方法接收的参数类型
     */
    public void save(Person person) {}
}

public class Department {}

public class PersonDao {}

public class IdCard {}

public class Person {}
```

```uml
@startuml
class Department {
}
class IdCard {
}
class Person {
}
class PersonDao {
}
class PersonServiceBean {
    + getIdCard()
    + modify()
    + save()
}
PersonServiceBean ..> PersonDao
PersonServiceBean ..> Person
PersonServiceBean ..> Department
PersonServiceBean ..> IdCard
@enduml
```

![](https://cdn.maxqiu.com/upload/a4194908919445dcbcd2ffef939a840e.jpg)

## 泛化 Generalization

实际上就是继承关系，他是依赖关系的特例，如果 A 类继承了 B 类，我们就说 A 和 B 存在泛化关系

```java
public class DaoSupport {
    public void save(Object entity) {}

    public void delete(Object id) {}
}

public class DepartmentServiceBean extends DaoSupport {}

public class PersonServiceBean extends DaoSupport {}
```

```uml
@startuml
class DaoSupport {
    + delete(id:Object)
    + save(entity:Object)
}
class PersonServiceBean {
}
class DepartmentServiceBean {
}
DaoSupport <|-- PersonServiceBean
DaoSupport <|-- DepartmentServiceBean
@enduml
```

> PS：这里关系语法可以反着写，反着写的效果就是生成的图片方向翻一下

> PS：注意！箭头指向父类

![](https://cdn.maxqiu.com/upload/8063d6159c4647f4886a0711e444830a.jpg)

## 实现 Implementation

实际上就是 A 类实现 B 接口，它是依赖关系的特例

```java
public interface PersonService {
    void delete(Integer id);
}

public class PersonServiceImpl implements PersonService {
    @Override
    public void delete(Integer id) {

    }
}
```

```uml
@startuml
interface PersonService {
    {abstract} + delete()
}
class PersonServiceImpl {
    + delete()
}
PersonServiceImpl .up.|> PersonService
@enduml
```

> 单破折号（或者点）之间加上方向单词也可以改变图形的方向，同样的单词有`left`、`right`、`up`、`down`

> PS：注意！箭头指向接口

![](https://cdn.maxqiu.com/upload/8f875bf761d3422eb93718d006245872.jpg)

## 关联 Association

实际上就是类与类之间的联系，他是依赖关系的特例

1. 关联具有导航性（即双向关系或单向关系）
2. 关联具有多重性：
    1. 1 表示有且仅有一个
    2. \* 表示有0个或多个
    3. m..n 表示m到n个(m≤n)

> PS：多重性表达式可能不太正确，大概是这个意思

### 单向

```java
public class Person {
    private IdCard idCard;

    private List<Address> addressList;
}

public class IdCard {}

public class Address {}
```

```uml
@startuml
class Person {
}
class IdCard {
}
class Address {
}
Person "1" --> "1" IdCard
Person "1" --> "*" Address
@enduml
```

![](https://cdn.maxqiu.com/upload/3e644b2d3ccc47af87ed2a7d92cc61d0.jpg)

### 双向

```java
public class Person {
    private IdCard idCard;
}

public class IdCard {
    private Person person;
}
```

```uml
@startuml
class IdCard {
}
class Person {
}
IdCard "1" <--> "1" Person
@enduml
```

> 单破折号（或者点）俩侧都可以写关系符号

![](https://cdn.maxqiu.com/upload/d7136b4188b142ebbcb42389b785347e.jpg)

## 聚合 Aggregation

表示的是**整体和部分**的关系，整体与部分**可以分开**。聚合关系是关联关系的特例，所以他具有关联的导航性与多重性。

> 比如：电脑包含鼠标和显示器，但是没有鼠标和显示器也可以

```java
public class Computer {
    private Monitor monitor;
    private Mouse mouse;

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }
}

public class Monitor {}

public class Mouse {}
```

```uml
@startuml
class Computer {
    - mouse : Mouse
    - monitor : Monitor
    + setMonitor(mouse:Mouse)
    + setMouse(monitor:Monitor)
}
class Monitor {
}
class Mouse {
}
Computer o-- Monitor
Computer o-- Mouse
@enduml
```

![](https://cdn.maxqiu.com/upload/a516ee1ad5284757af4730044e1f0b1b.jpg)

## 组合 Composition

也是**整体与部分**的关系，但是整体与部分**不可分开**。

> 比如：电脑必须包含处理器，没有处理器就没有用

```java
public class Computer {
    private Cpu cpu = new Cpu();
    private Monitor monitor;
    private Mouse mouse;

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }
}

public class Cpu {}

public class Monitor {}

public class Mouse {}
```

```uml
@startuml
class Computer {
    + setMonitor()
    + setMouse()
}
class Cpu {
}
class Monitor {
}
class Mouse {
}
Computer *-- Cpu
Computer o-- Monitor
Computer o-- Mouse
@enduml
```

![](https://cdn.maxqiu.com/upload/2fc55a03e1cd45c5b679ff4e4ade6088.jpg)
