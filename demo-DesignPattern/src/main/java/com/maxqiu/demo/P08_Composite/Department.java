package com.maxqiu.demo.P08_Composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门
 *
 * @author Max_Qiu
 */
public class Department extends HumanResource {

    /**
     * 子人力资源
     */
    protected List<HumanResource> childNodes = new ArrayList<>();

    public Department(String id) {
        super(id);
    }

    @Override
    public double calculateSalary() {
        double totalSalary = 0;
        for (HumanResource resource : childNodes) {
            totalSalary += resource.calculateSalary();
        }
        this.salary = totalSalary;
        return totalSalary;
    }

    /**
     * 添加子人力资源
     */
    public void addSubNode(HumanResource hr) {
        childNodes.add(hr);
    }

}
