package com.maxqiu.demo.ABC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Max_Qiu
 */
public class Alternate {

    // 当前线程标记
    private int number = 1;

    private Lock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void loopA(int totalLoop) {
        lock.lock();

        try {
            if (number != 1) {
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + totalLoop);
            number = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopB(int totalLoop) {
        lock.lock();

        try {
            if (number != 2) {
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + totalLoop);
            number = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopC(int totalLoop) {
        lock.lock();

        try {
            if (number != 3) {
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + totalLoop);
            number = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
