import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义处理机制
 * 
 * @author Max_Qiu
 */
public class CustomPolicy implements RejectedExecutionHandler {

    /**
     * 处理线程
     * 
     * @param r
     *            需要处理的线程
     * @param executor
     *            当前线程池
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        // 啥都不做，相当于：ThreadPoolExecutor.DiscardPolicy()
    }
}
