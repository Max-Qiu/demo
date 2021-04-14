package com.maxqiu.demo.document.single;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import com.maxqiu.demo.CreateClient;

/**
 * 索引相关操作
 * 
 * @author Max_Qiu
 */
public class IndexApi {

    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            indexRequest();
            // indexRequest2();
            // indexRequest3();
            // indexRequest4();
            // indexRequestConfig();
            // indexRequestAsync();
            // indexRequestHasException();
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
     * 创建索引，插入ID为1的文档，设置键值对
     */
    private static void indexRequest() throws IOException {
        // 创建一个 IndexRequest 请求，设置索引为 posts （若索引不存在，则创建）
        IndexRequest request = new IndexRequest("posts");
        // 设置文档ID
        request.id("1");
        // 编写文档内容
        String jsonSource =
            "{\"user\":\"kimchy\",\"postDate\":\"2013-01-30\",\"message\":\"trying out Elasticsearch\"}";
        // 设置文档内容
        request.source(jsonSource, XContentType.JSON);

        // 使用客户端进行查询（同步执行，即等待直接结果并返回）
        IndexResponse response = CLIENT.index(request, RequestOptions.DEFAULT);

        // 输出结果
        System.out.println(response);

        // response包含索引和文档ID
        String index = response.getIndex();
        String id = response.getId();
        // 判断是创建文档还是修改文档
        if (response.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println("创建了文档");
        } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("修改了文档");
        }
        // 获取分片处理结果
        ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
        // 处理分片数量小于总分片数的情况
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.println("需要处理分片数量小于总分片数的情况");
        }
        // 处理潜在的故障
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                System.out.println(reason);
            }
        }
    }

    /**
     * map方式创建source
     */
    private static void indexRequest2() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>(3);
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest request = new IndexRequest("posts").id("2").source(jsonMap);
        IndexResponse response = CLIENT.index(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * XContentBuilder 方式创建
     */
    private static void indexRequest3() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        builder.field("user", "kimchy");
        builder.timeField("postDate", new Date());
        builder.field("message", "trying out Elasticsearch");
        builder.endObject();
        IndexRequest request = new IndexRequest("posts").id("3").source(builder);
        // 使用客户端进行查询
        IndexResponse response = CLIENT.index(request, RequestOptions.DEFAULT);
        // 输出结果
        System.out.println(response);
    }

    /**
     * Object密钥对 方式创建
     */
    private static void indexRequest4() throws IOException {
        IndexRequest request = new IndexRequest("posts").id("4").source("user", "kimchy", "postDate", new Date(),
            "message", "trying out Elasticsearch");
        // 使用客户端进行查询
        IndexResponse response = CLIENT.index(request, RequestOptions.DEFAULT);
        // 输出结果
        System.out.println(response);
    }

    /**
     * 自定义request
     */
    private static void indexRequestConfig() throws IOException {
        // 设置index（若索引不存在，则创建）
        IndexRequest request = new IndexRequest("posts");
        // 设置文档ID
        request.id("1");
        // 编写文档内容
        String jsonSource =
            "{\"user\":\"kimchy\",\"postDate\":\"2013-01-30\",\"message\":\"trying out Elasticsearch\"}";
        // 设置文档内容
        request.source(jsonSource, XContentType.JSON);

        // 设置路由（不了解的不要设置）
        // indexRequest.routing("routing");

        // 设置超时时间
        request.timeout(TimeValue.timeValueSeconds(1));
        // indexRequest.timeout("1s");

        // 设置刷新策略（默认 false）
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.NONE);
        // request.setRefreshPolicy("wait_for");

        // 设置版本和版本类型
        // request.version(11);
        // request.versionType(VersionType.EXTERNAL);

        // 文档操作类型（默认index）
        request.opType(DocWriteRequest.OpType.INDEX);
        // request.opType("create");

        // pipeline
        // request.setPipeline("pipeline");

        // 使用客户端进行查询（同步执行，即等待直接结果并返回）
        IndexResponse response = CLIENT.index(request, RequestOptions.DEFAULT);

        // 输出结果
        System.out.println(response);

        // response包含索引和文档ID
        String index = response.getIndex();
        String id = response.getId();
        // 判断是创建文档还是修改文档
        if (response.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println("创建了文档");
        } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("修改了文档");
        }
        // 获取分片处理结果
        ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
        // 处理分片数量小于总分片数的情况
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.println("需要处理分片数量小于总分片数的情况");
        }
        // 处理潜在的故障
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                System.out.println(reason);
            }
        }
    }

    /**
     * 创建索引，插入ID为1的文档，设置键值对
     */
    private static void indexRequestAsync() throws IOException {
        // 设置index（若索引不存在，则创建）
        IndexRequest request = new IndexRequest("posts");
        // 设置文档ID
        request.id("1");
        // 编写文档内容
        String jsonSource =
            "{\"user\":\"kimchy\",\"postDate\":\"2013-01-30\",\"message\":\"trying out Elasticsearch\"}";

        // 设置文档内容
        request.source(jsonSource, XContentType.JSON);
        // 使用客户端进行查询（同步执行，即等待直接结果并返回）
        Cancellable cancellable =
            CLIENT.indexAsync(request, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
                @Override
                public void onResponse(IndexResponse response) {
                    System.out.println("收到返回response：" + response);
                }

                @Override
                public void onFailure(Exception e) {
                    System.out.println("请求异常");
                    e.printStackTrace();
                }
            });
        // 输出结果
        System.out.println(cancellable);
        // 异步查询可以取消
        // cancellable.cancel();

        // 这里sleep 3s 等待查询返回值输出
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理创建时有异常
     */
    private static void indexRequestHasException() throws IOException {
        // opType 设置为 create 且具有相同的索引和文档id，则会报错
        IndexRequest request =
            new IndexRequest("posts").id("1").source("field", "value").opType(DocWriteRequest.OpType.CREATE);
        try {
            IndexResponse response = CLIENT.index(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                // 打印异常或者做其他事情
                e.printStackTrace();
            }
        }
    }

}
