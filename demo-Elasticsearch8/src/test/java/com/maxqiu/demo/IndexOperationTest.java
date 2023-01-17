package com.maxqiu.demo;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.maxqiu.demo.common.ClientUtils;

import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexRequest;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;

/**
 * 索引的操作
 *
 * @author Max_Qiu
 */
public class IndexOperationTest {
    @BeforeAll
    static void beforeAll() {
        ClientUtils.clientSslWithCaFingerprint();
        // ClientUtils.clientSslWithCaFile();
    }

    @Test
    void normal() throws IOException {
        // 创建索引
        CreateIndexRequest request = new CreateIndexRequest.Builder().index("myindex").build();
        CreateIndexResponse createIndexResponse = ClientUtils.client.indices().create(request);
        System.out.println("创建索引成功：" + createIndexResponse.acknowledged());
        // 查询索引
        GetIndexRequest getIndexRequest = new GetIndexRequest.Builder().index("myindex").build();
        GetIndexResponse getIndexResponse = ClientUtils.client.indices().get(getIndexRequest);
        System.out.println("索引查询成功：" + getIndexResponse.result());
        // 删除索引
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest.Builder().index("myindex").build();
        DeleteIndexResponse delete = ClientUtils.client.indices().delete(deleteIndexRequest);
        boolean acknowledged = delete.acknowledged();
        System.out.println("删除索引成功：" + acknowledged);
    }

    @Test
    void lambda() throws IOException {
        // 创建索引
        boolean createFlag = ClientUtils.client.indices().create(p -> p.index("myindex1")).acknowledged();
        System.out.println("创建索引结果：" + createFlag);
        // 获取索引
        System.out.println(ClientUtils.client.indices().get(req -> req.index("myindex1")).result());
        // 删除索引
        boolean deleteFlag = ClientUtils.client.indices().delete(reqbuilder -> reqbuilder.index("myindex1")).acknowledged();
        System.out.println("删除索引结果：" + deleteFlag);
    }

    @AfterAll
    static void afterAll() {
        ClientUtils.close();
    }
}
