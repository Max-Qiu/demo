package com.maxqiu.demo;

import java.util.concurrent.atomic.AtomicStampedReference;

import org.junit.jupiter.api.Test;

import com.maxqiu.demo.entity.Person;

/**
 * 引用类型 原子类 AtomicStampedReference 示例代码
 *
 * @author Max_Qiu
 */
public class AtomicStampedReferenceTest {
    @Test
    void test() {
        // 有参构造：初始化对象和标记
        AtomicStampedReference<Person> atomic = new AtomicStampedReference<>(new Person("张三", 18), 1);

        System.out.println("取值：");
        System.out.println("对象：" + atomic.getReference());
        System.out.println("版本：" + atomic.getStamp());

        System.out.println("\n获取当前值并同时将标记存储在boolean类型的数组中（数组长度至少为1）");
        int[] arr = new int[1];
        System.out.println("对象：" + atomic.get(arr));
        System.out.println("版本：" + arr[0]);

        System.out.println("\n设置值和版本");
        Person person = new Person("李四", 30);
        atomic.set(person, 2);
        System.out.println("新对象：" + atomic.getReference());
        System.out.println("新版本：" + atomic.getStamp());

        System.out.println("\n当对象相同时，单独修改版本");
        System.out.println("原版本：" + atomic.getStamp());
        System.out.println("修改结果：" + atomic.attemptStamp(person, 3));
        System.out.println("新版本：" + atomic.getStamp());

        System.out.println("\n比较并修改值");
        Person oldP = atomic.getReference();
        int oldV = atomic.getStamp();
        Person newP = new Person("王五", 45);
        int newV = 4;
        System.out.println("修改结果1：" + atomic.compareAndSet(oldP, newP, oldV, newV));
        System.out.println("对象：" + atomic.getReference());
        System.out.println("版本：" + atomic.getStamp());
        System.out.println("修改结果2：" + atomic.compareAndSet(oldP, newP, oldV, newV));
        System.out.println("对象：" + atomic.getReference());
        System.out.println("版本：" + atomic.getStamp());
    }
}
