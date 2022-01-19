package com.maxqiu.demo;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

/**
 * 基本类型 原子类 AtomicInteger 示例代码
 *
 * @author Max_Qiu
 */
public class AtomicIntegerTest {
    @Test
    void test() {
        // 无参构造：默认值为0
        // AtomicInteger atomic = new AtomicInteger();
        // 有参构造：初始化传入初始值，
        AtomicInteger atomic = new AtomicInteger(1);

        System.out.println("获取当前值：" + atomic.get());

        System.out.println("\n设置值");
        atomic.set(5);
        System.out.println("新值：" + atomic.get());

        System.out.println("\n获取当前值，并设置新值");
        System.out.println("原始：" + atomic.getAndSet(2));
        System.out.println("新值：" + atomic.get());

        System.out.println("\n获取当前值，并增加值");
        System.out.println("原始：" + atomic.getAndAdd(2));
        System.out.println("新值：" + atomic.get());

        System.out.println("\n增加值，并获取结果");
        System.out.println("新值：" + atomic.addAndGet(2));

        System.out.println("\n获取当前值，并自增");
        System.out.println("原始：" + atomic.getAndIncrement());
        System.out.println("新值：" + atomic.get());

        System.out.println("\n获取当前值，并自减");
        System.out.println("原始：" + atomic.getAndDecrement());
        System.out.println("新值：" + atomic.get());

        System.out.println("\n自增后，获取当前值，相当于 ++i");
        System.out.println("新值：" + atomic.incrementAndGet());

        System.out.println("\n自减后，获取当前值，相当于 --i");
        System.out.println("新值：" + atomic.decrementAndGet());

        System.out.println("\n比较并修改值");
        int i = atomic.get();
        System.out.println("原始：" + i);
        System.out.println("修改结果1：" + atomic.compareAndSet(i, 8) + "\t值：" + atomic.get());
        System.out.println("修改结果2：" + atomic.compareAndSet(i, 8) + "\t值：" + atomic.get());

        System.out.println("\n转换");
        System.out.println(atomic.intValue());
        System.out.println(atomic.longValue());
        System.out.println(atomic.floatValue());
        System.out.println(atomic.doubleValue());
    }
}
