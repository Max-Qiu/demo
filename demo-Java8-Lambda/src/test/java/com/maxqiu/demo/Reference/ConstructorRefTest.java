package com.maxqiu.demo.Reference;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import com.maxqiu.demo.Employee;

/**
 * 构造器引用
 * 
 * @author Max_Qiu
 */
public class ConstructorRefTest {
    /**
     * 无参构造
     */
    @Test
    public void test1() {
        Supplier<Employee> sup1 = () -> new Employee();
        System.out.println(sup1.get());

        System.out.println("------------------------------------");

        Supplier<Employee> sup2 = Employee::new;
        System.out.println(sup2.get());
    }

    /**
     * 有参构造
     */
    @Test
    public void test2() {
        Function<String, Employee> fun1 = s -> new Employee(s);
        System.out.println(fun1.apply("aaa"));

        System.out.println("------------------------------------");

        Function<String, Employee> fun = Employee::new;
        System.out.println(fun.apply("aaa"));

        System.out.println("=====================================");

        BiFunction<String, Integer, Employee> biFun1 = (name, age) -> new Employee(name, age);
        System.out.println(biFun1.apply("bbb", 18));

        System.out.println("------------------------------------");

        BiFunction<String, Integer, Employee> biFun2 = Employee::new;
        System.out.println(biFun2.apply("bbb", 18));
    }
}
