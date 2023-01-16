package com.maxqiu.demo;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.maxqiu.demo.common.ClientUtils;

/**
 * @author Max_Qiu
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IndexApi {
    private static RestHighLevelClient client;

    @BeforeAll
    public static void before() {
        client = ClientUtils.create();
    }

    @AfterAll
    public static void after() {
        ClientUtils.close(client);
    }

    /**
     * 创建索引
     */
    @Test
    @Order(1)
    public void create() throws Exception {
        // 创建索引
        CreateIndexRequest request = new CreateIndexRequest("user");
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        // 响应状态
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println("索引创建 ：" + acknowledged);
    }

    /**
     * 获取指定索引
     */
    @Test
    @Order(2)
    public void get() throws Exception {
        // 查询索引
        GetIndexRequest request = new GetIndexRequest("user");
        GetIndexResponse getIndexResponse = client.indices().get(request, RequestOptions.DEFAULT);
        // 响应状态
        System.out.println(getIndexResponse.getAliases());
        System.out.println(getIndexResponse.getMappings());
        System.out.println(getIndexResponse.getSettings());
    }

    /**
     * 删除索引
     */
    @Test
    @Order(3)
    public void delete() throws Exception {
        // 查询索引
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        // 响应状态
        System.out.println("索引删除 ：" + response.isAcknowledged());
    }

    /**
     * 索引是否存在
     */
    @Test
    @Order(4)
    public void exists() throws IOException {
        // 创建一个获取索引请求
        GetIndexRequest request = new GetIndexRequest("user");
        // 请求
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }
}
