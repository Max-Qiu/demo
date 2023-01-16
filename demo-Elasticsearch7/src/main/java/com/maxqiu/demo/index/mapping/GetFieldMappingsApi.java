package com.maxqiu.demo.index.mapping;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetFieldMappingsRequest;
import org.elasticsearch.client.indices.GetFieldMappingsResponse;

import com.maxqiu.demo.common.ClientUtils;

/**
 * 获取字段映射
 *
 * @author Max_Qiu
 */
public class GetFieldMappingsApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
    }

    public static void main(String[] args) {
        try {
            getFieldMappingsRequest();
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

    private static void getFieldMappingsRequest() throws IOException {
        // 空的请求
        GetFieldMappingsRequest request = new GetFieldMappingsRequest();
        // 设置索引以获取以下内容的映射
        request.indices("twitter");
        // 要返回的字段
        request.fields("message", "timestamp");

        // 设置IndicesOptions控制如何解决不可用的索引以及如何扩展通配符表达式
        request.indicesOptions(IndicesOptions.lenientExpandOpen());

        // 执行
        GetFieldMappingsResponse response = CLIENT.indices().getFieldMapping(request, RequestOptions.DEFAULT);

        // 异步
        // client.indices().getFieldMappingAsync(request, RequestOptions.DEFAULT, listener);

        // 返回所有请求的索引字段的映射
        Map<String, Map<String, GetFieldMappingsResponse.FieldMappingMetadata>> mappings = response.mappings();
        // 检索特定索引的映射
        Map<String, GetFieldMappingsResponse.FieldMappingMetadata> fieldMappings = mappings.get("twitter");
        // 获取该message字段的映射元数据
        GetFieldMappingsResponse.FieldMappingMetadata metadata = fieldMappings.get("message");
        // 获取字段的全名
        String fullName = metadata.fullName();
        // 获取字段的映射源
        Map<String, Object> source = metadata.sourceAsMap();
    }
}
