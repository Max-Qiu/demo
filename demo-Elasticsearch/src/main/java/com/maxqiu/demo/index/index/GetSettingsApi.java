package com.maxqiu.demo.index.index;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;

import com.maxqiu.demo.common.ClientUtils;

/**
 * @author Max_Qiu
 */
public class GetSettingsApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
    }

    public static void main(String[] args) {
        try {
            getSettingsRequest();
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

    private static void getSettingsRequest() throws IOException {
        GetSettingsRequest request = new GetSettingsRequest().indices("index");

        // 一个或多个设置是唯一检索到的设置。如果未设置，则将检索所有设置
        request.names("index.number_of_shards");

        // 如果为true，将为未在索引上明确设置的设置返回默认值
        request.includeDefaults(true);

        // 设置IndicesOptions控制如何解决不可用的索引以及如何扩展通配符表达式
        request.indicesOptions(IndicesOptions.lenientExpandOpen());

        GetSettingsResponse getSettingsResponse = CLIENT.indices().getSettings(request, RequestOptions.DEFAULT);

        // client.indices().getSettingsAsync(request, RequestOptions.DEFAULT, listener);

        // 我们可以直接从响应中以字符串的形式获取特定索引的设置值
        String numberOfShardsString = getSettingsResponse.getSetting("index", "index.number_of_shards");
        // 我们还可以检索特定索引的Settings对象以进行进一步检查
        Settings indexSettings = getSettingsResponse.getIndexToSettings().get("index");
        // 返回的Settings对象为非String类型提供了便捷方法
        Integer numberOfShards = indexSettings.getAsInt("index.number_of_shards", null);

        // 各个默认设置值可以直接从 GetSettingsResponse
        String refreshInterval = getSettingsResponse.getSetting("index", "index.refresh_interval");
        // 我们可能会检索索引的Settings对象，该索引包含具有默认值的那些设置
        Settings indexDefaultSettings = getSettingsResponse.getIndexToDefaultSettings().get("index");
    }
}
