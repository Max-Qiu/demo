package com.maxqiu.demo.P08_Composite;

/**
 * 人力资源抽象类
 * 
 * @author Max_Qiu
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
