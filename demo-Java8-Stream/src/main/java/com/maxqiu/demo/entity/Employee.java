package com.maxqiu.demo.entity;

import lombok.EqualsAndHashCode;
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
@ToString
@EqualsAndHashCode
public class Employee {

    private Integer id;
    private String name;
    private Integer age;
    private Double salary;
    private Status status;

    public Employee(Integer id, String name, Integer age, Double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public Employee(Integer id, String name, Integer age, Double salary, Status status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.status = status;
    }

}
