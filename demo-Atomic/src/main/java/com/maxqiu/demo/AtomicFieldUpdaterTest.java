package com.maxqiu.demo;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 原子更新器
 *
 * @author Max_Qiu
 */
public class AtomicFieldUpdaterTest {
    @Test
    void test() {
        // 整形字段的更新器
        // 使用静态方法创建更新器，第一个参数为对象类型，第二个参数为要更新的字段名称，该字段必须修饰为 public volatile
        AtomicIntegerFieldUpdater<User1> integerUpdater = AtomicIntegerFieldUpdater.newUpdater(User1.class, "age");
        User1 user1 = new User1("Java", 22);
        // 获取当前对象被管理字段的值并原子自增
        System.out.println(integerUpdater.getAndIncrement(user1));
        // 获取当前对象被管理字段的值
        System.out.println(integerUpdater.get(user1));

        // 引用类型字段的更新器
        // 使用静态方法创建更新器，第一个参数为对象类型，第二个参数为要更新的字段类型，第三个参数为要更新的字段名称，该字段必须修饰为 public volatile
        AtomicReferenceFieldUpdater<User2, Integer> referenceUpdater = AtomicReferenceFieldUpdater.newUpdater(User2.class, Integer.class, "age");
        User2 user2 = new User2("Jerry", 18);
        // 获取当前对象被管理字段的值并设置新值
        System.out.println(referenceUpdater.getAndSet(user2, 20));
        // 获取当前对象被管理字段的值
        System.out.println(referenceUpdater.get(user2));
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class User1 {
    private String name;
    public volatile int age;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class User2 {
    private String name;
    public volatile Integer age;
}
