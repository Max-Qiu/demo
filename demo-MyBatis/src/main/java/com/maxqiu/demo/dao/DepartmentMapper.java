package com.maxqiu.demo.dao;

import com.maxqiu.demo.bean.Department;

/**
 * @author Max_Qiu
 */
public interface DepartmentMapper {

    Department getDeptById(Integer id);

    Department getDeptByIdPlus(Integer id);

    Department getDeptByIdStep(Integer id);
}
