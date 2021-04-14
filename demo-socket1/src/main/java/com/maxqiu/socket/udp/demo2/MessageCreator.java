package com.maxqiu.socket.udp.demo2;

/**
 * 消息
 * 
 * @author max.qiu
 */
public class MessageCreator {
    private static final String ID_HEADER = "收到暗号，我是接收端唯一ID:";
    private static final String PORT_HEADER = "这是暗号，回送数据端口是:";

    /**
     * 创建暗号
     */
    public static String buildWithPort(int port) {
        return PORT_HEADER + port;
    }

    /**
     * 解析暗号
     */
    public static int parsePort(String data) {
        if (data.startsWith(PORT_HEADER)) {
            return Integer.parseInt(data.substring(PORT_HEADER.length()));
        }
        return -1;
    }

    /**
     * 创建ID消息
     */
    public static String buildWithId(String sn) {
        return ID_HEADER + sn;
    }

    /**
     * 解析ID消息
     */
    public static String parseId(String data) {
        if (data.startsWith(ID_HEADER)) {
            return data.substring(ID_HEADER.length());
        }
        return null;
    }

}
