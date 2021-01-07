package com.maxqiu.demo.principle6_demeter.demo;

import java.util.ArrayList;
import java.util.List;

import com.maxqiu.demo.principle6_demeter.CollegeEmployee;
import com.maxqiu.demo.principle6_demeter.Employee;

/**
 * @author Max_Qiu
 */
public class Demeter {

    public static void main(String[] args) {
        // 创建了一个 SchoolManager 对象
        SchoolManager schoolManager = new SchoolManager();
        // 输出学院的员工id 和 学校总部的员工信息
        schoolManager.printAllEmployee(new CollegeManager());
    }

}

// 学院管理类
class CollegeManager {
    // 返回学院的所有员工
    public List<CollegeEmployee> getAllEmployee() {
        List<CollegeEmployee> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new CollegeEmployee().setId("学院员工id= " + i));
        }
        return list;
    }
}

// 学校管理类
class SchoolManager {
    // 返回学校总部的员工
    public List<Employee> getAllEmployee() {
        List<Employee> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new Employee().setId("学校总部员工id= " + i));
        }
        return list;
    }

    // 该方法完成输出学校总部和学院员工信息(id)
    public void printAllEmployee(CollegeManager collegeManager) {

        // 分析问题：
        // CollegeEmployee不是SchoolManager的直接朋友，CollegeEmployee是以局部变量方式出现在SchoolManager中，违反了迪米特法则

        // 获取到学院员工
        List<CollegeEmployee> list1 = collegeManager.getAllEmployee();
        System.out.println("------------学院员工------------");
        for (CollegeEmployee e : list1) {
            System.out.println(e.getId());
        }
        // 获取到学校总部员工
        List<Employee> list2 = this.getAllEmployee();
        System.out.println("------------学校总部员工------------");
        for (Employee e : list2) {
            System.out.println(e.getId());
        }
    }
}
