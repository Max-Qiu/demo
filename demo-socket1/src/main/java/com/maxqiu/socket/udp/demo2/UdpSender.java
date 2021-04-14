package com.maxqiu.socket.udp.demo2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Socket网络编程示例——UDP协议——发送端——多端发送
 * 
 * @author max.qiu
 */
public class UdpSender {
    private static final int LISTEN_PORT = 30000;

    public static void main(String[] args) throws IOException {
        System.out.println("UDP发送端开始启动");

        // 启动一个数据监听
        Listener listener = listen();

        // 发送一个广播数据，告诉接收者，数据回送至30000端口
        sendBroadcast();

        // 读取任意键盘信息后退出数据监听并打印回送的数据
        // noinspection ResultOfMethodCallIgnored
        System.in.read();

        List<Device> devices = listener.getDevicesAndClose();

        for (Device device : devices) {
            System.out.println("Device:" + device.toString());
        }

        // 完成
        System.out.println("UDP发送端结束");
    }

    /**
     * 开启监听
     */
    private static Listener listen() {
        System.out.println("UDP发送端的回送数据监听已启动");
        Listener listener = new Listener(LISTEN_PORT);
        listener.start();
        return listener;
    }

    /**
     * 广播数据
     */
    private static void sendBroadcast() throws IOException {
        System.out.println("UDP发送端广播数据开始发送");

        // 作为搜索方，让系统自动分配端口
        DatagramSocket datagramSocket = new DatagramSocket();

        // 构建一份请求数据
        String requestData = MessageCreator.buildWithPort(LISTEN_PORT);
        byte[] requestDataBytes = requestData.getBytes();
        // 直接构建packet
        DatagramPacket requestPacket = new DatagramPacket(requestDataBytes, requestDataBytes.length);
        // 20000端口, 广播地址
        requestPacket.setAddress(InetAddress.getByName("255.255.255.255"));
        requestPacket.setPort(20000);

        // 发送
        datagramSocket.send(requestPacket);
        datagramSocket.close();

        // 完成
        System.out.println("UDP发送端广播数据发送完成");
    }

    /**
     * 设备信息
     */
    private static class Device {
        final String ip;
        final int port;
        final String id;

        private Device(String ip, int port, String id) {
            this.ip = ip;
            this.port = port;
            this.id = id;
        }

        @Override
        public String toString() {
            return "Device{" + "ip='" + ip + '\'' + ", port=" + port + ", id='" + id + '\'' + '}';
        }
    }

    /**
     * 回送数据接收线程
     */
    private static class Listener extends Thread {
        private final int listenPort;
        private final List<Device> devices = new ArrayList<Device>();
        private boolean done = false;
        private DatagramSocket ds = null;

        public Listener(int listenPort) {
            super();
            this.listenPort = listenPort;
        }

        @Override
        public void run() {
            super.run();
            try {
                // 监听回送端口
                ds = new DatagramSocket(listenPort);

                while (!done) {
                    // 构建接收实体
                    final byte[] buf = new byte[512];
                    DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);

                    // 接收
                    ds.receive(receivePacket);

                    // 打印接收到的数据与对方的信息
                    String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    String ip = receivePacket.getAddress().getHostAddress();
                    int port = receivePacket.getPort();
                    System.out.println("接收的数据为：" + data);
                    System.out.println("发送端的IP为：" + ip);
                    System.out.println("发送端的端口为：" + port);
                    String id = MessageCreator.parseId(data);
                    if (id != null) {
                        Device device = new Device(ip, port, id);
                        devices.add(device);
                    }
                }
            } catch (Exception ignored) {

            } finally {
                close();
            }
            System.out.println("UDP发送端数据监听已结束");

        }

        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        List<Device> getDevicesAndClose() {
            done = true;
            close();
            return devices;
        }
    }
}
