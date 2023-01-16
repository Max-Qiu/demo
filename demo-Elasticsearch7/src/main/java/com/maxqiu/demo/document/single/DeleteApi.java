package com.maxqiu.demo.document.single;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;

import com.maxqiu.demo.common.ClientUtils;

/**
 * @author Max_Qiu
 */
public class DeleteApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
    }

    public static void main(String[] args) {
        try {
            deleteRequest();
            // deleteRequestConfig();
            // deleteRequestAsync();
            // deleteRequestException();
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
     * 删除请求
     */
    private static void deleteRequest() throws IOException {
        // 创建一个 DeleteRequest 请求，设置索引为 posts ，文档ID 为 1
        DeleteRequest request = new DeleteRequest("posts", "1");
        // 执行请求
        DeleteResponse response = CLIENT.delete(request, RequestOptions.DEFAULT);
        System.out.println(response);

        if (response.getResult() == DocWriteResponse.Result.NOT_FOUND) {
            System.out.println("未找到文档");
        }

        // response 详细信息
        String index = response.getIndex();
        String id = response.getId();
        long version = response.getVersion();
        ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.println("处理成功分片数量少于总分片数量的情况");
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                System.out.println("需要处理潜在的故障：" + reason);
            }
        }
    }

    /**
     * 配置删除请求
     */
    private static void deleteRequestConfig() throws IOException {
        DeleteRequest request = new DeleteRequest("posts", "1");

        // 设置路由
        // request.routing("routing");

        // 设置超时时间
        request.timeout(TimeValue.timeValueMinutes(2));
        // request.timeout("2m");

        // 设置刷新策略
        // request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        // request.setRefreshPolicy("wait_for");

        // 设置版本
        // request.version(2);
        // 设置版本类型
        // request.versionType(VersionType.EXTERNAL);

        DeleteResponse response = CLIENT.delete(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * 异步执行
     */
    private static void deleteRequestAsync() throws IOException {
        // 创建一个 request 请求，设置索引为 posts ，文档ID 为 1
        DeleteRequest request = new DeleteRequest("posts", "1");
        // 异步查询
        Cancellable cancellable =
            CLIENT.deleteAsync(request, RequestOptions.DEFAULT, new ActionListener<DeleteResponse>() {
                @Override
                public void onResponse(DeleteResponse response) {
                    System.out.println(response);
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
     * 异常处理
     */
    private static void deleteRequestException() throws IOException {
        try {
            DeleteResponse deleteResponse = CLIENT
                .delete(new DeleteRequest("posts", "1").setIfSeqNo(100).setIfPrimaryTerm(2), RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                System.out.println("异常处理");
            }
            e.printStackTrace();
        }
    }
}
