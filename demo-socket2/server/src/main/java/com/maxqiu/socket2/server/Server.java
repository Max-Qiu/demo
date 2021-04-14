package com.maxqiu.socket2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.maxqiu.socket2.common.constants.TCPConstants;
import com.maxqiu.socket2.common.core.IoContext;
import com.maxqiu.socket2.common.impl.IoSelectorProvider;

/**
 * 服务端主程序入口
 * 
 * @author max.qiu
 */
public class Server {
    public static void main(String[] args) throws IOException {
        IoContext.setup().ioProvider(new IoSelectorProvider()).start();

        // 开启服务端TCP
        TCPServer tcpServer = new TCPServer(TCPConstants.PORT_SERVER);
        boolean isSucceed = tcpServer.start();
        if (!isSucceed) {
            System.out.println("服务端TCP启动失败");
            return;
        }
        // 开启服务端UDP
        UDPProvider.start(TCPConstants.PORT_SERVER);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String str;
        do {
            str = bufferedReader.readLine();
            tcpServer.broadcast(str);
        } while (!"00bye00".equals(str));

        UDPProvider.stop();
        tcpServer.stop();

        IoContext.close();
    }
}
