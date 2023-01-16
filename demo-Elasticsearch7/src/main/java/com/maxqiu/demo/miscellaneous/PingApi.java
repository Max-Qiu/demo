package com.maxqiu.demo.miscellaneous;

import java.io.IOException;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import com.maxqiu.demo.common.ClientUtils;

/**
 * 连通测试
 *
 * @author Max_Qiu
 */
public class PingApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
    }

    public static void main(String[] args) {
        try {
            ping();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 关闭客户端
        try {
            CLIENT.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取群集信息
     */
    private static void ping() throws IOException {
        boolean response = CLIENT.ping(RequestOptions.DEFAULT);
        System.out.println(response);
    }
}
