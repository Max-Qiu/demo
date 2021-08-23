package com.maxqiu.demo;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Max_Qiu
 */
public class CreateThreadPoolExecutor {
    /**
     * @param corePoolSize
     *            the number of threads to keep in the pool, even if they are idle, unless
     *            {@code allowCoreThreadTimeOut} is set 池中一直保持的线程的数量，即使线程空闲。除非设置了 allowCoreThreadTimeOut
     * @param maximumPoolSize
     *            the maximum number of threads to allow in the pool 池中允许的最大的线程数
     * @param keepAliveTime
     *            when the number of threads is greater than the core, this is the maximum time that excess idle threads
     *            will wait for new tasks before terminating. 当线程数大于核心线程数的时候，线程在最大多长时间没有接到新任务就会终止释放， 最终线程池维持在
     *            corePoolSize 大小
     * @param unit
     *            the time unit for the {@code keepAliveTime} argument 时间单位
     * @param workQueue
     *            the queue to use for holding tasks before they are* executed. This queue will hold only the
     *            {@code Runnable} tasks submitted by the {@code execute} method. 阻塞队列，用来存储等待执行的任务，如果当前对线程的需求超过了
     *            corePoolSize 大小，就会放在这里等待空闲线程执行。
     * @param threadFactory
     *            the factory to use when the executor creates a new thread 创建线程的工厂，比如指定线程名等
     * @param handler
     *            the handler to use when execution is blocked because the thread bounds and queue capacities are
     *            reached 拒绝策略，如果线程满了，线程池就会使用拒绝策略。
     * @param args
     */
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 5, 30, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20), new CustomThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }
}
