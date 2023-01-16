package com.maxqiu.demo;

import java.io.IOException;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.alibaba.fastjson.JSON;
import com.maxqiu.demo.common.ClientUtils;
import com.maxqiu.demo.entity.User;

/**
 * @author Max_Qiu
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DocumentApi {
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
     * 插入数据
     */
    @Test
    @Order(1)
    public void insert() throws Exception {
        // 插入数据
        IndexRequest request = new IndexRequest();
        // 指定索引和ID，若索引不存在，则自动创建
        request.index("user").id("1001");
        User user = new User();
        user.setName("Tom");
        user.setAge(30);
        user.setSex("男");
        // 向ES插入数据，必须将数据转换位JSON格式
        String userJson = JSON.toJSONString(user);
        request.source(userJson, XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getResult());
    }

    /**
     * 检查文档是否存在
     */
    @Test
    @Order(2)
    public void exists() throws IOException {
        // 创建一个 GetRequest 请求，设置索引为 posts ，文档ID 为 1
        GetRequest request = new GetRequest("user", "1001");
        // 查询
        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println("是否存在：" + exists);
    }

    @Test
    @Order(3)
    public void bulkInsert() throws Exception {
        // 批量插入数据
        BulkRequest bulkRequest = new BulkRequest();

        bulkRequest.add(new IndexRequest().index("user").id("1001").source(XContentType.JSON, "name", "zhangsan", "age",
            30, "sex", "男"));
        bulkRequest.add(new IndexRequest().index("user").id("1002").source(XContentType.JSON, "name", "lisi", "age", 30,
            "sex", "女"));
        bulkRequest.add(new IndexRequest().index("user").id("1003").source(XContentType.JSON, "name", "wangwu", "age",
            40, "sex", "男"));
        bulkRequest.add(new IndexRequest().index("user").id("1004").source(XContentType.JSON, "name", "wangwu1", "age",
            40, "sex", "女"));
        bulkRequest.add(new IndexRequest().index("user").id("1005").source(XContentType.JSON, "name", "wangwu2", "age",
            50, "sex", "男"));
        bulkRequest.add(new IndexRequest().index("user").id("1006").source(XContentType.JSON, "name", "wangwu3", "age",
            50, "sex", "男"));
        bulkRequest.add(new IndexRequest().index("user").id("1007").source(XContentType.JSON, "name", "wangwu44", "age",
            60, "sex", "男"));
        bulkRequest.add(new IndexRequest().index("user").id("1008").source(XContentType.JSON, "name", "wangwu555",
            "age", 60, "sex", "男"));
        bulkRequest.add(new IndexRequest().index("user").id("1009").source(XContentType.JSON, "name", "wangwu66666",
            "age", 60, "sex", "男"));
        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(response.getTook());
    }

    @Test
    @Order(4)
    public void update() throws Exception {
        // 修改数据
        UpdateRequest request = new UpdateRequest();
        request.index("user").id("1001");
        request.doc(XContentType.JSON, "sex", "女");
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response.getResult());
    }

    @Test
    @Order(5)
    public void get() throws Exception {
        // 查询数据
        GetRequest request = new GetRequest();
        request.index("user").id("1001");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
    }

    @Test
    @Order(6)
    public void delete() throws Exception {
        DeleteRequest request = new DeleteRequest();
        request.index("user").id("1001");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }

    @Test
    @Order(7)
    public void bulkDelete() throws Exception {
        // 批量删除数据
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest().index("user").id("1001"));
        request.add(new DeleteRequest().index("user").id("1002"));
        request.add(new DeleteRequest().index("user").id("1003"));
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.getTook());
    }
}
