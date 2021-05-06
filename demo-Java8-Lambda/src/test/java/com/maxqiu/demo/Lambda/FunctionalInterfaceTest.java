package com.maxqiu.demo.Lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

/**
 * Java8 内置的四大核心函数式接口
 * 
 * Consumer<T> : 消费型接口 void accept(T t);
 * 
 * Supplier<T> : 供给型接口 T get();
 * 
 * Function<T, R> : 函数型接口 R apply(T t);
 * 
 * Predicate<T> : 断言型接口 boolean test(T t);
 * 
 * @author Max_Qiu
 */
public class FunctionalInterfaceTest {
    @Test
    public void test1() {
        happy(10000, (m) -> System.out.println("传入的参数是：" + m));
    }

    /**
     * Consumer<T> 消费型接口
     */
    public void happy(int v, Consumer<Integer> con) {
        con.accept(v);
    }

    /**
     * 需求：产生指定个数的整数，并放入集合中
     */
    @Test
    public void test2() {
        // 100随机
        List<Integer> numList = getNumList(5, () -> (int)(Math.random() * 100));
        for (Integer num : numList) {
            System.out.println(num);
        }
        System.out.println("==============");
        // 1000随机
        List<Integer> numList2 = getNumList(5, () -> (int)(Math.random() * 1000));
        for (Integer num : numList2) {
            System.out.println(num);
        }
    }

    /**
     * Supplier<T> 供给型接口
     */
    public List<Integer> getNumList(int num, Supplier<Integer> sup) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Integer n = sup.get();
            list.add(n);
        }
        return list;
    }

    /**
     * 使用不同的方法处理字符串
     */
    @Test
    public void test3() {
        String newStr = strHandler("\t\t\t aaa   ", (str) -> str.trim());
        System.out.println(newStr);
        String subStr = strHandler("1234567890", (str) -> str.substring(2, 5));
        System.out.println(subStr);
    }

    /**
     * Function<T, R> 函数型接口
     */
    public String strHandler(String str, Function<String, String> fun) {
        return fun.apply(str);
    }

    /**
     * 需求：使用不同方式将满足条件的字符串放入集合中
     */
    @Test
    public void test4() {
        List<String> list = Arrays.asList("Hello", "atguigu", "Lambda", "w", "ok");
        List<String> strList = filterStr(list, (s) -> s.length() > 5);
        for (String str : strList) {
            System.out.println(str);
        }
        System.out.println("==============");
        List<String> strList2 = filterStr(list, (s) -> s.length() < 2);
        for (String str : strList2) {
            System.out.println(str);
        }
    }

    /**
     * Predicate<T> 断言型接口
     */
    public List<String> filterStr(List<String> list, Predicate<String> pre) {
        List<String> strList = new ArrayList<>();
        for (String str : list) {
            if (pre.test(str)) {
                strList.add(str);
            }
        }
        return strList;
    }
}
