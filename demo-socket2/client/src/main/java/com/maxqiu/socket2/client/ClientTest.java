package com.maxqiu.socket2.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author max.qiu
 */
public class ClientTest {
    private static boolean done;

    public static void main(String[] args) throws IOException {
        ServerInfo info = UDPSearcher.searchServer(10000);
        System.out.println("服务端信息：" + info);
        if (info == null) {
            return;
        }
        int size = 0;
        List<TCPClient> tcpClients = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            try {
                TCPClient tcpClient = TCPClient.startWith(info);
                if (tcpClient == null) {
                    System.out.println("连接异常");
                    continue;
                }

                tcpClients.add(tcpClient);

                System.out.println("连接成功：" + (++size));

            } catch (IOException e) {
                System.out.println("连接异常");
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.in.read();

        Runnable runnable = () -> {
            while (!done) {
                for (TCPClient tcpClient : tcpClients) {
                    tcpClient.send("Hello~~");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        System.in.read();

        // 等待线程完成
        done = true;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 客户端结束
        for (TCPClient tcpClient : tcpClients) {
            tcpClient.exit();
        }
    }
}
