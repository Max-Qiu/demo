package com.maxqiu.demo;

import java.util.concurrent.atomic.AtomicMarkableReference;

import org.junit.jupiter.api.Test;

import com.maxqiu.demo.entity.Person;

/**
 * 引用类型 原子类 AtomicMarkableReference 示例代码
 *
 * @author Max_Qiu
 */
public class AtomicMarkableReferenceTest {
    @Test
    void test() {
        // 有参构造：初始化对象和标记
        AtomicMarkableReference<Person> atomic = new AtomicMarkableReference<>(new Person("张三", 18), false);

        System.out.println("取值：");
        System.out.println("对象：" + atomic.getReference());
        System.out.println("标记：" + atomic.isMarked());

        System.out.println("\n获取当前值并同时将标记存储在boolean类型的数组中（数组长度至少为1）");
        boolean[] arr = new boolean[1];
        System.out.println("对象：" + atomic.get(arr));
        System.out.println("标记：" + arr[0]);

        System.out.println("\n设置值和标记");
        Person person = new Person("李四", 30);
        atomic.set(person, true);
        System.out.println("新对象：" + atomic.getReference());
        System.out.println("新标记：" + atomic.isMarked());

        System.out.println("\n当对象相同时，单独修改标记");
        System.out.println("原标记：" + atomic.isMarked());
        System.out.println("修改结果：" + atomic.attemptMark(person, false));
        System.out.println("新标记：" + atomic.isMarked());

        System.out.println("\n比较并修改值");
        Person oldP = atomic.getReference();
        boolean oldF = atomic.isMarked();
        Person newP = new Person("王五", 45);
        boolean newF = false;
        System.out.println("修改结果1：" + atomic.compareAndSet(oldP, newP, oldF, newF));
        System.out.println("对象：" + atomic.getReference());
        System.out.println("标记：" + atomic.isMarked());
        System.out.println("修改结果2：" + atomic.compareAndSet(oldP, newP, oldF, newF));
        System.out.println("对象：" + atomic.getReference());
        System.out.println("标记：" + atomic.isMarked());

    }
}
