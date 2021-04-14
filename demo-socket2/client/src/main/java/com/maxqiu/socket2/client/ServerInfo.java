package com.maxqiu.socket2.client;

import lombok.Data;

/**
 * 服务端信息
 */
@Data
public class ServerInfo {
    private String sn;
    private int port;
    private String address;

    public ServerInfo(int port, String ip, String sn) {
        this.port = port;
        this.address = ip;
        this.sn = sn;
    }

}
