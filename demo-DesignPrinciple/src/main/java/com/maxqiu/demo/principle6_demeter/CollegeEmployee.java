package com.maxqiu.demo.principle6_demeter;

/**
 * 学院的员工类
 * 
 * @author Max_Qiu
 */
public class CollegeEmployee {
    private String id;

    public CollegeEmployee setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }
}
