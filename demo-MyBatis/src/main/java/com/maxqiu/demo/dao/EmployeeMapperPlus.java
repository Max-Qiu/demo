package com.maxqiu.demo.dao;

import java.util.List;

import com.maxqiu.demo.bean.Employee;

/**
 * @author Max_Qiu
 */
public interface EmployeeMapperPlus {
    Employee getEmpById(Integer id);

    Employee getEmpAndDept(Integer id);

    Employee getEmpByIdStep(Integer id);

    List<Employee> getEmpListByDeptId(Integer deptId);
}
