package com.maxqiu.demo.ReadWriteLock;

/**
 *
 * ReadWriteLock:=读写锁<br>
 * <br>
 * 写写/读写 需要“互斥”<br>
 * <br>
 * 读读 不需要互斥
 *
 * @author Max_Qiu
 */
public class TestReadWriteLock {
    public static void main(String[] args) {

        ReadWriteLockEntity entity = new ReadWriteLockEntity();

        new Thread(() -> entity.setNumber((int)(Math.random() * 101)), "Write").start();

        for (int i = 0; i < 50; i++) {
            new Thread(entity::getNumber).start();
            // 不要写成下面这样，写了就看不出效果了（可能是::效率有点低）
            // new Thread(entity::getNumber).start();
        }

    }
}
