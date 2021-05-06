package com.maxqiu.demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.maxqiu.demo.entity.Employee;
import com.maxqiu.demo.entity.Status;

/**
 * 3. 终止操作：查找与匹配
 * 
 * @author Max_Qiu
 */
public class TerminatingOperationOfFindAndMatchTest {
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
     * allMatch：检查是否匹配所有元素
     * 
     * anyMatch：检查是否至少匹配一个元素
     * 
     * noneMatch：检查是否没有匹配的元素
     */
    @Test
    public void test1() {
        boolean allMatch = employeeList.stream().allMatch((e) -> e.getStatus().equals(Status.BUSY));
        System.out.println(allMatch);

        boolean anyMatch = employeeList.stream().anyMatch((e) -> e.getStatus().equals(Status.BUSY));
        System.out.println(anyMatch);

        boolean noneMatch = employeeList.stream().noneMatch((e) -> e.getStatus().equals(Status.BUSY));
        System.out.println(noneMatch);
    }

    /**
     * findFirst：返回第一个元素
     * 
     * findAny：返回当前流中的任意元素
     */
    @Test
    public void test2() {
        Optional<Employee> op1 = employeeList.stream().filter((e) -> e.getStatus().equals(Status.BUSY)).findFirst();
        System.out.println(op1.get());

        System.out.println("--------------------------------");

        Optional<Employee> op2 =
            employeeList.parallelStream().filter((e) -> e.getStatus().equals(Status.BUSY)).findAny();
        System.out.println(op2.get());
    }

    /**
     * count：返回流中元素的总个数
     * 
     * max：返回流中最大值
     * 
     * min：返回流中最小值
     */
    @Test
    public void test3() {
        long count = employeeList.stream().filter((e) -> e.getStatus().equals(Status.BUSY)).count();
        System.out.println(count);

        Optional<Double> op = employeeList.stream().map(Employee::getSalary).max(Double::compare);
        System.out.println(op.get());

        Optional<Employee> op2 = employeeList.stream().min(Comparator.comparingDouble(Employee::getSalary));
        System.out.println(op2.get());
    }

    /**
     * 注意：流进行了终止操作后，不能再次使用
     */
    @Test
    public void test4() {
        Stream<Employee> stream = employeeList.stream().filter((e) -> e.getStatus().equals(Status.BUSY));
        System.out.println(stream.count());
        // 此处抛出异常
        Optional<Double> max = stream.map(Employee::getSalary).max(Double::compare);
        System.out.println(max);
    }
}
