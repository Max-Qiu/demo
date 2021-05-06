import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * 参考链接：http://www.crazyant.net/2124.html<br>
 *
 * public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
 * BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) <br>
 *
 * 参数：<br>
 * int corePoolSize:线程池基础线程数量<br>
 * int maximumPoolSize:线程池最大线程数<br>
 * long keepAliveTime:超出线程池基础线程数的线程运行结束后的存活时间<br>
 * TimeUnit unit:参数keepAliveTime的时间单位<br>
 * BlockingQueue<Runnable> workQueue:存储任务的阻塞队列<br>
 * ThreadFactory threadFactory:线程工厂 <br>
 * RejectedExecutionHandler handler:线程池满后，新的任务处理方式<br>
 *
 * 测试
 *
 * @author Max_Qiu
 */
public class QueueTest {
    /**
     * ArrayBlockingQueue<br>
     * 一个有边界的阻塞队列，它的内部实现是一个数组<br>
     * 有边界的意思是它的容量是有限的，我们必须在其初始化的时候指定它的容量大小，容量大小一旦指定就不可改变。
     */
    @Test
    void testArrayBlockingQueue() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3));
        System.out.println("ThreadPool Created");
        System.out.println("ThreadPool start create");
        for (int i = 1; i <= 20; i++) {
            threadPoolExecutor.execute(new TestRunnable(i));
            System.out.println(threadPoolExecutor.toString() + "  " + i);
            Thread.sleep(100);
        }
        // 0. 创建20个任务
        // 1. 任务 1 2 3 创建时，创建3个核心线程，并执行任务
        // 2. 任务 4 5 6 创建时，加入队列
        // 3. 任务 7 8 9 创建时，发现未达到最大线程数，创建线程并执行任务
        // 4. 任务 10 - 20 创建时，线程池和队列均满载，使用默认handler，抛出异常RejectedExecutionException
        // 5. 任务 1 2 3 运行完，队列内的 4 5 6 被取出执行
        // 6. 任务 7 8 9 4 5 6 执行完
        System.out.println("Thread end create");
    }

    /**
     * LinkedBlockingQueue<br>
     * 一个有边界的阻塞队列，它的内部实现是一个链表<br>
     * 初始化时，需要指定LinkedBlockingQueue队列大小，若不指定，则使用Integer.MAX_VALUE作为容量值<br>
     * 千万不要不指定，否则JVM内存会被撑爆！
     */
    @Test
    void testLinkedBlockingQueue() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3));
        System.out.println("ThreadPool Created");
        System.out.println("ThreadPool start create");
        for (int i = 1; i <= 20; i++) {
            threadPoolExecutor.execute(new TestRunnable(i));
            System.out.println(threadPoolExecutor.toString() + "  " + i);
            Thread.sleep(100);
        }
        // 0. 创建20个任务
        // 1. 任务 1 2 3 创建时，创建3个核心线程，并执行任务
        // 2. 任务 4 5 6 创建时，加入队列
        // 3. 任务 7 8 9 创建时，发现未达到最大线程数，创建线程并执行任务
        // 4. 任务 10 - 20 创建时，线程池和队列均满载，使用默认handler，抛出异常RejectedExecutionException
        // 5. 任务 1 2 3 运行完，队列内的 4 5 6 被取出执行
        // 6. 任务 7 8 9 4 5 6 执行完
        System.out.println("Thread end create");
    }

    /**
     * 线程池和队列已满的情况下<br>
     * ThreadPoolExecutor.AbortPolicy 处理策略（默认）<br>
     * 处理程序遭到拒绝将抛出运行时 RejectedExecutionException
     */
    @Test
    void testAbortPolicy() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(3), new ThreadPoolExecutor.AbortPolicy());
        System.out.println("ThreadPool Created");
        System.out.println("ThreadPool start create");
        for (int i = 1; i <= 20; i++) {
            threadPoolExecutor.execute(new TestRunnable(i));
            System.out.println(threadPoolExecutor.toString());
            Thread.sleep(100);
        }
        // 0. 创建20个任务
        // 1. 任务 1 2 3 创建时，创建3个核心线程，并执行任务
        // 2. 任务 4 5 6 创建时，加入队列
        // 3. 任务 7 8 9 创建时，发现未达到最大线程数，创建线程并执行任务
        // 4. 任务 10 创建时，线程池和队列均满载，抛出运行时 RejectedExecutionException，当前for循环结束
        System.out.println("Thread end create");
    }

    /**
     * 线程池和队列已满的情况下<br>
     * ThreadPoolExecutor.CallerRunsPolicy 处理策略<br>
     * 使用调用者本身的线程执行该任务。<br>
     * 此策略提供简单的反馈控制机制，能够减缓新任务的提交速度。
     */
    @Test
    void testCallerRunsPolicy() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(3), new ThreadPoolExecutor.CallerRunsPolicy());
        System.out.println("ThreadPool Created");
        System.out.println("ThreadPool start create");
        for (int i = 1; i <= 20; i++) {
            threadPoolExecutor.execute(new TestRunnable(i));
            System.out.println(threadPoolExecutor.toString() + "  " + i);
            Thread.sleep(100);
        }
        // 0. 创建20个任务
        // 1. 任务 1 2 3 创建时，创建3个核心线程，并执行任务
        // 2. 任务 4 5 6 创建时，加入队列
        // 3. 任务 7 8 9 创建时，发现未达到最大线程数，创建线程并执行任务
        // 4. 任务 10 创建时，线程池和队列均满载，使用调用者本身的线程执行该任务
        // 5. 任务 1 2 3 运行完，队列内的 4 5 6 执行
        // 6. 任务 7 8 9 执行完
        // 7. 任务 10 执行完，继续创建任务 11 12 13，加入队列，因为 active threads < pool size，任务立即被执行
        // 8. 继续创建任务 14 15 16，并加入队列
        // 9. 任务 17 创建时，线程池和队列均满载，使用调用者本身的线程执行该任务
        // 10. 任务 4 5 6 执行结束，队列中的 14 15 16 执行
        // 11. 任务 11 12 13 执行完
        // 12. 任务 17 执行完，继续创建任务 18 19 20 并执行，任务创建完毕
        // 13. 任务 14 15 16 18 19 20 执行完毕
        System.out.println("Thread end create");
    }

    /**
     * 线程池和队列已满的情况下<br>
     * ThreadPoolExecutor.DiscardPolicy 处理策略<br>
     * 不能执行的任务将被删除
     */
    @Test
    void testDiscardPolicy() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(3), new ThreadPoolExecutor.DiscardPolicy());
        System.out.println("ThreadPool Created");
        System.out.println("ThreadPool start create");
        for (int i = 1; i <= 20; i++) {
            threadPoolExecutor.execute(new TestRunnable(i));
            System.out.println(threadPoolExecutor.toString() + "  " + i);
            Thread.sleep(100);
        }
        // 0. 创建20个任务
        // 1. 任务 1 2 3 创建时，创建3个核心线程，并执行任务
        // 2. 任务 4 5 6 创建时，加入队列
        // 3. 任务 7 8 9 创建时，发现未达到最大线程数，创建线程并执行任务
        // 4. 任务 10 - 20 创建时，线程池和队列均满载，不能执行任务，当前任务被删除，后续任务也被删除，任务创建完毕
        // 5. 任务 1 2 3 运行完，队列内的 4 5 6 执行
        // 6. 任务 7 8 9 4 5 6 执行完
        System.out.println("Thread end create");
    }

    /**
     * 线程池和队列已满的情况下<br>
     * ThreadPoolExecutor.DiscardOldestPolicy 处理策略<br>
     * 如果执行程序尚未关闭，则队列头部的任务将被删除，并在队列尾部添加当前任务
     */
    @Test
    void testDiscardOldestPolicy() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(3), new ThreadPoolExecutor.DiscardOldestPolicy());
        System.out.println("ThreadPool Created");
        System.out.println("ThreadPool start create");
        for (int i = 1; i <= 20; i++) {
            threadPoolExecutor.execute(new TestRunnable(i));
            System.out.println(threadPoolExecutor.toString() + "  " + i);
            Thread.sleep(100);
        }
        // 0. 创建20个任务
        // 1. 任务 1 2 3 创建时，创建3个核心线程，并执行任务
        // 2. 任务 4 5 6 创建时，加入队列
        // 3. 任务 7 8 9 创建时，发现未达到最大线程数，创建线程并执行任务
        // 4. 任务 10 创建时，线程池和队列均满载，不能执行任务，则删除队列内的第一个任务，并添加当前任务，完成后队列内的任务变成 5 6 10
        // 5. 任务 11 - 20 创建时，同上，队列任务变成 18 19 20
        // 5. 任务 1 2 3 运行完，队列内的 18 19 20 执行
        // 6. 任务 7 8 9 18 19 20 执行完
        System.out.println("Thread end create");
    }

    /**
     * 线程池和队列已满的情况下<br>
     * CustomPolicy 自定义处理策略<br>
     * 如果执行程序尚未关闭，则队列头部的任务将被删除，并在队列尾部添加当前任务
     */
    @Test
    void testCustomPolicy() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3), new CustomPolicy());
        System.out.println("ThreadPool Created");
        System.out.println("ThreadPool start create");
        for (int i = 1; i <= 20; i++) {
            threadPoolExecutor.execute(new TestRunnable(i));
            System.out.println(threadPoolExecutor.toString() + "  " + i);
            Thread.sleep(100);
        }
        // 0. 创建20个任务
        // 1. 任务 1 2 3 创建时，创建3个核心线程，并执行任务
        // 2. 任务 4 5 6 创建时，加入队列
        // 3. 任务 7 8 9 创建时，发现未达到最大线程数，创建线程并执行任务
        // 4. 任务 10 - 20 创建时，线程池和队列均满载，不能执行任务，当前任务被删除，后续任务也被删除，任务创建完毕
        // 5. 任务 1 2 3 运行完，队列内的 4 5 6 执行
        // 6. 任务 7 8 9 4 5 6 执行完
        System.out.println("Thread end create");
    }

    /**
     * 自定义线程工厂<br>
     * 这里省略掉RejectedExecutionHandler handler参数，使用默认handler，即：AbortPolicy
     */
    @Test
    void testCustomThreadFactory() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3), new CustomThreadFactory());
        System.out.println("ThreadPool Created");
        System.out.println("ThreadPool start create");
        for (int i = 1; i <= 20; i++) {
            threadPoolExecutor.execute(new TestRunnable(i));
            System.out.println(threadPoolExecutor.toString() + "  " + i);
            Thread.sleep(100);
        }
        // 0. 创建20个任务
        // 1. 任务 1 2 3 创建时，创建3个核心线程，并执行任务，创建线程时发现线程工厂内的输出被执行了
        // 2. 任务 4 5 6 创建时，加入队列
        // 3. 任务 7 8 9 创建时，发现未达到最大线程数，创建线程并执行任务，创建线程时发现线程工厂内的输出被执行了
        // 4. 任务 10 - 20 创建时，线程池和队列均满载，使用默认handler，抛出异常RejectedExecutionException
        // 5. 任务 1 2 3 运行完，队列内的 4 5 6 执行
        // 6. 任务 7 8 9 4 5 6 执行完
        System.out.println("Thread end create");
    }
}
