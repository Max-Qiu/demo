package com.maxqiu.demo.index.index;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;

import com.maxqiu.demo.CreateClient;

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
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            createIndexRequest();
            // indexSetting();
            // setMapping();
            // alias();
            // sourceCreate();

            // getIndexRequest();

            // deleteIndexRequest();
            // deleteException();
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
     * 简单创建索引
     */
    private static void createIndexRequest() throws IOException {
        // 新建一个创建索引请求，指定索引名称为twitter
        CreateIndexRequest request = new CreateIndexRequest("twitter");
        // 注：如果索引已存在，则报错
        CreateIndexResponse createIndexResponse = CLIENT.indices().create(request, RequestOptions.DEFAULT);

        // 异步
        // CLIENT.indices().createAsync(request, RequestOptions.DEFAULT, listener);

        System.out.println(createIndexResponse);

        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println(acknowledged);
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
        System.out.println(shardsAcknowledged);
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

    /**
     * 索引是否存在
     */
    private static void getIndexRequest() throws IOException {
        // 创建一个获取索引请求
        GetIndexRequest request = new GetIndexRequest("twitter");

        // 是返回本地信息还是从主节点检索状态
        request.local(false);
        // 以适合人类的格式返回结果
        request.humanReadable(true);
        // 是否为每个索引返回所有默认设置
        request.includeDefaults(false);
        // 控制如何解决不可用的索引以及如何扩展通配符表达式
        // request.indicesOptions(indicesOptions);

        // 请求
        boolean exists = CLIENT.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);

        // 异步
        // client.indices().existsAsync(request, RequestOptions.DEFAULT, listener);
    }

    private static void deleteIndexRequest() throws IOException {
        // 创建一个删除索引请求，指定删除posts索引
        DeleteIndexRequest request = new DeleteIndexRequest("posts");

        // 等待所有节点将索引删除确认为超时的超时 TimeValue
        request.timeout(TimeValue.timeValueMinutes(2));
        // 等待所有节点将索引删除确认为超时的超时 String
        // request.timeout("2m")

        // 连接到主节点的超时 TimeValue
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        // 连接到主节点的超时 String
        // request.masterNodeTimeout("1m");

        // 设置IndicesOptions控制如何解决不可用的索引以及如何扩展通配符表达式
        // request.indicesOptions(IndicesOptions.lenientExpandOpen());

        // 执行
        AcknowledgedResponse deleteIndexResponse = CLIENT.indices().delete(request, RequestOptions.DEFAULT);
        // 异步执行
        // client.indices().deleteAsync(request, RequestOptions.DEFAULT, listener);

        // 返回的值DeleteIndexResponse允许检索有关已执行操作的信息
        boolean acknowledged = deleteIndexResponse.isAcknowledged();
    }

    /**
     * 如果找不到索引，ElasticsearchException将抛出：
     */
    private static void deleteException() throws IOException {
        try {
            DeleteIndexRequest request = new DeleteIndexRequest("does_not_exist");
            CLIENT.indices().delete(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {
                System.out.println("找不到要删除的索引");
            }
        }
    }
}
