package com.maxqiu.demo.ProducterAndConsumerForLock;

/**
 * @author Max_Qiu
 */
public class TestProducerAndConsumer {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Producer producer = new Producer(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(producer, "生产者 A").start();
        new Thread(consumer, "消费者 B").start();

        new Thread(producer, "生产者 C").start();
        new Thread(consumer, "消费者 D").start();

    }
}
