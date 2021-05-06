package com.maxqiu.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.maxqiu.demo.entity.Employee;
import com.maxqiu.demo.entity.Status;

/**
 * 3. 终止操作：规约
 * 
 * @author Max_Qiu
 */
public class TerminatingOperationOfReduceTest {
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
     * reduce(T identity, BinaryOperator) / reduce(BinaryOperator)：可以将流中元素反复结合起来，得到一个值。
     */
    @Test
    public void test1() {
        // 求和
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum = list.stream().reduce(0, Integer::sum);
        System.out.println(sum);

        System.out.println("----------------------------------------");

        // 求总工资
        Optional<Double> op = employeeList.stream().map(Employee::getSalary).reduce(Double::sum);
        System.out.println(op.get());
    }

    /**
     * 需求：搜索名字中 “六” 出现的次数
     */
    @Test
    public void test2() {
        Optional<Integer> sum = employeeList.stream().map(Employee::getName)
            .flatMap(MiddleOperationOfMapTest::filterCharacter).map((ch) -> {
                if (ch.equals('六')) {
                    return 1;
                } else {
                    return 0;
                }
            }).reduce(Integer::sum);
        System.out.println(sum.get());
    }
}
