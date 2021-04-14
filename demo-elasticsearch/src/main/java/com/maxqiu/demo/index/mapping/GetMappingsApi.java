package com.maxqiu.demo.index.mapping;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.unit.TimeValue;

import com.maxqiu.demo.CreateClient;

/**
 * 获取映射
 * 
 * @author Max_Qiu
 */
public class GetMappingsApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            getMappingsRequest();
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

    private static void getMappingsRequest() throws IOException {
        // 一个空请求，将返回所有索引
        GetMappingsRequest request = new GetMappingsRequest();
        // 设置索引以获取以下内容的映射
        request.indices("twitter");

        // 连接到主节点的超时 TimeValue
        request.setMasterTimeout(TimeValue.timeValueMinutes(1));
        // 扩展索引名称的选项
        request.indicesOptions(IndicesOptions.lenientExpandOpen());

        // 执行
        GetMappingsResponse getMappingResponse = CLIENT.indices().getMapping(request, RequestOptions.DEFAULT);

        // 异步
        // client.indices().getMappingAsync(request, RequestOptions.DEFAULT, listener);

        // 返回所有索引的映射
        Map<String, MappingMetadata> allMappings = getMappingResponse.mappings();
        // 检索特定索引的映射
        MappingMetadata indexMapping = allMappings.get("twitter");
        // 将映射作为Java Map获取
        Map<String, Object> mapping = indexMapping.sourceAsMap();
    }
}
