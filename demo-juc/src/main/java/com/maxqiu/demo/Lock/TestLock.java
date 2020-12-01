package com.maxqiu.demo.Lock;

/**
 * @author Max_Qiu
 */
public class TestLock {
    public static void main(String[] args) {
        TicketRunnable ticketRunnable = new TicketRunnable();

        new Thread(ticketRunnable, "1号窗口").start();
        new Thread(ticketRunnable, "2号窗口").start();
        new Thread(ticketRunnable, "3号窗口").start();
    }
}
