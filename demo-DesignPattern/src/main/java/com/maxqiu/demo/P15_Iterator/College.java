package com.maxqiu.demo.P15_Iterator;

import java.util.Iterator;

/**
 * 学院
 * 
 * @author Max_Qiu
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
