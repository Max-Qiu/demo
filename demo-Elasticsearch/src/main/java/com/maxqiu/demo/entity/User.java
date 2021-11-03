package com.maxqiu.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 实体
 *
 * @author Max_Qiu
 */
@Getter
@Setter
@NoArgsConstructor
public class User {
    private String name;
    private String sex;
    private Integer age;
}
