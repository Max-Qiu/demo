package com.maxqiu.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * 1. 创建 Stream
 * 
 * @author Max_Qiu
 */
public class CreateStreamTest {

    /**
     * Collection 提供了两个方法：stream() 与 parallelStream()
     */
    @Test
    public void test1() {
        // 创建一个list集合
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");

        // 获取一个顺序流（操作时，顺序执行）
        Stream<String> stream = list.stream();
        stream.forEach(System.out::println);
        System.out.println("==========================");
        // 获取一个并行流（操作时，并发执行）
        Stream<String> parallelStream = list.parallelStream();
        parallelStream.forEach(System.out::println);
    }

    /**
     * 通过 Arrays 中的 stream() 获取一个数组流
     */
    @Test
    public void test2() {
        Integer[] nums = new Integer[] {1, 2, 3};
        Stream<Integer> stream = Arrays.stream(nums);
        stream.forEach(System.out::println);
    }

    /**
     * 通过 Stream 类中静态方法 of()
     */
    @Test
    public void test3() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);
        stream.forEach(System.out::println);
    }

    /**
     * 创建无限流
     */
    @Test
    public void test4() {
        // 迭代
        Stream<Integer> stream1 = Stream.iterate(0, x -> x + 2).limit(5);
        stream1.forEach(System.out::println);
        System.out.println("===============================");
        // 生成
        Stream<Double> stream2 = Stream.generate(Math::random).limit(5);
        stream2.forEach(System.out::println);
    }

}
