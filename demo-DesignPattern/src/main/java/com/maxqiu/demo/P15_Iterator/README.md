# 迭代器模式 Iterator Pattern

> 基本介绍

如果集合元素是用不同的方式实现的，有的数组，还有java的集合类，或者还有其他方式，当客户端要遍历这些集合元素的时候就要使用多种遍历方式，而且还会暴露元素的内部结构，可以考虑使用迭代器模式解决。迭代器模式提供一种遍历集合元素的统一接口，用一致的方法遍历集合元素，不需要知道集合对象的底层表示，即：不暴露其内部的结构。

> UML类图与角色

![](https://cdn.maxqiu.com/upload/9f739b5737f94d02b93c8b2f5b036cd4.jpg)

- Iterator：迭代器接口，是系统提供，包含 hasNext, next, remove
- ConcreteIterator：具体的迭代器类，管理迭代
- Aggregate：一个统一的聚合接口，将客户端和具体聚合解耦
- ConcreteAggregate：具体的聚合持有对象集合，并提供一个方法，返回一个迭代器，该迭代器可以正确遍历集合
- Client：客户端，通过 Iterator 和 Aggregate 依赖子类

# 示例

> 情景介绍

编写程序展示一个学校院系结构：展示出学校的院系组成，一个学校有多个学院，一个学院有多个系。

> UML类图

![](https://cdn.maxqiu.com/upload/4cee8a38367b4a7caf8a03f4401c4950.jpg)

> 代码实现

`Iterator`使用`java.util.Iterator`

```java
/**
 * 部门
 */
public class Department {
    private String name;
    public Department(String name) {
        super();
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
```

```java
/**
 * 学院
 */
public interface College {
    String getName();
    /**
     * 增加系的方法
     *
     * @param name
     */
    void addDepartment(String name);
    /**
     * 返回一个迭代器,遍历
     *
     * @return
     */
    Iterator<Department> createIterator();
}
```

```java
/**
 * 计算机学院
 */
public class ComputerCollege implements College {
    Department[] departments;
    // 保存当前数组的对象个数
    int numOfDepartment = 0;
    public ComputerCollege() {
        departments = new Department[5];
        addDepartment("Java专业");
        addDepartment("PHP专业");
        addDepartment("大数据专业");
    }
    @Override
    public String getName() {
        return "计算机学院";
    }
    @Override
    public void addDepartment(String name) {
        Department department = new Department(name);
        departments[numOfDepartment] = department;
        numOfDepartment += 1;
    }
    @Override
    public Iterator<Department> createIterator() {
        return new ComputerCollegeIterator(departments);
    }
}
```

```java
/**
 * 计算机学院迭代器
 */
public class ComputerCollegeIterator implements Iterator<Department> {
    // 以数组方式存储
    Department[] departments;
    // 遍历的位置
    int position = 0;
    public ComputerCollegeIterator(Department[] departments) {
        this.departments = departments;
    }
    @Override
    public boolean hasNext() {
        return position < departments.length && departments[position] != null;
    }
    @Override
    public Department next() {
        Department department = departments[position];
        position += 1;
        return department;
    }
}
```

```java
/**
 * 信息工程学院
 */
public class InfoCollege implements College {
    List<Department> departmentList;
    public InfoCollege() {
        departmentList = new ArrayList<>();
        addDepartment("信息安全专业");
        addDepartment("网络安全专业");
        addDepartment("服务器安全专业");
    }
    @Override
    public String getName() {
        return "信息工程学院";
    }
    @Override
    public void addDepartment(String name) {
        Department department = new Department(name);
        departmentList.add(department);
    }
    @Override
    public Iterator<Department> createIterator() {
        return new InfoCollegeIterator(departmentList);
    }
}
```

```java
/**
 * 信息工程学院迭代器
 */
public class InfoCollegeIterator implements Iterator<Department> {
    // 信息工程学院是以List方式存放系
    List<Department> departmentList;
    // 索引
    int index = -1;
    public InfoCollegeIterator(List<Department> departmentList) {
        this.departmentList = departmentList;
    }
    @Override
    public boolean hasNext() {
        if (index >= departmentList.size() - 1) {
            return false;
        } else {
            index += 1;
            return true;
        }
    }
    @Override
    public Department next() {
        return departmentList.get(index);
    }
}
```

# 总结

- 优点
    1. 提供一个统一的方法遍历对象，客户不用再考虑聚合的类型，使用一种方法就可以遍历对象了。
    2. 隐藏了聚合的内部结构，客户端要遍历聚合的时候只能取到迭代器，而不会知道聚合的具体组成。
    3. 提供了一种设计思想，就是一个类应该只有一个引起变化的原因（单一责任原则）。在聚合类中，我们把迭代器分开，就是要把管理对象集合和遍历对象集合的责任分开，这样一来集合改变的话，只影响到聚合对象。而如果遍历方式改变的话，只影响到了迭代器。
    4. 当要展示一组相似对象，或者遍历一组相同对象时使用，适合使用迭代器模式
- 缺点：每个聚合对象都要一个迭代器，会生成多个迭代器不好管理类
