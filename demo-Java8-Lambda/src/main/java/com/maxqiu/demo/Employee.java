package com.maxqiu.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 实体
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    private Integer id;
    private String name;
    private Integer age;
    private Double salary;

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String show() {
        return "测试方法引用！";
    }

}
