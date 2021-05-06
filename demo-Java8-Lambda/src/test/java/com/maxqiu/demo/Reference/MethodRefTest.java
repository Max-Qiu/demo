package com.maxqiu.demo.Reference;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import com.maxqiu.demo.Employee;

/**
 * 方法引用
 * 
 * @author Max_Qiu
 */
public class MethodRefTest {
    /**
     * 对象 :: 实例方法
     */
    @Test
    public void test1() {
        Employee emp = new Employee(1, "张三", 18, 9000.00);

        Supplier<String> sup = () -> emp.getName();
        System.out.println(sup.get());

        System.out.println("-------------------------------------");

        Supplier<String> sup2 = emp::getName;
        System.out.println(sup2.get());

        System.out.println("=====================================");

        PrintStream ps = System.out;
        Consumer<String> con = (str) -> ps.println(str);
        con.accept("Hello World！");

        System.out.println("-------------------------------------");

        Consumer<String> con2 = ps::println;
        con2.accept("Hello Java8！");

        System.out.println("-------------------------------------");

        Consumer<String> con3 = System.out::println;
        con3.accept("Hello Lambda");
    }

    /**
     * 类 :: 静态方法
     */
    @Test
    public void test2() {
        Comparator<Integer> com1 = (x, y) -> Integer.compare(x, y);
        System.out.println(com1.compare(1, 2));

        System.out.println("-------------------------------------");

        Comparator<Integer> com2 = Integer::compare;
        System.out.println(com2.compare(1, 2));

        System.out.println("=====================================");

        BiFunction<Double, Double, Double> fun1 = (x, y) -> Math.max(x, y);
        System.out.println(fun1.apply(1.2, 1.5));

        System.out.println("-------------------------------------");

        BiFunction<Double, Double, Double> fun2 = Math::max;
        System.out.println(fun2.apply(1.2, 1.5));
    }

    /**
     * 类 :: 实例方法
     */
    @Test
    public void test3() {
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        System.out.println(bp.test("abcde", "abcde"));

        System.out.println("-------------------------------------");

        BiPredicate<String, String> bp2 = String::equals;
        System.out.println(bp2.test("abc", "abc"));

        System.out.println("=====================================");

        Function<Employee, String> fun = (e) -> e.show();
        System.out.println(fun.apply(new Employee()));

        System.out.println("-------------------------------------");

        Function<Employee, String> fun2 = Employee::show;
        System.out.println(fun2.apply(new Employee()));
    }
}
