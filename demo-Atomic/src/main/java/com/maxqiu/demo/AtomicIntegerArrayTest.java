package com.maxqiu.demo;

import java.util.concurrent.atomic.AtomicIntegerArray;

import org.junit.jupiter.api.Test;

/**
 * 数组类型 原子类 AtomicIntegerArray 示例代码
 *
 * @author Max_Qiu
 */
public class AtomicIntegerArrayTest {
    @Test
    void test() {
        // 有参构造：指定数组长度，数组内初始值为0
        // AtomicIntegerArray atomic = new AtomicIntegerArray(10);
        // 有参构造：传入数组
        AtomicIntegerArray atomic = new AtomicIntegerArray(new int[] {1, 2, 3, 4, 5});

        System.out.println("数组的长度为：" + atomic.length());

        System.out.println("\n获取指定位置的值");
        for (int i = 0; i < atomic.length(); i++) {
            System.out.println(i + "\t" + atomic.get(i));
        }

        System.out.println("\n更新指定位置值");
        atomic.set(0, 2);
        System.out.println("位置：" + 0 + "\t新值：" + atomic.get(0));

        System.out.println("\n获取指定位置当前值，并设置新值");
        System.out.println("位置：" + 0 + "\t原始：" + atomic.getAndSet(0, 2));
        System.out.println("位置：" + 0 + "\t新值：" + atomic.get(0));

        System.out.println("\n获取指定位置当前值，并增加值");
        System.out.println("位置：" + 0 + "\t原始：" + atomic.getAndAdd(0, 2));
        System.out.println("位置：" + 0 + "\t新值：" + atomic.get(0));

        System.out.println("\n指定位置增加值，并获取结果");
        System.out.println("位置：" + 0 + "\t新值：" + atomic.addAndGet(0, 2));

        System.out.println("\n获取指定位置当前值，并自增");
        System.out.println("位置：" + 0 + "\t原始：" + atomic.getAndIncrement(0));
        System.out.println("位置：" + 0 + "\t新值：" + atomic.get(0));

        System.out.println("\n获取指定位置当前值，并自减");
        System.out.println("位置：" + 0 + "\t原始：" + atomic.getAndDecrement(0));
        System.out.println("位置：" + 0 + "\t新值：" + atomic.get(0));

        System.out.println("\n指定位置自增后，获取当前值，相当于 ++i");
        System.out.println("位置：" + 0 + "\t新值：" + atomic.incrementAndGet(0));

        System.out.println("\n指定位置自减后，获取当前值，相当于 --i");
        System.out.println("位置：" + 0 + "\t新值：" + atomic.decrementAndGet(0));

        System.out.println("\n指定位置比较并修改值");
        int i = atomic.get(0);
        System.out.println("位置：" + 0 + "\t原始：" + i);
        System.out.println("位置：" + 0 + "\t修改结果1：" + atomic.compareAndSet(0, i, 8) + "\t值：" + atomic.get(0));
        System.out.println("位置：" + 0 + "\t修改结果2：" + atomic.compareAndSet(0, i, 8) + "\t值：" + atomic.get(0));
    }
}
