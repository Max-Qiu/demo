package com.maxqiu.demo.Callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 一、创建执行线程的方式三：实现 Callable 接口。 相较于实现 Runnable 接口的方式，方法可以有返回值，并且可以抛出异常。<br>
 * <br>
 * 二、执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。 FutureTask 是 Future 接口的实现类
 * 
 * @author Max_Qiu
 */
public class TestCallable {

    public static void main(String[] args) {
        DemoCallable demoCallable = new DemoCallable();

        // 1.执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。
        FutureTask<Integer> result = new FutureTask<>(demoCallable);

        new Thread(result).start();

        // 2.接收线程运算后的结果
        try {
            // FutureTask 可用于 闭锁
            Integer sum = result.get();

            System.out.println(sum);
            System.out.println("------------------------------------");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}