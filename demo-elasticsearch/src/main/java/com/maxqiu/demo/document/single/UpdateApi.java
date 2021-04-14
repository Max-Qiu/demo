package com.maxqiu.demo.document.single;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.rest.RestStatus;

import com.maxqiu.demo.CreateClient;

/**
 * @author Max_Qiu
 */
public class UpdateApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            updateRequest();
            // upsert();
            // updateRequestConfig();
            // updateRequestAsync();
            // updateRequestException1();
            // updateRequestException2();
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
     * 修改请求
     */
    private static void updateRequest() throws IOException {
        // 创建一个 UpdateRequest 请求，设置索引为 posts （若索引不存在，则创建）
        UpdateRequest request = new UpdateRequest("posts", "1");

        /// TODO 使用脚本更新 singletonMap 不知道是什么东西
        // Map<String, Object> parameters = singletonMap("count", 4);
        // 1. 作为 inline script
        // Script inline = new Script(ScriptType.INLINE, "painless", "ctx._source.field += params.count", parameters);
        // request.script(inline);
        // 2. 作为 stored script
        // Script stored = new Script(ScriptType.STORED, null, "increment-field", parameters);
        // request.script(stored);

        /// 使用部分文档进行更新
        // 1. JSON格式提供
        String jsonString = "{" + "\"updated\":\"2017-01-01\"," + "\"reason\":\"daily update\"" + "}";
        request.doc(jsonString, XContentType.JSON);
        // 2. 使用Map键值对
        // Map<String, Object> jsonMap = new HashMap<>(2);
        // jsonMap.put("updated", new Date());
        // jsonMap.put("reason", "daily update");
        // request.doc(jsonMap);
        // 3. 使用 XContentBuilder
        // XContentBuilder builder = XContentFactory.jsonBuilder();
        // builder.startObject();
        // {
        // builder.timeField("updated", LocalDateTime.now());
        // builder.field("reason", "daily update");
        // }
        // builder.endObject();
        // request.doc(builder);
        // 4. 使用Object密钥对
        // request.doc("updated", new Date(), "reason", "daily update");

        // 启用后返回结果有GetResult
        request.fetchSource(true);

        // 执行请求
        UpdateResponse response = CLIENT.update(request, RequestOptions.DEFAULT);
        System.out.println(response);

        // response Result 解析
        String index = response.getIndex();
        String id = response.getId();
        long version = response.getVersion();
        if (response.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println("处理首次创建文档的情况（upsert）");
        } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("处理文档更新的情况");
        } else if (response.getResult() == DocWriteResponse.Result.DELETED) {
            System.out.println("处理文件被删除的情况");
        } else if (response.getResult() == DocWriteResponse.Result.NOOP) {
            System.out.println("处理文档不受更新影响的情况，即未对文档执行任何操作（空转）");
        }

        // response GetResult 解析
        GetResult result = response.getGetResult();
        if (result.isExists()) {
            String sourceAsString = result.sourceAsString();
            System.out.println("GetResult 转 String：" + sourceAsString);
            Map<String, Object> sourceAsMap = result.sourceAsMap();
            System.out.println("GetResult 转 Map<String, Object>：" + sourceAsMap);
            byte[] sourceAsBytes = result.source();
            System.out.println("GetResult 转 byte[]：" + Arrays.toString(sourceAsBytes));
        } else {
            System.out.println("处理响应中不存在文档源的情况（默认情况下就是这种情况）");
        }

        // 检查分配故障
        ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.println("处理成功分片数量少于总分片数量的情况");
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                System.out.println("处理潜在的故障：" + reason);
            }
        }
    }

    /**
     * 如果文档尚不存在，则可以使用upsert方法定义一些内容，这些内容将作为新文档插入：
     */
    private static void upsert() throws IOException {
        UpdateRequest request = new UpdateRequest("posts", "1");
        String jsonString = "{\"created\":\"2017-01-01\"}";
        request.upsert(jsonString, XContentType.JSON);
        // TODO 报错 Validation Failed: 1: script or doc is missing;
        UpdateResponse response = CLIENT.update(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * 修改请求
     */
    private static void updateRequestConfig() throws IOException {
        // 创建一个 UpdateRequest 请求，设置索引为 posts （若索引不存在，则创建）
        UpdateRequest request = new UpdateRequest("posts", "1");

        String jsonString = "{" + "\"updated\":\"2017-01-01\"," + "\"reason\":\"daily update\"" + "}";
        request.doc(jsonString, XContentType.JSON);

        // 设置路由
        // indexRequest.routing("routing");

        // 设置超时时间
        request.timeout(TimeValue.timeValueSeconds(1));
        // indexRequest.timeout("1s");

        // 设置刷新策略（默认 NONE）
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        // request.setRefreshPolicy("wait_for");

        // 如果要更新的文档已在更新操作的获取和索引阶段之间被另一个操作更改，则重试多少次更新操作
        request.retryOnConflict(3);

        /// 1. 禁用源检索（默认为 FETCH_SOURCE 返还所有字段）
        // request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        request.fetchSource(false);

        /// 2. 为特定字段配置包含
        // String[] includes = new String[] {"message", "*Date"};// 包含字段
        // String[] excludes = Strings.EMPTY_ARRAY;// 排除的字段
        // FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);// 配置
        // request.fetchSourceContext(fetchSourceContext);

        /// 3. 为特定字段配置屏蔽
        // String[] includes = Strings.EMPTY_ARRAY;// 包含字段
        // String[] excludes = new String[] {"message"};// 排除的字段
        // FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);// 配置
        // request.fetchSourceContext(fetchSourceContext);

        // ifSeqNo ifPrimaryTerm
        request.setIfSeqNo(2L);
        request.setIfPrimaryTerm(1L);

        // 禁用noop检测
        request.detectNoop(false);

        // 指示脚本必须运行，而不管文档是否存在，即，脚本将负责创建尚不存在的文档。
        request.scriptedUpsert(true);

        // 指示部分文档（如果尚不存在）必须用作upsert文档。
        request.docAsUpsert(true);

        // 设置在继续更新操作之前必须处于活动状态的分片副本数。
        request.waitForActiveShards(2);
        // 作为提供碎片拷贝数ActiveShardCount：可以是ActiveShardCount.ALL， ActiveShardCount.ONE或ActiveShardCount.DEFAULT（默认）
        request.waitForActiveShards(ActiveShardCount.ALL);

        // 执行请求
        UpdateResponse response = CLIENT.update(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * 异步修改请求
     */
    private static void updateRequestAsync() throws IOException {
        UpdateRequest request = new UpdateRequest("posts", "1");

        String jsonString = "{" + "\"updated\":\"2017-01-01\"," + "\"reason\":\"daily update\"" + "}";
        request.doc(jsonString, XContentType.JSON);

        // 异步执行请求
        Cancellable cancellable =
            CLIENT.updateAsync(request, RequestOptions.DEFAULT, new ActionListener<UpdateResponse>() {
                @Override
                public void onResponse(UpdateResponse updateResponse) {
                    System.out.println(updateResponse);
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
     * 处理 文档ID不存在的情况
     */
    private static void updateRequestException1() throws Exception {
        UpdateRequest request = new UpdateRequest("posts", "does_not_exist").doc("field", "value");
        try {
            UpdateResponse updateResponse = CLIENT.update(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                System.out.println("处理由于文档不存在而引发的异常");
            }
            e.printStackTrace();
        }
    }

    /**
     * 处理 版本冲突初五
     */
    private static void updateRequestException2() throws Exception {
        UpdateRequest request =
            new UpdateRequest("posts", "1").doc("field", "value").setIfSeqNo(101L).setIfPrimaryTerm(200L);
        try {
            UpdateResponse updateResponse = CLIENT.update(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                System.out.println("处理示返回了版本冲突错误。");
            }
            e.printStackTrace();
        }
    }
}
