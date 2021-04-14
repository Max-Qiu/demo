package com.maxqiu.demo.index.mapping;

import java.io.IOException;

import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import com.maxqiu.demo.CreateClient;

/**
 * 修改映射
 * 
 * @author Max_Qiu
 */
public class PutMappingApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            putMappingRequest();
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

    private static void putMappingRequest() throws IOException {
        // PutMappingRequest需要一个index参数：
        PutMappingRequest request = new PutMappingRequest("twitter");

        /// 1. 映射源作为 String
        request.source("{\"properties\": {\"message\": {\"type\": \"text\"}}}", XContentType.JSON);

        /// 2. 提供为的映射源，Map它会自动转换为JSON格式
        // Map<String, Object> jsonMap = new HashMap<>();
        // Map<String, Object> message = new HashMap<>();
        // message.put("type", "text");
        // Map<String, Object> properties = new HashMap<>();
        // properties.put("message", message);
        // jsonMap.put("properties", properties);
        // request.source(jsonMap);

        /// 3. 作为XContentBuilder对象提供的映射源，Elasticsearch内置帮助器可生成JSON内容
        // XContentBuilder builder = XContentFactory.jsonBuilder();
        // builder.startObject();
        // {
        // builder.startObject("properties");
        // {
        // builder.startObject("message");
        // {
        // builder.field("type", "text");
        // }
        // builder.endObject();
        // }
        // builder.endObject();
        // }
        // builder.endObject();
        // request.source(builder);

        // 等待所有节点将索引创建确认为超时的超时 TimeValue
        request.setTimeout(TimeValue.timeValueMinutes(2));

        // 连接到主节点的超时 TimeValue
        request.setMasterTimeout(TimeValue.timeValueMinutes(1));

        // 请求
        AcknowledgedResponse putMappingResponse = CLIENT.indices().putMapping(request, RequestOptions.DEFAULT);

        // 异步
        // client.indices().putMappingAsync(request, RequestOptions.DEFAULT, listener);

        // 指示是否所有节点都已确认请求
        boolean acknowledged = putMappingResponse.isAcknowledged();
    }
}
