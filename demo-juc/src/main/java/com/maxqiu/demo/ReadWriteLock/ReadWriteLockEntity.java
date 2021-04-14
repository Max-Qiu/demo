package com.maxqiu.demo.ReadWriteLock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Max_Qiu
 */
public class ReadWriteLockEntity {

    private int number = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void getNumber() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + number);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void setNumber(int number) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName());
            this.number = number;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
