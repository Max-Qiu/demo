package com.maxqiu.demo.P15_Iterator;

import java.util.Iterator;

/**
 * 计算机学院迭代器
 * 
 * @author Max_Qiu
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
