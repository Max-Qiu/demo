package com.maxqiu.demo.document.single;

import java.io.IOException;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.maxqiu.demo.CreateClient;

/**
 * TODO 后期整理 <br>
 * URL：https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.9/java-rest-high-document-term-vectors.html<br>
 * 术语向量API返回有关特定文档字段中术语的信息和统计信息。该文档可以存储在索引中或由用户人工提供。
 * 
 * @author Max_Qiu
 */
public class TermVectorsApi {

    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            termVectorsRequest();
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

    private static void termVectorsRequest() throws IOException {
        // 创建 TermVectorsRequest 需要 index，type，id 来指定某个文档以及为其检索信息的字段。
        TermVectorsRequest request = new TermVectorsRequest("posts", "1");
        request.setFields("user");
        // 执行查询
        TermVectorsResponse response = CLIENT.termvectors(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    private static void termVectorsRequest2() throws IOException {
        // 为人工文档（即索引中不存在的文档）生成术语向量：
        XContentBuilder docBuilder = XContentFactory.jsonBuilder();
        docBuilder.startObject().field("user", "guest-user").endObject();
        TermVectorsRequest request = new TermVectorsRequest("authors", docBuilder);
        TermVectorsResponse response = CLIENT.termvectors(request, RequestOptions.DEFAULT);
    }
}
