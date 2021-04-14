/**
 * @author Max_Qiu
 */
public class SyncMethodObj {
    private static int normalCount = 0;
    private static int synchronizedCount = 0;

    public void normalAdd() {
        for (int i = 0; i < 5; i++) {
            normalCount++;
            System.out.println(Thread.currentThread().getName() + ":" + normalCount);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //

    /**
     * synchronized 方法锁（对象锁）：<br>
     * 修饰在方法上：<br>
     * 多个线程调用<strong><em>同一个对象</em></strong>的同步方法会阻塞<br>
     * 调用<strong><em>不同对象</em></strong>的同步方法不会阻塞
     */
    public synchronized void synchronizedAdd1() {
        for (int i = 0; i < 5; i++) {
            synchronizedCount++;
            System.out.println("          " + Thread.currentThread().getName() + ":" + synchronizedCount);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 不同方法添加同步锁后，同一对象也会阻塞
     */
    public synchronized void synchronizedAdd2() {
        for (int i = 0; i < 5; i++) {
            synchronizedCount++;
            System.out.println("          " + Thread.currentThread().getName() + ":" + synchronizedCount);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 修饰代码块<br>
     * 这个this就是指当前对象（类的实例）
     */
    public void synchronizedAdd3() {
        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                synchronizedCount++;
                System.out.println("          " + Thread.currentThread().getName() + ":" + synchronizedCount);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
