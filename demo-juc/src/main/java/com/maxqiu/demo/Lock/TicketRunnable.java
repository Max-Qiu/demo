package com.maxqiu.demo.Lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用于解决多线程安全问题的方式：<br>
 *
 * synchronized:隐式锁<br>
 * 1. 同步代码块<br>
 * 2. 同步方法<br>
 * <br>
 * jdk 1.5 后：<br>
 * 3. 同步锁 Lock<br>
 * 注意：<br>
 * Lock是一个显示锁，需要通过 lock() 方法上锁，必须通过 unlock() 方法进行释放锁
 * 
 * @author Max_Qiu
 */
public class TicketRunnable implements Runnable {
    private int tick = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock();

            try {
                if (tick > 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "售票！余票：" + --tick);
                } else {
                    break;
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
