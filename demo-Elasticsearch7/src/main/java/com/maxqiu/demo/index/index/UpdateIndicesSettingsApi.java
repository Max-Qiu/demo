package com.maxqiu.demo.index.index;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;

import com.maxqiu.demo.common.ClientUtils;

/**
 * @author Max_Qiu
 */
public class UpdateIndicesSettingsApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
    }

    public static void main(String[] args) {
        try {
            updateSettingsRequest();
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

    /**
     * 更新设置
     */
    private static void updateSettingsRequest() throws IOException {
        // 更新一个索引的设置
        UpdateSettingsRequest request = new UpdateSettingsRequest("index1");
        // 更新多个索引的设置
        // UpdateSettingsRequest requestMultiple = new UpdateSettingsRequest("index1", "index2");
        // 更新所有索引的设置
        // UpdateSettingsRequest requestAll = new UpdateSettingsRequest();

        // 设置要应用的索引设置（1. Settings设置）
        Settings settings = Settings.builder().put("index.number_of_replicas", 0).build();
        request.settings(settings);

        // 2. Settings.Builder 设置
        // Settings.Builder settingsBuilder = Settings.builder().put(settingKey, settingValue);
        // request.settings(settingsBuilder);

        // 3. String 设置
        // request.settings("{\"index.number_of_replicas\": \"2\"}", XContentType.JSON);

        // 4. Map 设置
        // Map<String, Object> map = new HashMap<>();
        // map.put(settingKey, settingValue);
        // request.settings(map);

        request.setPreserveExisting(false);

        // 等待所有节点将索引创建确认为超时的超时
        request.timeout(TimeValue.timeValueMinutes(2));
        // request.timeout("2m");

        // 连接到主节点的超时
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        // request.masterNodeTimeout("1m");

        // 设置IndicesOptions控制如何解决不可用的索引以及如何扩展通配符表达式
        request.indicesOptions(IndicesOptions.lenientExpandOpen());

        // 执行
        AcknowledgedResponse updateSettingsResponse = CLIENT.indices().putSettings(request, RequestOptions.DEFAULT);
        // 异步
        // CLIENT.indices().putSettingsAsync(request, RequestOptions.DEFAULT, listener);

        // 指示是否所有节点都已确认请求
        boolean acknowledged = updateSettingsResponse.isAcknowledged();
    }
}
