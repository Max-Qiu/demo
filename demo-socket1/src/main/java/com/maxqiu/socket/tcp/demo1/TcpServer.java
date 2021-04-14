package com.maxqiu.socket.tcp.demo1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket网络编程示例--TCP协议--服务端--简单示例
 * 
 * @author max.qiu
 */
public class TcpServer {
    public static void main(String[] args) throws IOException {
        // 创建一个服务端
        ServerSocket server = new ServerSocket();
        // 绑定端口，默认监听本地0.0.0.0
        server.bind(new InetSocketAddress(20000));

        System.out.println("服务器准备就绪");
        System.out.println("服务器信息：" + server.getInetAddress().getHostAddress() + " P:" + server.getLocalPort());

        // 监听一个客户端的连接
        Socket socket = server.accept();

        System.out.println("客户端的IP为：" + socket.getInetAddress().getHostAddress());
        System.out.println("客户端的端口为：" + socket.getPort());

        // 接收消息
        InputStream inputStream = socket.getInputStream();
        int read = inputStream.read();

        // 打印接收到的数据与对方的信息
        System.out.println("接收的数据为：" + read);

        // 发送回送消息
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(0);
        outputStream.flush();

        // 关闭流
        inputStream.close();
        outputStream.close();

        // 关闭连接
        socket.close();

        // 关闭服务端
        server.close();
    }
}
