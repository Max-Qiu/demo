package com.maxqiu.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.maxqiu.demo.bean.Employee;

/**
 * @author Max_Qiu
 */
public interface EmployeeMapperDynamicSql {

    // 携带了哪个字段查询条件就带上这个字段的值
    List<Employee> getEmpListByConditionIf(Employee employee);

    List<Employee> getEmpListByConditionTrim(Employee employee);

    List<Employee> getEmpListByConditionChoose(Employee employee);

    void updateEmp(Employee employee);

    List<Employee> getEmpListByConditionForeach(@Param("ids") List<Integer> ids);

    void addEmpList(@Param("empList") List<Employee> empList);

    List<Employee> getEmpListTestInnerParameter(Employee employee);
}
