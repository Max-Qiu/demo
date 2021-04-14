/**
 * 测试类
 * 
 * @author Max_Qiu
 */
public class Test {

    public static void main(String[] args) {
        // test1();
        // test2();
        test3();
    }

    private static void test1() {
        // 创建一个对象
        SyncMethodObj syncMethodObj = new SyncMethodObj();

        // 创建两个线程实例操作 同一个对象，不添加同步锁
        Thread thread1 = new Thread(syncMethodObj::normalAdd, "Thread1");
        Thread thread2 = new Thread(syncMethodObj::normalAdd, "Thread2");
        // 启动！发现 thread1 thread1 同时执行，且 normalCount 有可能不为10
        thread1.start();
        thread2.start();

        // 创建两个线程实例操作 同一个对象，添加同步锁
        Thread thread3 = new Thread(syncMethodObj::synchronizedAdd1, "Thread3");
        Thread thread4 = new Thread(syncMethodObj::synchronizedAdd2, "Thread4");
        Thread thread5 = new Thread(syncMethodObj::synchronizedAdd3, "Thread5");
        // 启动！发现 thread3 先执行， thread4 在 thread3 执行完毕后执行，且 synchronizedCount 一定为10
        thread3.start();
        thread4.start();
        thread5.start();

        // 多次运行发现，线程执行顺序随机，且只有一个线程可以拿到对象锁
        // 同时也发现，当一个线程访问同步代码块时，另一个线程可以访问该对象的非同步代码块，如：normalAdd和synchronizedAdd1
    }

    private static void test2() {
        // 创建两个对象
        SyncMethodObj syncMethodObj1 = new SyncMethodObj();
        SyncMethodObj syncMethodObj2 = new SyncMethodObj();

        // 创建两个线程实例操作 操作不同的对象，且添加同步锁
        Thread thread1 = new Thread(syncMethodObj1::synchronizedAdd2, "Thread1");
        Thread thread2 = new Thread(syncMethodObj2::synchronizedAdd2, "Thread2");
        // 启动！发现并没有锁住，因为synchronized只锁定当前对象，syncObj1和syncObj2不是同一个对象，所以不会锁住
        thread1.start();
        thread2.start();
    }

    private static void test3() {
        // 创建两个线程实例操作 操作同一个对象锁
        Thread thread1 = new Thread(SyncClassObj::synchronizedAdd1, "Thread1");
        Thread thread2 = new Thread(SyncClassObj::synchronizedAdd1, "Thread2");
        // 启动！发现锁住
        thread1.start();
        thread2.start();

        // 创建两个对象
        SyncClassObj syncClassObj1 = new SyncClassObj();
        SyncClassObj syncClassObj2 = new SyncClassObj();

        // 创建两个线程实例操作 操作不同的对象，且添加同步锁，synchronized代码块参数为当前类
        Thread thread3 = new Thread(syncClassObj1::synchronizedAdd2, "Thread3");
        Thread thread4 = new Thread(syncClassObj2::synchronizedAdd2, "Thread4");
        // 启动！发现锁住
        thread3.start();
        thread4.start();

        // 多次运行发现，线程执行顺序随机，且只有一个线程可以拿到类锁
    }
}
