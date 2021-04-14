package com.maxqiu.demo.dao;

import org.apache.ibatis.annotations.Select;

import com.maxqiu.demo.bean.Employee;

/**
 * @author Max_Qiu
 */
public interface EmployeeMapperAnnotation {

    @Select("select * from tbl_employee where id=#{id}")
    Employee getEmpById(Integer id);
}
