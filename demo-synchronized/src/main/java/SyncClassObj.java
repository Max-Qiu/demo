/**
 * @author Max_Qiu
 */
public class SyncClassObj {
    private static int synchronizedCount = 0;

    /**
     * 修饰静态方法<br>
     * 静态方法属于类不属于对象<br>
     * 此时synchronized同步锁称为对象锁
     */
    public synchronized static void synchronizedAdd1() {
        for (int i = 0; i < 5; i++) {
            synchronizedCount++;
            System.out.println(Thread.currentThread().getName() + ":" + synchronizedCount);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void synchronizedAdd2() {
        synchronized (SyncClassObj.class) {
            for (int i = 0; i < 5; i++) {
                synchronizedCount++;
                System.out.println(Thread.currentThread().getName() + ":" + synchronizedCount);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
