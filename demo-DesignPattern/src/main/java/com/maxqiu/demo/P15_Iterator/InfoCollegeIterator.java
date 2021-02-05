package com.maxqiu.demo.P15_Iterator;

import java.util.Iterator;
import java.util.List;

/**
 * 信息工程学院迭代器
 * 
 * @author Max_Qiu
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
