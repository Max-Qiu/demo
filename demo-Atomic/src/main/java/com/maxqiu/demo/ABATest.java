package com.maxqiu.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

import org.junit.jupiter.api.Test;

/**
 * ABA问题示例
 *
 * @author Max_Qiu
 */
public class ABATest {
    /**
     * 普通的对象引用原子类，不能解决ABA问题
     */
    @Test
    void atomicReference() {
        AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "t1").start();
        new Thread(() -> {
            // 暂停一会t2线程，保证上面的t1线程完成了一次ABA操作
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100, 2019));
        }, "t2").start();

        // 等待线程结束
        while (Thread.activeCount() > 2) {
            // 让出当前线程，给其他线程执行
            Thread.yield();
        }

        System.out.println(atomicReference.get());
    }

    /**
     * 带版本控制的对象引用原子类，可以解决ABA的问题
     */
    @Test
    void atomicStampedReference() {
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);
        new Thread(() -> {
            // 获取初始版本
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第1次版本号：" + stamp);
            // 暂停一会t1线程，保证t2线程获取到了初始版本号
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100, 101, stamp, ++stamp);
            System.out.println(Thread.currentThread().getName() + "\t第2次版本号：" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100, stamp, ++stamp);
            System.out.println(Thread.currentThread().getName() + "\t第3次版本号：" + atomicStampedReference.getStamp());
        }, "t1").start();

        new Thread(() -> {
            // 获取初始版本
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第1次版本号：" + stamp);
            // 暂停一会t2线程，保证上面的t3线程完成了一次ABA操作
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicStampedReference.compareAndSet(100, 2019, stamp, ++stamp);
            System.out.println(Thread.currentThread().getName() + "\t修改成功否：" + result);
        }, "t2").start();

        // 等待线程结束
        while (Thread.activeCount() > 2) {
            // 让出当前线程，给其他线程执行
            Thread.yield();
        }

        System.out.println("当前最新实际版本号：" + atomicStampedReference.getStamp() + "\t当前实际最新值：" + atomicStampedReference.getReference());
    }
}
