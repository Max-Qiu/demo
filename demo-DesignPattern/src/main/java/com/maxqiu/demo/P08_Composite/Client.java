package com.maxqiu.demo.P08_Composite;

/**
 * 客户端
 * 
 * @author Max_Qiu
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
