package com.maxqiu.socket.tcp.demo2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;

/**
 * Socket网络编程示例--TCP协议--客户端--详细示例
 *
 * @author max.qiu
 */
public class TcpClient {
    /**
     * 服务器端口
     */
    private static final int SERVER_PORT = 20000;

    /**
     * 本地端口
     */
    private static final int LOCAL_PORT = 20001;

    public static void main(String[] args) throws IOException {
        Socket socket = createSocket();

        initSocket(socket);

        // 连接到本地20000端口，超时时间3秒，超过则抛出超时异常
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), SERVER_PORT), 3000);

        System.out.println("已连接服务器");
        System.out.println("客户端信息：" + socket.getLocalAddress().getHostAddress() + " P:" + socket.getLocalPort());
        System.out.println("服务器信息：" + socket.getInetAddress().getHostAddress() + " P:" + socket.getPort());

        try {
            // 发送接收数据
            todo(socket);
        } catch (Exception e) {
            System.out.println("异常关闭");
        }

        // 释放资源
        socket.close();
        System.out.println("客户端已退出～");

    }

    /**
     * 创建socket示例
     * 
     * @return socket
     */
    private static Socket createSocket() throws IOException {
        // 创建一个socket
        Socket socket = new Socket();
        // 绑定到本地20001端口，客户端一般无需绑定
        // socket.bind(new InetSocketAddress(Inet4Address.getLocalHost(), LOCAL_PORT));

        // 无代理模式，等效于空构造函数
        // Socket socket = new Socket(Proxy.NO_PROXY);

        // 新建一份具有HTTP代理的socket，传输数据将通过127.0.0.1:8080端口转发
        // Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(Inet4Address.getByName("127.0.0.1"), 8800));
        // Socket socket = new Socket(proxy);

        // 新建一个socket，并连接到本地20000的服务器上
        // Socket socket = new Socket("localhost", SERVER_PORT);
        // Socket socket = new Socket(Inet4Address.getLocalHost(), SERVER_PORT);

        // 新建一个socket，并连接到本地20000的服务器上，且本地绑定到20001端口
        // Socket socket = new Socket("localhost", SERVER_PORT, Inet4Address.getLocalHost(), LOCAL_PORT);
        // Socket socket = new Socket(Inet4Address.getLocalHost(), SERVER_PORT, Inet4Address.getLocalHost(),
        // LOCAL_PORT);

        return socket;
    }

    /**
     * 设置参数
     */
    private static void initSocket(Socket socket) throws SocketException {
        // 设置读取超时时间为2秒
        socket.setSoTimeout(2000);

        // 是否复用未完全关闭的socket地址，对于指定bind操作后的socket有效
        socket.setReuseAddress(true);

        // 是否开启Nagle算法
        socket.setTcpNoDelay(true);

        // 是否需要在长时无数据响应时发送确认数据（类似心跳包），时间大约为2小时
        socket.setKeepAlive(true);

        // 对于close关闭操作行为进行怎样的处理；默认为false，0
        // false、0：默认情况，关闭时立即返回，底层系统接管输出流，将缓冲区内的数据发送完成
        // true、0：关闭时立即返回，缓冲区数据抛弃，直接发送RST结束命令到对方，并无需经过2MSL等待
        // true、200：关闭时最长阻塞200毫秒，随后按第二情况处理
        socket.setSoLinger(true, 20);

        // 是否让紧急数据内敛，默认false；紧急数据通过 socket.sendUrgentData(1);发送
        socket.setOOBInline(true);

        // 设置接收发送缓冲器大小
        socket.setReceiveBufferSize(64 * 1024 * 1024);
        socket.setSendBufferSize(64 * 1024 * 1024);

        // 设置性能参数：短链接，延迟，带宽的相对重要性
        socket.setPerformancePreferences(1, 1, 0);
    }

    /**
     * 发送与接收消息
     */
    private static void todo(Socket client) throws IOException {
        // 得到Socket输出流
        OutputStream outputStream = client.getOutputStream();

        // 得到Socket输入流
        InputStream inputStream = client.getInputStream();
        byte[] buffer = new byte[256];
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

        // byte
        byteBuffer.put((byte)126);

        // char
        char c = 'a';
        byteBuffer.putChar(c);

        // int
        int i = 2323123;
        byteBuffer.putInt(i);

        // bool
        boolean b = true;
        byteBuffer.put(b ? (byte)1 : (byte)0);

        // Long
        long l = 298789739;
        byteBuffer.putLong(l);

        // float
        float f = 12.345f;
        byteBuffer.putFloat(f);

        // double
        double d = 13.31241248782973;
        byteBuffer.putDouble(d);

        // String
        String str = "Hello你好！";
        byteBuffer.put(str.getBytes());

        // 发送到服务器
        outputStream.write(buffer, 0, byteBuffer.position() + 1);

        // 接收服务器返回
        int read = inputStream.read(buffer);
        System.out.println("收到返回数据：" + read);

        // 资源释放
        outputStream.close();
        inputStream.close();
    }
}
