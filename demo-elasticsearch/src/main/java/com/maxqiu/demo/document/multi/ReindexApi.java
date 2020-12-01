package com.maxqiu.demo.document.multi;

import java.io.IOException;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;

import com.maxqiu.demo.CreateClient;

/**
 * 
 * 重建索引（用于索引拷贝，多索引合并）
 * 
 * @author Max_Qiu
 */
public class ReindexApi {

    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            reindexRequest();
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

    private static void reindexRequest() throws IOException {
        // 创建一个 ReindexRequest 请求
        ReindexRequest request = new ReindexRequest();
        // 添加要复制的来源列表
        request.setSourceIndices("source1", "source2");
        // 添加目标索引
        request.setDestIndex("dest");

        // 执行
        BulkByScrollResponse bulkResponse = CLIENT.reindex(request, RequestOptions.DEFAULT);
    }
}
