package com.maxqiu.demo.P08_Composite;

/**
 * 员工
 * 
 * @author Max_Qiu
 */
public class Employee extends HumanResource {

    public Employee(String id, double salary) {
        super(id);
        this.salary = salary;
    }

    @Override
    public double calculateSalary() {
        return salary;
    }
}
