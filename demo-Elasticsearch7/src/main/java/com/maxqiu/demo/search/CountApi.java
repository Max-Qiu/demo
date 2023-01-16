package com.maxqiu.demo.search;

import java.io.IOException;

import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.maxqiu.demo.common.ClientUtils;

/**
 * @author Max_Qiu
 */
public class CountApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
    }

    public static void main(String[] args) {
        try {
            countRequest();
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

    private static void countRequest() throws IOException {
        CountRequest countRequest = new CountRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        countRequest.source(searchSourceBuilder);

        // CountRequest countRequest = new CountRequest("blog").routing("routing")
        // .indicesOptions(IndicesOptions.lenientExpandOpen()).preference("_local");

        // SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // sourceBuilder.query(QueryBuilders.termQuery("user", "kimchy"));

        // CountRequest countRequest = new CountRequest();
        // countRequest.indices("blog", "author");
        // countRequest.source(sourceBuilder);

        CountResponse countResponse = CLIENT.count(countRequest, RequestOptions.DEFAULT);
        // CLIENT.countAsync(countRequest, RequestOptions.DEFAULT, listener);

        long count = countResponse.getCount();
        RestStatus status = countResponse.status();
        Boolean terminatedEarly = countResponse.isTerminatedEarly();

        int totalShards = countResponse.getTotalShards();
        int skippedShards = countResponse.getSkippedShards();
        int successfulShards = countResponse.getSuccessfulShards();
        int failedShards = countResponse.getFailedShards();
        for (ShardSearchFailure failure : countResponse.getShardFailures()) {
            // failures should be handled here
        }
    }
}
