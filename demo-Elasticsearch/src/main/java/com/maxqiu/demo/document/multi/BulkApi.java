package com.maxqiu.demo.document.multi;

import java.io.IOException;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import com.maxqiu.demo.common.ClientUtils;

/**
 * @author Max_Qiu
 */
public class BulkApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
    }

    public static void main(String[] args) {
        try {
            bulkRequest();
            // bulkRequestConfig();
            // bulkRequestAsync();
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

    private static void bulkRequest() throws IOException {
        // 创建一个 批处理
        BulkRequest request = new BulkRequest();
        // 添加多个 index 请求
        request.add(new IndexRequest("posts").id("1").source(XContentType.JSON, "field", "foo"));
        request.add(new IndexRequest("posts").id("2").source(XContentType.JSON, "field", "bar"));
        request.add(new IndexRequest("posts").id("3").source(XContentType.JSON, "field", "baz"));
        // 而且可以添加多个类型
        request.add(new DeleteRequest("posts", "3"));
        request.add(new UpdateRequest("posts", "2").doc(XContentType.JSON, "other", "test"));
        request.add(new IndexRequest("posts").id("4").source(XContentType.JSON, "field", "baz"));

        // 批量执行
        BulkResponse bulkResponse = CLIENT.bulk(request, RequestOptions.DEFAULT);
        System.out.println(bulkResponse);

        // 解析 批量请求返回值
        for (BulkItemResponse bulkItemResponse : bulkResponse) {
            // 检索操作的响应（成功与否），可以是 IndexResponse，UpdateResponse或DeleteResponse可全部被视为 DocWriteResponse实例
            DocWriteResponse itemResponse = bulkItemResponse.getResponse();
            switch (bulkItemResponse.getOpType()) {
                case INDEX:
                case CREATE:
                    IndexResponse indexResponse = (IndexResponse)itemResponse;
                    System.out.println("处理索引操作的响应：" + indexResponse);
                    break;
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse)itemResponse;
                    System.out.println("处理更新操作的响应：" + updateResponse);
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse)itemResponse;
                    System.out.println("处理删除操作的响应：" + deleteResponse);
                    break;
                default:
                    System.out.println("异常");
            }
        }

        // 批量响应提供了一种快速检查一个或多个操作是否失败的方法：
        if (bulkResponse.hasFailures()) {
            // true如果至少一项操作失败，则返回此方法
            System.out.println("true如果至少一项操作失败，则返回此方法");
        }

        for (BulkItemResponse bulkItemResponse : bulkResponse) {
            // 判断给定的操作是否失败
            if (bulkItemResponse.isFailed()) {
                BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                // 操作失败的原因
                System.out.println("操作失败的原因：" + failure);
            }
        }
    }

    private static void bulkRequestConfig() throws IOException {
        BulkRequest request = new BulkRequest();

        // 具有全局索引的批量请求，该全局请求用于所有子请求，除非在子请求上被覆盖。此参数为@Nullable，只能在BulkRequest创建期间设置。
        // BulkRequest defaulted = new BulkRequest("posts");

        request.add(new IndexRequest("posts").id("1").source(XContentType.JSON, "field", "foo"));
        request.add(new IndexRequest("posts").id("2").source(XContentType.JSON, "field", "bar"));
        request.add(new IndexRequest("posts").id("3").source(XContentType.JSON, "field", "baz"));

        // 设置超时时间
        request.timeout(TimeValue.timeValueSeconds(1));
        // indexRequest.timeout("1s");

        // 设置刷新策略（默认 false）
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.NONE);
        // request.setRefreshPolicy("wait_for");

        // 设置在继续更新操作之前必须处于活动状态的分片副本数。
        request.waitForActiveShards(2);
        // 作为提供碎片拷贝数ActiveShardCount：可以是ActiveShardCount.ALL， ActiveShardCount.ONE或ActiveShardCount.DEFAULT（默认）
        request.waitForActiveShards(ActiveShardCount.ALL);

        // pipeline
        // request.setPipeline("pipeline");

        // 设置路由
        // request.routing("routing");

        BulkResponse bulkResponse = CLIENT.bulk(request, RequestOptions.DEFAULT);
        System.out.println(bulkResponse);
    }

    private static void bulkRequestAsync() throws IOException {
        // 创建一个 批处理
        BulkRequest request = new BulkRequest();
        // 添加多个 index 请求
        request.add(new IndexRequest("posts").id("1").source(XContentType.JSON, "field", "foo"));
        request.add(new IndexRequest("posts").id("2").source(XContentType.JSON, "field", "bar"));
        request.add(new IndexRequest("posts").id("3").source(XContentType.JSON, "field", "baz"));
        // 而且可以添加多个类型
        request.add(new DeleteRequest("posts", "3"));
        request.add(new UpdateRequest("posts", "2").doc(XContentType.JSON, "other", "test"));
        request.add(new IndexRequest("posts").id("4").source(XContentType.JSON, "field", "baz"));

        // 批量执行
        Cancellable cancellable = CLIENT.bulkAsync(request, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
            @Override
            public void onResponse(BulkResponse response) {
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
     * // TODO 细化
     */
    private static void bulkProcessor() {
        // 创建 BulkProcessor.Listener
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                System.out.println("执行 BulkRequest 之前");
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                System.out.println("执行 BulkRequest 之后（正常执行）");
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                System.out.println("执行 BulkRequest 之后（返回异常）");
            }
        };

        // BulkProcessor通过中调用方法来创建BulkProcessor.Builder的build()。该RestHighLevelClient.bulkAsync() 方法将用于执行BulkRequest后台操作。
        BulkProcessor bulkProcessor = BulkProcessor
            .builder((request, bulkListener) -> CLIENT.bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
                listener)
            .build();

        // 该BulkProcessor.Builder提供的方法来配置，BulkProcessor应该处理请求的执行：
        BulkProcessor.Builder builder = BulkProcessor.builder(
            (request, bulkListener) -> CLIENT.bulkAsync(request, RequestOptions.DEFAULT, bulkListener), listener);
        // 根据当前添加的操作数设置何时刷新新的批量请求（默认为1000，使用-1禁用它）
        builder.setBulkActions(500);
        // 根据当前添加的操作大小设置何时刷新新的批量请求（默认为5Mb，使用-1禁用它）
        builder.setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.MB));
        // 设置允许执行的并发请求数（默认为1，使用0仅允许执行单个请求）
        builder.setConcurrentRequests(0);
        // 设置刷新间隔，BulkRequest如果间隔超过，则刷新所有未决（默认值未设置）
        builder.setFlushInterval(TimeValue.timeValueSeconds(10L));
        // 设置一个恒定的退避策略，该策略最初等待1秒，然后重试3次。
        // 请参阅BackoffPolicy.noBackoff()，BackoffPolicy.constantBackoff()以及BackoffPolicy.exponentialBackoff() 更多选项。
        builder.setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1L), 3));
        BulkProcessor build = builder.build();

        IndexRequest one = new IndexRequest("posts").id("1").source(XContentType.JSON, "title",
            "In which order are my Elasticsearch queries executed?");
        IndexRequest two = new IndexRequest("posts").id("2").source(XContentType.JSON, "title",
            "Current status and upcoming changes in Elasticsearch");
        IndexRequest three = new IndexRequest("posts").id("3").source(XContentType.JSON, "title",
            "The Future of Federated Search in Elasticsearch");
        bulkProcessor.add(one);
        bulkProcessor.add(two);
        bulkProcessor.add(three);

    }
}
