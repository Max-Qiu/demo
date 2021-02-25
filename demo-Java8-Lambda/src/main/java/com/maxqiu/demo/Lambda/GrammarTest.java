package com.maxqiu.demo.Lambda;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.junit.Test;

/**
 * Lambda语法示例
 * 
 * @author Max_Qiu
 */
public class GrammarTest {

    /**
     * 无参无返回值
     */
    @Test
    public void test1() {
        // 传统方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
            }
        }).start();

        // lambda语法
        new Thread(() -> {
            System.out.println("2");
        }).start();

        // 单行方法时，不需要大括号
        new Thread(() -> System.out.println("3")).start();

        // 和匿名内部类一样，可以使用外部变量
        int a = 1;
        new Thread(() -> System.out.println(a)).start();
    }

    /**
     * 有一个参无返回值
     */
    @Test
    public void test2() {
        // 传统方法
        Consumer<String> consumer1 = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        consumer1.accept("1");

        // lambda语法
        Consumer<String> consumer2 = (String x) -> System.out.println(x);
        consumer2.accept("2");

        // 参数的类型可以不写，因为JVM编译器通过上下文推断出数据类型，即“类型推断”
        Consumer<String> consumer3 = (x) -> System.out.println(x);
        consumer3.accept("2");

        // 单个参数时，不需要小括号
        Consumer<String> consumer4 = x -> System.out.println(x);
        consumer4.accept("3");
    }

    /**
     * 有多个参无返回值
     */
    @Test
    public void test3() {
        // 传统方法
        BiConsumer<Integer, Integer> consumer1 = new BiConsumer<Integer, Integer>() {
            @Override
            public void accept(Integer a, Integer b) {
                System.out.println(a + b);
            }
        };
        consumer1.accept(1, 2);

        // lambda语法
        BiConsumer<Integer, Integer> consumer2 = (a, b) -> System.out.println(a + b);
        consumer2.accept(1, 2);
    }

    /**
     * 有返回值
     */
    @Test
    public void test4() {
        // 传统方法
        Supplier<Integer> supplier1 = new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println("哈哈");
                return 1;
            }
        };
        System.out.println(supplier1.get());

        // lambda语法
        Supplier<Integer> supplier2 = () -> {
            System.out.println("哈哈");
            return 1;
        };
        System.out.println(supplier2.get());

        // 单行方法时，不需要return和大括号
        Supplier<Integer> supplier3 = () -> 1;
        System.out.println(supplier3.get());
    }

}
