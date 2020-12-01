package com.maxqiu.demo.Atomic;

/**
 * 一、i++ 的原子性问题：i++ 的操作实际上分为三个步骤“读-改-写”<br>
 * int i = 10;<br>
 * i = i++; //10<br>
 * <br>
 * int temp = i;<br>
 * i = i + 1;<br>
 * i = temp;<br>
 * <br>
 * 二、原子变量：<br>
 * 在 java.util.concurrent.atomic 包下提供了一些原子变量。<br>
 * 1. volatile 保证内存可见性 <br>
 * 2. CAS(Compare-And-Swap)算法保证数据变量的原子性<br>
 * CAS 算法是硬件对于并发操作的支持<br>
 * CAS 包含了三个操作数：<br>
 * ①内存值 V<br>
 * ②预估值 A<br>
 * ③更新值 B<br>
 * 当且仅当 V == A 时， V = B; 否则，不会执行任何操作。
 * 
 * @author Max_Qiu
 */
public class TestAtomicDemo {

    public static void main(String[] args) {
        int i = 10;
        i = i++;
        System.out.println(i);

        AtomicRunnable atomicDemo = new AtomicRunnable();

        for (int z = 0; z < 10; z++) {
            new Thread(atomicDemo).start();
        }
    }

}