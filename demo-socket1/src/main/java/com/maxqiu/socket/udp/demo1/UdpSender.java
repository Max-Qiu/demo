package com.maxqiu.socket.udp.demo1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Socket网络编程示例——UDP协议——发送端——单点发送
 * 
 * @author max.qiu
 */
public class UdpSender {
    public static void main(String[] args) throws IOException {
        System.out.println("发送端已启动");

        // 创建一个UDP协议的socket，作为发送端，无需绑定指定端口
        DatagramSocket socket = new DatagramSocket();

        // 创建一个UDP协议的socket，作为发送端，也可以固定端口
        // DatagramSocket socket = new DatagramSocket(20001);

        System.out.println("本地发送端IP：" + socket.getLocalAddress().getHostAddress());
        System.out.println("本地发送端端口：" + socket.getLocalPort());

        // 构建一份请求数据
        String requestData = "UdpSender";
        byte[] requestDataBytes = requestData.getBytes();
        // 创建一个数据接请求
        DatagramPacket requestPacket = new DatagramPacket(requestDataBytes, requestDataBytes.length);
        // 设置发送的端口
        requestPacket.setAddress(InetAddress.getLocalHost());
        requestPacket.setPort(20000);
        // 接收数据
        socket.send(requestPacket);

        // 构建接收实体
        byte[] returnByte = new byte[512];
        DatagramPacket returnPack = new DatagramPacket(returnByte, returnByte.length);

        // 打印接收到的数据与对方的信息
        socket.receive(returnPack);
        System.out.println("接收的数据为：" + new String(returnPack.getData()));
        System.out.println("接收端的IP为：" + returnPack.getAddress().getHostAddress());
        System.out.println("接收端的端口为：" + returnPack.getPort());

        socket.close();
    }
}
