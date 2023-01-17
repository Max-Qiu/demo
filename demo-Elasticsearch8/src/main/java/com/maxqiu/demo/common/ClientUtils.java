package com.maxqiu.demo.common;

import java.io.File;
import java.io.IOException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;

/**
 * @author Max_Qiu
 */
public class ClientUtils {
    private static ElasticsearchTransport transport;
    public static ElasticsearchClient client;
    public static ElasticsearchAsyncClient asyncClient;

    /**
     * 使用CA证书的指纹方式
     */
    public static void clientSslWithCaFingerprint() {
        // 使用证书指纹方式
        // 执行 openssl x509 -fingerprint -sha256 -noout -in ./config/certs/http_ca.crt 生成证书的指纹
        String fingerprint = "D0:AA:AD:25:56:03:AE:A0:15:64:80:2A:10:53:43:7F:A6:0B:4E:EF:0B:2B:D8:EE:1F:F5:DA:6B:E2:B5:16:5B";

        SSLContext sslContext = TransportUtils.sslContextFromCaFingerprint(fingerprint);

        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "hCnLTnd6EwzhaNJ7fpoL"));

        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200, "https"))
            .setHttpClientConfigCallback(hc -> hc.setSSLContext(sslContext).setDefaultCredentialsProvider(credsProv)).build();

        transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        client = new ElasticsearchClient(transport);
        asyncClient = new ElasticsearchAsyncClient(transport);
    }

    public static void clientSslWithCaFile() throws IOException {
        File certFile = new File("C:\\development\\elasticsearch\\config\\certs\\http_ca.crt");

        SSLContext sslContext = TransportUtils.sslContextFromHttpCaCrt(certFile);

        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "hCnLTnd6EwzhaNJ7fpoL"));

        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200, "https"))
            .setHttpClientConfigCallback(hc -> hc.setSSLContext(sslContext).setDefaultCredentialsProvider(credsProv)).build();

        transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        client = new ElasticsearchClient(transport);
        asyncClient = new ElasticsearchAsyncClient(transport);
    }

    public static void close() {
        try {
            transport.close();
        } catch (IOException e) {
            System.out.println("关闭异常：" + e);
        }
    }
}
