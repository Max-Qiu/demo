# 组合模式 Composite Pattern

> 介绍

又叫**部分整体模式**，它创建了对象组的**树形结构**，将对象组合成树状结构以表示“整体-部分”的层次关系。组合模式使得用户对单个对象和组合对象的访问具有一致性，即：组合能让客户以一致的方式处理个别对象以及组合对象

> UML类图与角色分析

![](https://cdn.maxqiu.com/upload/8933d7c14b8943848b16de31e240280d.jpg)

1. `Component`:这是组合中对象声明抽象类，用于定义公共字段或实现共有的方法，`Component`可以是抽象类或者接口
2. `Leaf`:在组合中表示叶子节点，叶子节点没有子节点
3. `Composite`:非叶子节点，用于存储子部件，在`Component`抽象类中实现子部件的相关操作，比如增加、删除等。

# 示例

> 情景介绍

公司的人力资源架构包含部门和人员。人员有名称和薪资，部门有名称和该部门全员薪资，要求打印部门的薪资

> UML 类图

![](https://cdn.maxqiu.com/upload/6c518307c9404006819ffdbf8a153775.jpg)

> 示例代码

```java
/**
 * 人力资源抽象类
 */
public abstract class HumanResource {
    /**
     * 名称
     */
    protected String name;
    /**
     * 薪资
     */
    protected double salary;
    public HumanResource(String name) {
        this.name = name;
    }
    /**
     * 获取名称
     */
    public String getName() {
        return name;
    }
    /**
     * 计算工资
     */
    public abstract double calculateSalary();
}
```

```java
/**
 * 员工
 */
public class Employee extends HumanResource {
    public Employee(String id, double salary) {
        super(id);
        this.salary = salary;
    }
    @Override
    public double calculateSalary() {
        return salary;
    }
}
```

```java
/**
 * 部门
 */
public class Department extends HumanResource {
    /**
     * 子人力资源
     */
    protected List<HumanResource> childNodes = new ArrayList<>();
    public Department(String id) {
        super(id);
    }
    @Override
    public double calculateSalary() {
        double totalSalary = 0;
        for (HumanResource resource : childNodes) {
            totalSalary += resource.calculateSalary();
        }
        this.salary = totalSalary;
        return totalSalary;
    }
    /**
     * 添加子人力资源
     */
    public void addSubNode(HumanResource hr) {
        childNodes.add(hr);
    }
}
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        // @formatter:off
        // xxx公司
        // |--设计部
        //      |--员工1
        //      |--员工2
        // |--开发部
        //      |--开发1组
        //           |--员工3
        //           |--员工4
        //      |--开发2组
        //           |--员工5
        //           |--员工6
        // @formatter:on

        // xxx公司
        Department root = new Department("xxx公司");
        // 设计部
        Department designDepartment = new Department("设计部");
        root.addSubNode(designDepartment);
        // 员工 1 2
        Employee e1 = new Employee("员工1", 8000);
        designDepartment.addSubNode(e1);
        Employee e2 = new Employee("员工2", 5000);
        designDepartment.addSubNode(e2);
        // 开发部
        Department developmentDepartment = new Department("开发部");
        root.addSubNode(developmentDepartment);
        // 开发1组
        Department group1 = new Department("开发1组");
        developmentDepartment.addSubNode(group1);
        // 员工 3 4
        Employee e3 = new Employee("员工3", 7000);
        group1.addSubNode(e3);
        Employee e4 = new Employee("员工4", 4000);
        group1.addSubNode(e4);
        // 部门 2-2
        Department group2 = new Department("开发2组");
        developmentDepartment.addSubNode(group2);
        // 员工 5 6
        Employee e5 = new Employee("员工5", 7500);
        group2.addSubNode(e5);
        Employee e6 = new Employee("员工6", 3000);
        group2.addSubNode(e6);
        // 获取 全公司 的薪资
        System.out.println(root.getName() + "\t" + root.calculateSalary());
        // 获取 开发部 的薪资
        System.out.println(developmentDepartment.getName() + "\t" + developmentDepartment.calculateSalary());
    }
}
```

输出结果如下：

    xxx公司	34500.0
    开发部	21500.0

# 注意事项

- 需要遍历**组织结构**，或者处理的对象具有**树形结构**时, 非常适合使用**组合模式**
- 要求较高的抽象性，如果节点和叶子有很多差异性的话，比如很多方法和属性都不一样，不适合使用组合模式
