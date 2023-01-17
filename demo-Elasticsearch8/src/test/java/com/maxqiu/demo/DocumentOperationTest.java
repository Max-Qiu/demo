package com.maxqiu.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.maxqiu.demo.common.ClientUtils;
import com.maxqiu.demo.entity.User;

import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.CreateOperation;

/**
 * 索引的操作
 *
 * @author Max_Qiu
 */
public class DocumentOperationTest {
    @BeforeAll
    static void beforeAll() {
        ClientUtils.clientSslWithCaFingerprint();
        // ClientUtils.clientSslWithCaFile();
    }

    @Test
    void normal() throws IOException {
        User user = new User(1, "zhangsan", 30);
        // 创建文档
        IndexRequest<User> indexRequest = new IndexRequest.Builder<User>().index("myindex").id(user.getId().toString()).document(user).build();
        IndexResponse index = ClientUtils.client.index(indexRequest);
        System.out.println("文档操作结果:" + index.result());
        // 批量创建文档
        final List<BulkOperation> operations = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            CreateOperation.Builder<User> builder = new CreateOperation.Builder<>();
            builder.index("myindex");
            builder.id("200" + i);
            builder.document(new User(2000 + i, "zhangsan" + i, 30 + i));
            CreateOperation<User> objectCreateOperation = builder.build();
            BulkOperation bulk = new BulkOperation.Builder().create(objectCreateOperation).build();
            operations.add(bulk);
        }
        BulkRequest bulkRequest = new BulkRequest.Builder().operations(operations).build();
        BulkResponse bulkResponse = ClientUtils.client.bulk(bulkRequest);
        System.out.println("数据操作成功：" + bulkResponse);
        // 删除文档
        DeleteRequest deleteRequest = new DeleteRequest.Builder().index("myindex").id("1001").build();
        ClientUtils.client.delete(deleteRequest);
    }

    @Test
    void lambda() throws IOException {
        User user = new User(1, "zhangsan", 30);
        // 创建文档
        System.out.println(ClientUtils.client.index(req -> req.index("myindex").id(user.getId().toString()).document(user)).result());
        // 批量创建文档
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            users.add(new User(2000 + i, "zhangsan" + i, 30 + i));
        }
        ClientUtils.client.bulk(req -> {
            users.forEach(u -> req.operations(b -> b.create(d -> d.id(u.getId().toString()).index("myindex").document(u))));
            return req;
        });
        // 删除文档
        ClientUtils.client.delete(req -> req.index("myindex").id("1001"));
    }

    @AfterAll
    static void afterAll() {
        ClientUtils.close();
    }
}
