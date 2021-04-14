package com.maxqiu.socket.udp.demo2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.UUID;

/**
 * Socket网络编程示例——UDP协议——接收端
 * 
 * @author max.qiu
 */
public class UdpReceiver {

    public static void main(String[] args) throws IOException {
        // 生成一份接收端的唯一标示
        String id = UUID.randomUUID().toString();
        System.out.println("接收端已启动ID：" + id);

        // 启用线程循环接收数据
        Receiver receiver = new Receiver(id);
        receiver.start();

        // 读取任意键盘信息后退出程序
        // noinspection ResultOfMethodCallIgnored
        System.in.read();
        receiver.exit();

    }

    private static class Receiver extends Thread {
        private final String id;
        private boolean done = false;
        private DatagramSocket socket = null;

        public Receiver(String id) {
            super();
            this.id = id;
        }

        @Override
        public void run() {
            try {
                // 监听20000 端口
                socket = new DatagramSocket(20000);

                System.out.println("本地接收端IP：" + socket.getLocalAddress().getHostAddress());
                System.out.println("本地接收端端口：" + socket.getLocalPort());

                while (!done) {
                    // 构建接收实体
                    final byte[] receiveBytes = new byte[512];
                    DatagramPacket receivePacket = new DatagramPacket(receiveBytes, receiveBytes.length);

                    // 接收
                    socket.receive(receivePacket);

                    // 打印接收到的数据与对方的信息
                    String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("接收的数据为：" + data);
                    System.out.println("发送端的IP为：" + receivePacket.getAddress().getHostAddress());
                    System.out.println("发送端的端口为：" + receivePacket.getPort());

                    // 解析端口号
                    int responsePort = MessageCreator.parsePort(data);
                    if (responsePort != -1) {
                        // 构建一份回送数据
                        String responseData = MessageCreator.buildWithId(id);
                        byte[] responseDataBytes = responseData.getBytes();
                        // 直接根据发送者构建一份回送信息
                        DatagramPacket responsePacket = new DatagramPacket(responseDataBytes, responseDataBytes.length,
                            receivePacket.getAddress(), responsePort);
                        socket.send(responsePacket);
                    }
                }

            } catch (Exception ignored) {
            } finally {
                close();
            }

            // 完成
            System.out.println("UDPProvider Finished.");
        }

        private void close() {
            if (socket != null) {
                socket.close();
                socket = null;
            }
        }

        /**
         * 提供结束
         */
        void exit() {
            done = true;
            close();
        }

    }
}
