package com.maxqiu.demo.CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch ：闭锁，在完成某些运算是，只有其他所有线程的运算全部完成，当前运算才继续执行
 * 
 * @author Max_Qiu
 */
public class TestCountDownLatch {

    public static void main(String[] args) {
        int threadNum = 5;

        CountDownLatch latch = new CountDownLatch(threadNum);

        LatchRunnable ld = new LatchRunnable(latch);

        long start = System.currentTimeMillis();

        for (int i = 0; i < threadNum; i++) {
            new Thread(ld).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println("异常");
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("耗费时间为：" + (end - start));
    }

}
