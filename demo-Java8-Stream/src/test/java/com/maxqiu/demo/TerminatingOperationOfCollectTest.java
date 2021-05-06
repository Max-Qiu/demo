package com.maxqiu.demo;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.maxqiu.demo.entity.Employee;
import com.maxqiu.demo.entity.Status;

/**
 * 3. 终止操作：收集
 * 
 * collect：将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
 * 
 * @author Max_Qiu
 */
public class TerminatingOperationOfCollectTest {
    List<Employee> employeeList;
    {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(102, "李四", 59, 6666.66, Status.BUSY));
        employeeList.add(new Employee(101, "张三", 18, 9999.99, Status.FREE));
        employeeList.add(new Employee(103, "王五", 28, 3333.33, Status.VOCATION));
        employeeList.add(new Employee(104, "赵六", 8, 7777.77, Status.BUSY));
        employeeList.add(new Employee(105, "田七", 38, 5555.55, Status.BUSY));
    }

    /**
     * 转换
     */
    @Test
    public void test1() {
        // 收集到List
        List<String> list = employeeList.stream().map(Employee::getName).collect(Collectors.toList());
        list.forEach(System.out::println);

        System.out.println("----------------------------------");

        // 收集到Set
        Set<String> set = employeeList.stream().map(Employee::getName).collect(Collectors.toSet());
        set.forEach(System.out::println);

        System.out.println("----------------------------------");

        // 收集到自定义的Collection
        HashSet<String> hs =
            employeeList.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
        hs.forEach(System.out::println);
    }

    /**
     * 统计
     */
    @Test
    public void test2() {
        // 求工资的平均值
        Double avg = employeeList.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);

        System.out.println("--------------------------------------------");

        // 求工资的多种统计值
        DoubleSummaryStatistics dss = employeeList.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(dss.getMax());
        System.out.println(dss.getCount());
    }

    /**
     * 分组
     */
    @Test
    public void test3() {
        Map<Status, List<Employee>> map = employeeList.stream().collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(map);
    }

    /**
     * 多级分组
     */
    @Test
    public void test4() {
        Map<Status, Map<String, List<Employee>>> map =
            employeeList.stream().collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
                if (e.getAge() >= 60) {
                    return "老年";
                } else if (e.getAge() >= 35) {
                    return "中年";
                } else {
                    return "成年";
                }
            })));
        System.out.println(map);
    }

    /**
     * 分区
     */
    @Test
    public void test5() {
        Map<Boolean, List<Employee>> map =
            employeeList.stream().collect(Collectors.partitioningBy((e) -> e.getSalary() >= 5000));
        System.out.println(map);
    }

    /**
     * 拼接
     */
    @Test
    public void test6() {
        String str = employeeList.stream().map(Employee::getName).collect(Collectors.joining(",", "----", "----"));
        System.out.println(str);
    }
}
