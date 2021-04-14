package com.maxqiu.socket.tcp.demo1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Socket网络编程示例--TCP协议--客户端--简单示例
 * 
 * @author max.qiu
 */
public class TcpClient {
    public static void main(String[] args) throws IOException {
        // 创建一个socket
        Socket socket = new Socket();

        // 连接到指定端口
        socket.connect(new InetSocketAddress("127.0.0.1", 20000));

        System.out.println("已连接服务器");
        System.out.println("客户端信息：" + socket.getLocalAddress().getHostAddress() + " P:" + socket.getLocalPort());
        System.out.println("服务器信息：" + socket.getInetAddress().getHostAddress() + " P:" + socket.getPort());

        // 发送消息
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(1);
        outputStream.flush();

        // 接收回送消息
        InputStream inputStream = socket.getInputStream();
        int read = inputStream.read();
        System.out.println(read);

        // 关闭流
        outputStream.close();
        inputStream.close();

        // 关闭客户端
        socket.close();

    }
}
