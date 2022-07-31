package com.maxqiu.demo.ReadWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 *
 * @author Max_Qiu
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) throws InterruptedException {
        MyCache myCache = new MyCache();
        // 创建线程放数据
        for (int i = 1; i <= 5; i++) {
            final int num = i;
            new Thread(() -> myCache.put(num + "", num + ""), String.valueOf(i)).start();
        }

        // 创建线程取数据
        for (int i = 1; i <= 5; i++) {
            final int num = i;
            new Thread(() -> myCache.get(num + ""), String.valueOf(i)).start();
        }
    }

    // 资源类
    private static class MyCache {
        // 创建map集合
        private volatile Map<String, String> map = new HashMap<>();

        // 创建读写锁对象
        private ReadWriteLock rwLock = new ReentrantReadWriteLock();

        // 放数据
        public void put(String key, String value) {
            // 添加写锁
            rwLock.writeLock().lock();

            try {
                System.out.println(Thread.currentThread().getName() + " 正在写操作" + key);
                // 暂停一会
                TimeUnit.MICROSECONDS.sleep(500);
                // 放数据
                map.put(key, value);
                System.out.println(Thread.currentThread().getName() + " 写完了" + key);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放写锁
                rwLock.writeLock().unlock();
            }
        }

        // 取数据
        public String get(String key) {
            // 添加读锁
            rwLock.readLock().lock();
            String result = null;
            try {
                System.out.println(Thread.currentThread().getName() + " 正在读取操作" + key);
                // 暂停一会
                TimeUnit.MICROSECONDS.sleep(500);
                result = map.get(key);
                System.out.println(Thread.currentThread().getName() + " 取完了" + key);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放读锁
                rwLock.readLock().unlock();
            }
            return result;
        }
    }
}
