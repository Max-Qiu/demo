package com.maxqiu.demo.index.index;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CloseIndexRequest;
import org.elasticsearch.common.unit.TimeValue;

import com.maxqiu.demo.CreateClient;

/**
 * 打开、关闭索引
 * 
 * @author Max_Qiu
 */
public class Open_CloseIndexApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            closeIndexRequest();
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
     * 打开索引
     */
    private static void openIndexRequest() throws IOException {
        // OpenIndexRequest需要一个index参数：
        OpenIndexRequest request = new OpenIndexRequest("twitter");

        // 等待所有节点确认索引已超时的超时
        request.timeout(TimeValue.timeValueMinutes(2));
        // request.timeout("2m");

        // 连接到主节点的超时
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        // request.masterNodeTimeout("1m");

        // 开放索引API返回响应之前要等待的活动分片副本数
        // request.waitForActiveShards(2);
        // request.waitForActiveShards(ActiveShardCount.DEFAULT);

        // 设置IndicesOptions控制如何解决不可用的索引以及如何扩展通配符表达式
        // request.indicesOptions(IndicesOptions.strictExpandOpen());

        // 执行
        OpenIndexResponse openIndexResponse = CLIENT.indices().open(request, RequestOptions.DEFAULT);
        // 异步执行
        // CLIENT.indices().openAsync(request, RequestOptions.DEFAULT, listener);

        // 指示是否所有节点都已确认请求
        boolean acknowledged = openIndexResponse.isAcknowledged();
        System.out.println(acknowledged);
        // 指示是否在超时之前为索引中的每个分片启动了必要数量的分片副本
        boolean shardsAcked = openIndexResponse.isShardsAcknowledged();
        System.out.println(shardsAcked);
    }

    /**
     * 打开索引
     */
    private static void closeIndexRequest() throws IOException {
        // CloseIndexRequest需要一个index参数：
        CloseIndexRequest request = new CloseIndexRequest("twitter");

        // 等待所有节点确认索引的超时已关闭 TimeValue
        request.setTimeout(TimeValue.timeValueMinutes(2));

        // 连接到主节点的超时 TimeValue
        request.setMasterTimeout(TimeValue.timeValueMinutes(1));

        // 设置IndicesOptions控制如何解决不可用的索引以及如何扩展通配符表达式
        // request.indicesOptions(IndicesOptions.lenientExpandOpen());

        // 执行
        AcknowledgedResponse closeIndexResponse = CLIENT.indices().close(request, RequestOptions.DEFAULT);
        // 异步执行
        // CLIENT.indices().closeAsync(request, RequestOptions.DEFAULT, listener);

        // 返回的值CloseIndexResponse允许检索有关已执行操作的信息
        boolean acknowledged = closeIndexResponse.isAcknowledged();
        System.out.println(acknowledged);
    }

}
