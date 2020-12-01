import java.util.concurrent.LinkedBlockingQueue;

/**
 * 消费者<br>
 * 每秒消费一条
 * 
 * @author Max_Qiu
 */
public class Consumer extends Thread {

    public LinkedBlockingQueue<Apple> queue;

    public Consumer(LinkedBlockingQueue<Apple> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        // 只要阻塞队列不为空，就一直从里面取苹果
        while (true) {
            try {
                Apple take = queue.take();
                System.out.println(take.toString());
                System.out.println("Consumer Apple is left : " + queue.size());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                System.out.println("No Apple " + queue.size());
                e.printStackTrace();
            }

        }
    }

}
