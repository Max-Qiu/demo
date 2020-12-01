package com.maxqiu.demo.ProducterAndConsumerForLock;

/**
 * 消费者
 * 
 * @author Max_Qiu
 */
public class Consumer implements Runnable {
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}
