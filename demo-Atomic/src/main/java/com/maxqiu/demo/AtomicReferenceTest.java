package com.maxqiu.demo;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import com.maxqiu.demo.entity.Person;

/**
 * 引用类型 原子类 AtomicReference 示例代码
 *
 * @author Max_Qiu
 */
public class AtomicReferenceTest {
    @Test
    void test() {
        // 无参构造：默认null
        // AtomicReference<Person> atomic = new AtomicReference<>();
        // 有参构造：传入初始对象
        AtomicReference<Person> atomic = new AtomicReference<>(new Person("张三", 25));

        System.out.println("获取当前值：" + atomic.get());

        System.out.println("\n设置值");
        atomic.set(new Person("李四", 30));
        System.out.println("新值：" + atomic.get());

        System.out.println("\n获取当前值，并设置新值");
        System.out.println("原始：" + atomic.getAndSet(new Person("王五", 35)));
        System.out.println("新值：" + atomic.get());

        System.out.println("\n比较并修改值");
        Person oldP = atomic.get();
        Person newP = new Person("赵六", 40);
        System.out.println("原始：" + oldP);
        System.out.println("修改结果1：" + atomic.compareAndSet(oldP, newP) + "\t值：" + atomic.get());
        System.out.println("修改结果2：" + atomic.compareAndSet(oldP, newP) + "\t值：" + atomic.get());
    }
}
