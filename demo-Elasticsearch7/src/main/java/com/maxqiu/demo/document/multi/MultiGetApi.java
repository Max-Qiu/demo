package com.maxqiu.demo.document.multi;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import com.maxqiu.demo.common.ClientUtils;

/**
 * TODO 补全 <br>
 * MultiGetRequest 在单个http请求中并行执行多个请求。
 *
 * @author Max_Qiu
 */
public class MultiGetApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
    }

    public static void main(String[] args) {
        try {
            multiGetRequest();
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

    private static void multiGetRequest() throws IOException {
        // 创建一个多查询索引
        MultiGetRequest request = new MultiGetRequest();
        // 添加两个查询
        request.add(new MultiGetRequest.Item("index", "1"));
        request.add(new MultiGetRequest.Item("index", "2"));

        // 执行查询
        MultiGetResponse response = CLIENT.mget(request, RequestOptions.DEFAULT);

        // 获取查询结果的第一个
        MultiGetItemResponse itemResponse = response.getResponses()[0];

        if (itemResponse.getFailure() == null) {
            return;
        }
        GetResponse firstGet = itemResponse.getResponse();
        String index = itemResponse.getIndex();
        String id = itemResponse.getId();
        if (firstGet.isExists()) {
            long version = firstGet.getVersion();
            String sourceAsString = firstGet.getSourceAsString();
            Map<String, Object> sourceAsMap = firstGet.getSourceAsMap();
            byte[] sourceAsBytes = firstGet.getSourceAsBytes();
        } else {

        }
    }
}
