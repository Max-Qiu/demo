package com.maxqiu.demo.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Max_Qiu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
// @Alias("emp")
public class Employee implements Serializable {
    private static final long serialVersionUID = 5140004183496949215L;
    private Integer id;
    private String lastName;
    private String email;
    private Integer gender;
    private Department dept;

    public Employee(Integer id, String lastName, String email, Integer gender) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }
}
