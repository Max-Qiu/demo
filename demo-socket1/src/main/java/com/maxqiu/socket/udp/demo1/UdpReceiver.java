package com.maxqiu.socket.udp.demo1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Socket网络编程示例——UDP协议——接收端
 * 
 * @author max.qiu
 */
public class UdpReceiver {
    public static void main(String[] args) throws IOException {
        System.out.println("接收端已启动");

        // 创建一个UDP协议的socket，作为接收端，应绑定指定端口，默认监听本地0.0.0.0
        // 注：若不传入端口参数，则会绑定随机端口
        DatagramSocket socket = new DatagramSocket(20000);

        // 创建一个UDP协议的socket，作为接收端，应绑定指定端口，传入网口参数可以绑定指定网卡
        // DatagramSocket socket = new DatagramSocket(20000, InetAddress.getByName("192.168.101.166"));

        // socket在创建时已绑定端口和网卡，此时无法使用bind命令更换
        // socket.bind(new InetSocketAddress(20000));

        System.out.println("本地接收端IP：" + socket.getLocalAddress().getHostAddress());
        System.out.println("本地接收端端口：" + socket.getLocalPort());

        // 创建一个数据接收包
        byte[] receiveBytes = new byte[512];
        DatagramPacket receivePacket = new DatagramPacket(receiveBytes, receiveBytes.length);
        // 作为接收端，先接收数据
        socket.receive(receivePacket);

        // 打印接收到的数据与对方的信息
        System.out.println("接收的数据为：" + new String(receivePacket.getData()));
        System.out.println("发送端的IP为：" + receivePacket.getAddress().getHostAddress());
        System.out.println("发送端的端口为：" + receivePacket.getPort());

        // 创建一个数据返回包
        String returnString = "UdpReceiver";
        DatagramPacket returnPacket = new DatagramPacket(returnString.getBytes(), returnString.length(),
            receivePacket.getAddress(), receivePacket.getPort());
        // 发送回送数据
        socket.send(returnPacket);

        // 关闭socket
        socket.close();
    }
}
