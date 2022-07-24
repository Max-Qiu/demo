package com.maxqiu.demo.Lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 卖票
 *
 * @author Max_Qiu
 */
public class SaleTicket {
    // 创建多个线程，调用资源类的操作方法
    // 创建三个线程
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "C").start();
    }

    /**
     * 创建资源类，定义属性和和操作方法
     */
    private static class Ticket {
        // 票数量
        private int number = 30;

        // 创建可重入锁
        private final ReentrantLock lock = new ReentrantLock(true);

        // 卖票方法
        public void sale() {
            // 上锁
            lock.lock();
            try {
                // 判断是否有票
                if (number > 0) {
                    System.out.println(Thread.currentThread().getName() + " ：卖出" + (number--) + " 剩余：" + number);
                }
            } finally {
                // 解锁
                lock.unlock();
            }
        }
    }
}
