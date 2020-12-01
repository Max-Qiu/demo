package com.maxqiu.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author Max_Qiu
 */
public class CreateClient {
    public static RestHighLevelClient createClient() {
        /// 多节点
        // RestHighLevelClient client = new RestHighLevelClient(
        // RestClient.builder(
        // new HttpHost("localhost", 9200, "http"),
        // new HttpHost("localhost", 9201, "http")));
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("192.168.220.101", 9200, "http"));

        /// 设置连接密码
        // CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        // credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "123456"));
        // restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
        // httpClientBuilder.disableAuthCaching();
        // httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
        // return httpClientBuilder;
        // });

        return new RestHighLevelClient(restClientBuilder);
    }
}
