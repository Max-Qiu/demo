package com.maxqiu.demo.ProducterAndConsumer;

/**
 * 店员
 * 
 * @author Max_Qiu
 */
public class Clerk {
    private int product = 0;

    /**
     * 进货
     */
    public synchronized void get() {
        // 为了避免虚假唤醒问题，应该总是使用在循环中
        while (product >= 5) {
            System.out.println("产品已满！");

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println(Thread.currentThread().getName() + " : " + ++product);

        this.notifyAll();

    }

    /**
     * 卖货
     */
    public synchronized void sale() {
        while (product <= 0) {
            System.out.println("缺货！");

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " : " + --product);
        this.notifyAll();

    }
}
