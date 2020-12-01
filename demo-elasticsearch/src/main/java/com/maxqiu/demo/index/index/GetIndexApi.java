package com.maxqiu.demo.index.index;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;

import com.maxqiu.demo.CreateClient;

/**
 * @author Max_Qiu
 */
public class GetIndexApi {

    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            getIndexRequest();
            // openIndexRequest();
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

    private static void getIndexRequest() throws IOException {
        // 要检索其信息的索引
        GetIndexRequest request = new GetIndexRequest("index");
        // 如果为true，将为未在索引上明确设置的设置返回默认值
        request.includeDefaults(true);
        // 设置IndicesOptions控制如何解决不可用的索引以及如何扩展通配符表达式
        request.indicesOptions(IndicesOptions.lenientExpandOpen());

        // 执行
        GetIndexResponse getIndexResponse = CLIENT.indices().get(request, RequestOptions.DEFAULT);
        // 异步执行
        // client.indices().getAsync(request, RequestOptions.DEFAULT, listener);

        // 检索地图不同类型的MappingMetadata的index。
        MappingMetadata indexMappings = getIndexResponse.getMappings().get("index");
        // 检索地图以获取文档类型的属性doc。
        Map<String, Object> indexTypeMappings = indexMappings.getSourceAsMap();
        // 获取的别名列表index。
        List<AliasMetadata> indexAliases = getIndexResponse.getAliases().get("index");
        // 获取设置字符串值index.number_of_shards的index。如果未明确指定设置，但该设置是默认设置的一部分（并且includeDefault为true），则将检索默认设置。
        String numberOfShardsString = getIndexResponse.getSetting("index", "index.number_of_shards");
        // 检索的所有设置index。
        Settings indexSettings = getIndexResponse.getSettings().get("index");
        // 这些Settings对象提供了更大的灵活性。在这里，它用于将设置提取index.number_of_shards为整数。
        Integer numberOfShards = indexSettings.getAsInt("index.number_of_shards", null);
        // 获取默认设置index.refresh_interval（如果includeDefault设置为true）。如果includeDefault设置为false，getIndexResponse.defaultSettings()将返回一个空的地图。
        TimeValue time = getIndexResponse.getDefaultSettings().get("index").getAsTime("index.refresh_interval", null);
    }
}
