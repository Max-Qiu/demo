package com.maxqiu.demo.ABC;

/**
 * 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍，要求输出的结果必须按顺序显示。 * 如：ABCABCABC…… 依次递归
 * 
 * @author Max_Qiu
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class TestABCAlternate {
    public static void main(String[] args) {
        Alternate alternate = new Alternate();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                alternate.loopA(i);
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                alternate.loopB(i);
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                alternate.loopC(i);
                System.out.println("-----------------------");
            }
        }, "C").start();
    }
}
