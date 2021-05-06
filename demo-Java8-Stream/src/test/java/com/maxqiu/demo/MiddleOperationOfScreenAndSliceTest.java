package com.maxqiu.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.maxqiu.demo.entity.Employee;

/**
 * 2. 中间操作：筛选与切片
 * 
 * @author Max_Qiu
 */
public class MiddleOperationOfScreenAndSliceTest {
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
     * filter：接收Lambda，从流中排除某些元素。
     */
    @Test
    public void test1() {
        // 所有的中间操作不会做任何的处理
        Stream<Employee> stream = employeeList.stream().filter((e) -> {
            System.out.println("中间操作");
            return e.getAge() <= 35;
        });
        // 只有当做终止操作时，所有的中间操作会一次性的全部执行，称为“惰性求值”
        stream.forEach(System.out::println);
    }

    /**
     * limit：截断流，使其元素不超过给定数量。
     */
    @Test
    public void test2() {
        employeeList.stream().limit(3).forEach(System.out::println);
        System.out.println("====================================");
        // filter()和limit()是与的关系
        employeeList.stream().filter((e) -> {
            System.out.println("短路！");
            return e.getSalary() >= 5000;
        }).limit(3).forEach(System.out::println);
    }

    /**
     * skip(n)：跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
     */
    @Test
    public void test3() {
        employeeList.stream().filter((e) -> e.getSalary() >= 5000).skip(2).forEach(System.out::println);
    }

    /**
     * distinct：筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
     */
    @Test
    public void test4() {
        // 注意：备操作的对象要重写 hashCode() 和 equals() 方法
        employeeList.stream().distinct().forEach(System.out::println);
    }
}
