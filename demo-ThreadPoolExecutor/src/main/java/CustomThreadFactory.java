import java.util.concurrent.ThreadFactory;

/**
 * 自定义线程工厂<br>
 * 仅当线程池内的线程被创建时会执行
 * 
 * @author Max_Qiu
 */
public class CustomThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        System.out.println("创建了一个Thread");
        // 创建一个线程
        return new Thread(r);
    }
}
