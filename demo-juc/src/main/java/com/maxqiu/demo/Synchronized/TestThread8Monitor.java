package com.maxqiu.demo.Synchronized;

/**
 * 题目：判断打印的 "one" or "two" ？<br>
 * <br>
 * 1. 两个普通同步方法，两个线程，标准打印， 打印? //one two<br>
 * 2. 新增 Thread.sleep() 给 getOne() ,打印? //one two<br>
 * 3. 新增普通方法 getThree() , 打印? //three one two<br>
 * 4. 两个普通同步方法，两个 Number 对象，打印? //two one<br>
 * 5. 修改 getOne() 为静态同步方法，打印? //two one<br>
 * 6. 修改两个方法均为静态同步方法，一个 Number 对象? //one two<br>
 * 7. 一个静态同步方法，一个非静态同步方法，两个 Number 对象? //two one<br>
 * 8. 两个静态同步方法，两个 Number 对象? //one two<br>
 * <br>
 * 线程八锁的关键:<br>
 * ①非静态方法的锁默认为 this, 静态方法的锁为 对应的 Class 实例<br>
 * ②某一个时刻内，只能有一个线程持有锁，无论几个方法。
 * 
 * @author Max_Qiu
 */
public class TestThread8Monitor {

    public static void main(String[] args) {
        Number number = new Number();
        Number number2 = new Number();

        new Thread(() -> number.getOne()).start();

        new Thread(() -> {
            // number.getTwo();
            number2.getTwo();
        }).start();

        // new Thread(() -> number.getThree()).start();

    }

}
