package com.maxqiu.demo.P15_Iterator;

import java.util.Iterator;

/**
 * 计算机学院
 * 
 * @author Max_Qiu
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
