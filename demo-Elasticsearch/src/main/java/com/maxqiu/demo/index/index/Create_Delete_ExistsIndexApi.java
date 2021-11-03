package com.maxqiu.demo.index.index;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;

import com.maxqiu.demo.common.ClientUtils;

/**
 * 创建、删除、存在<br>
 * 索引
 *
 * @author Max_Qiu
 */
public class Create_Delete_ExistsIndexApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
    }

    public static void main(String[] args) {
        try {
            indexSetting();
            setMapping();
            alias();
            sourceCreate();
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
     * 索引设置
     */
    private static void indexSetting() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("twitter");
        // 指定设置
        request.settings(Settings.builder()
            // 指定分片数量
            .put("index.number_of_shards", 3)
            // 指定副本数量
            .put("index.number_of_replicas", 0));
        CreateIndexResponse createIndexResponse = CLIENT.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    /**
     * 映射
     */
    private static void setMapping() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("twitter");
        // 设置映射关系
        request.mapping(
            // 要定义的类型
            "{\"properties\":{\"message\": {\"type\": \"text\"}}}",
            // 此类型的映射，以JSON字符串形式提供
            XContentType.JSON);

        // 也可以使用map设置映射关系
        Map<String, Object> message = new HashMap<>(1);
        message.put("type", "text");
        Map<String, Object> properties = new HashMap<>(1);
        properties.put("message", message);
        Map<String, Object> mapping = new HashMap<>(1);
        mapping.put("properties", properties);
        // 提供为的映射源，Map它会自动转换为JSON格式
        request.mapping(mapping);

        // 还可以使用XContentBuilder设置映射关系
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("message");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        request.mapping(builder);

        CreateIndexResponse createIndexResponse = CLIENT.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    /**
     * 别名
     */
    private static void alias() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("twitter");
        // 定义的别名
        request.alias(new Alias("twitter_alias").filter(QueryBuilders.termQuery("user", "kimchy")));
        CreateIndexResponse createIndexResponse = CLIENT.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    private static void sourceCreate() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("twitter");
        request.source(
            "{\n" + "    \"settings\" : {\n" + "        \"number_of_shards\" : 1,\n"
                + "        \"number_of_replicas\" : 0\n" + "    },\n" + "    \"mappings\" : {\n"
                + "        \"properties\" : {\n" + "            \"message\" : { \"type\" : \"text\" }\n" + "        }\n"
                + "    },\n" + "    \"aliases\" : {\n" + "        \"twitter_alias\" : {}\n" + "    }\n" + "}",
            XContentType.JSON);
        CreateIndexResponse createIndexResponse = CLIENT.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    private static void createConfig() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("twitter");

        // 等待所有节点将索引创建确认为超时的超时 TimeValue
        request.setTimeout(TimeValue.timeValueMinutes(2));
        // 连接到主节点的超时 TimeValue
        request.setMasterTimeout(TimeValue.timeValueMinutes(1));

        // 创建索引API返回响应之前要等待的活动分片副本数，例如 int
        request.waitForActiveShards(ActiveShardCount.from(2));
        // 创建索引API返回响应之前要等待的活动分片副本数，例如 ActiveShardCount
        request.waitForActiveShards(ActiveShardCount.DEFAULT);

        CreateIndexResponse createIndexResponse = CLIENT.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }
}
