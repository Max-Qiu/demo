package com.maxqiu.demo.old;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import org.junit.Test;

/**
 * @author Max_Qiu
 */
public class TestOldSimpleDateFormat {

    /**
     * 多线程调用 SimpleDateFormat.parse()
     * 
     * 出现线程安全问题
     */
    @Test
    public void testParse() throws ExecutionException, InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Callable<Date> task = () -> sdf.parse("20161121");

        ExecutorService pool = Executors.newFixedThreadPool(1);

        List<Future<Date>> results = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }

        for (Future<Date> future : results) {
            System.out.println(future.get());
        }

        pool.shutdown();
    }

    /**
     * TODO ThreadLocal 需要看一下
     */
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        Callable<Date> task = () -> DateFormatThreadLocal.convert("20161121");

        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<Date>> results = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }

        for (Future<Date> future : results) {
            System.out.println(future.get());
        }

        pool.shutdown();
    }

    /**
     * 使用Java8的 DateTimeFormatter.parse() 处理Java8的 LocalDate
     */
    @Test
    public void testJava8Parse() throws ExecutionException, InterruptedException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

        Callable<LocalDate> task = () -> LocalDate.parse("20161121", dtf);

        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<LocalDate>> results = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }

        for (Future<LocalDate> future : results) {
            System.out.println(future.get());
        }

        pool.shutdown();
    }
}
