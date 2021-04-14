package com.maxqiu.demo.miscellaneous;

import java.io.IOException;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;

import com.maxqiu.demo.CreateClient;

/**
 * 
 * 获取群集信息
 * 
 * @author Max_Qiu
 */
public class InfoApi {

    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            info();
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
    private static void info() throws IOException {
        MainResponse response = CLIENT.info(RequestOptions.DEFAULT);
        System.out.println(response);
        String clusterName = response.getClusterName();
        String clusterUuid = response.getClusterUuid();
        String nodeName = response.getNodeName();
        MainResponse.Version version = response.getVersion();
        String buildDate = version.getBuildDate();
        String buildFlavor = version.getBuildFlavor();
        String buildHash = version.getBuildHash();
        String buildType = version.getBuildType();
        String luceneVersion = version.getLuceneVersion();
        String minimumIndexCompatibilityVersion = version.getMinimumIndexCompatibilityVersion();
        String minimumWireCompatibilityVersion = version.getMinimumWireCompatibilityVersion();
        String number = version.getNumber();
    }
}
