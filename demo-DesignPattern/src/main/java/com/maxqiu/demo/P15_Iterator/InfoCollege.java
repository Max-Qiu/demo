package com.maxqiu.demo.P15_Iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 信息工程学院
 * 
 * @author Max_Qiu
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
