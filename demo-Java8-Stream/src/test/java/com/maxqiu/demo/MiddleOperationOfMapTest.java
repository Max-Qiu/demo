package com.maxqiu.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.maxqiu.demo.entity.Employee;

/**
 * 2. 中间操作：映射
 * 
 * @author Max_Qiu
 */
public class MiddleOperationOfMapTest {
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
     * map：接收Lambda，将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
     * 
     * flatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void test() {
        // 提取名称集合
        Stream<String> str = employeeList.stream().map(Employee::getName);
        str.forEach(System.out::println);

        System.out.println("=============================================");

        List<String> strList = Arrays.asList("aa", "bb", "cc", "dd", "ee");

        // 转换为大写
        Stream<String> stream = strList.stream().map(String::toUpperCase);
        stream.forEach(System.out::println);

        System.out.println("---------------------------------------------");

        Stream<Stream<Character>> stream2 = strList.stream().map(MiddleOperationOfMapTest::filterCharacter);
        stream2.forEach((sm) -> sm.forEach(System.out::println));

        System.out.println("---------------------------------------------");

        Stream<Character> stream3 = strList.stream().flatMap(MiddleOperationOfMapTest::filterCharacter);
        stream3.forEach(System.out::println);
    }

    public static Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();
        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }
        return list.stream();
    }
}
