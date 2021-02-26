package com.maxqiu.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.maxqiu.demo.entity.Employee;

/**
 * 2. 中间操作：排序
 * 
 * @author Max_Qiu
 */
public class MiddleOperationOfShortTest {

    List<Employee> employeeList;
    {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(102, "李四", 59, 6666.66));
        employeeList.add(new Employee(101, "张三", 18, 9999.99));
        employeeList.add(new Employee(103, "王五", 18, 3333.33));
        employeeList.add(new Employee(104, "赵六", 8, 7777.77));
        employeeList.add(new Employee(105, "田七", 38, 5555.55));
        employeeList.add(new Employee(105, "田七", 38, 5555.55));
    }

    /**
     * sorted()：自然排序
     * 
     * sorted(Comparator com)：定制排序
     */
    @Test
    public void test() {
        // 取出年龄，并自如排序，然后打印
        employeeList.stream().map(Employee::getAge).sorted().forEach(System.out::println);

        System.out.println("------------------------------------");

        // 按照年龄排序，如果年龄相同，则按工资排序
        employeeList.stream().sorted((x, y) -> {
            if (x.getAge().intValue() == y.getAge().intValue()) {
                return Double.compare(x.getSalary(), y.getSalary());
            } else {
                return Integer.compare(x.getAge(), y.getAge());
            }
        }).forEach(System.out::println);
    }

}
