package com.maxqiu.demo.ForkJoin;

import java.util.concurrent.RecursiveTask;

/**
 * @author Max_Qiu
 */
public class ForkJoinSumCalculate extends RecursiveTask<Long> {

    private static final long serialVersionUID = -259195479995561737L;

    private long start;
    private long end;

    // 临界值
    private static final long THRESHOLD = 10000L;

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;

        if (length <= THRESHOLD) {
            long sum = 0L;

            for (long i = start; i <= end; i++) {
                sum += i;
            }

            return sum;
        } else {
            long middle = (start + end) / 2;

            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            left.fork(); // 进行拆分，同时压入线程队列

            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end);
            right.fork(); //

            return left.join() + right.join();
        }
    }

}
