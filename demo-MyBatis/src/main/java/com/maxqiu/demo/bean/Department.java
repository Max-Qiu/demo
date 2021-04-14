package com.maxqiu.demo.bean;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Max_Qiu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department implements Serializable {

    private static final long serialVersionUID = -8523417562003931126L;

    private Integer id;

    private String departmentName;

    private List<Employee> employeeList;

    public Department(Integer i) {
        this.id = i;
    }
}
