import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Max_Qiu
 */
public class Test {

    /**
     * 消息队列的大小
     */
    private static final int MAX_NUM = 10;

    public static void main(String[] args) {

        // 1、创建一个BlockingQueue
        LinkedBlockingQueue<Apple> queue = new LinkedBlockingQueue<>(MAX_NUM);

        // 2、创建一个生产者，一个消费者
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        // 3、开启两个线程
        producer.start();
        consumer.start();

    }
}
