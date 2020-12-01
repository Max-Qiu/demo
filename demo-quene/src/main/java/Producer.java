import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 生产者<br>
 * 每20秒放入20条消息
 * 
 * @author Max_Qiu
 */
public class Producer extends Thread {

    public LinkedBlockingQueue<Apple> queue;

    public Producer(LinkedBlockingQueue<Apple> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        // 只要阻塞队列没有满，就可以一直往里面放苹果
        while (true) {
            try {
                for (int i = 0; i < 10; i++) {
                    queue.put(new Apple(i, UUID.randomUUID().toString()));
                }
                System.out.println("Producer queue size " + queue.size());
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                System.out.println("Apple is enough !!  " + queue.size());
                e.printStackTrace();
            }

        }
    }

}
