package com.maxqiu.demo.principle6_demeter;

/**
 * 学校总部员工类
 * 
 * @author Max_Qiu
 */
public class Employee {
    private String id;

    public Employee setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }
}
