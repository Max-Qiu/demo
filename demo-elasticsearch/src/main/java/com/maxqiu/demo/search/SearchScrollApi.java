package com.maxqiu.demo.search;

import java.io.IOException;

import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.maxqiu.demo.CreateClient;

/**
 * 
 * 滚动搜索
 * 
 * @author Max_Qiu
 */
public class SearchScrollApi {

    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            searchRequest();
            // searchRequestConfig();
            // searchSourceBuilder();
            // matchQueryBuilder();
            // queryBuilders();
            // sort();
            // sourceFiltering();
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

    private static void searchRequest() throws IOException {
        // 创建SearchRequest和及其对应的SearchSourceBuilder。
        SearchRequest searchRequest = new SearchRequest("posts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", "Elasticsearch"));
        // 还可以选择设置size来控制一次检索多少个结果。
        searchSourceBuilder.size(10);
        searchRequest.source(searchSourceBuilder);
        // 设置滚动间隔
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        // 读取返回的滚动ID，该滚动ID指向保持活动状态的搜索上下文，并且在以下搜索滚动调用中将需要使用该滚动ID。
        String scrollId = searchResponse.getScrollId();
        // 检索第一批搜索结果
        SearchHits hits = searchResponse.getHits();

        // 创建SearchScrollRequest通过设置所需的滚动ID和滚动间隔
        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        scrollRequest.scroll(TimeValue.timeValueSeconds(30));
        SearchResponse searchScrollResponse = CLIENT.scroll(scrollRequest, RequestOptions.DEFAULT);
        // 阅读新的滚动ID，它指向保持活动状态的搜索上下文，在下面的搜索滚动调用中将需要使用此滚动ID。
        scrollId = searchScrollResponse.getScrollId();
        // 检索另一批搜索结果
        hits = searchScrollResponse.getHits();
        // assertEquals(3, hits.getTotalHits().value);
        // assertEquals(1, hits.getHits().length);
        // assertNotNull(scrollId);

        // 设置滚动间隔为 TimeValue
        scrollRequest.scroll(TimeValue.timeValueSeconds(60L));
        scrollRequest.scroll("60s");

        // 异步执行
        // CLIENT.scrollAsync(scrollRequest, RequestOptions.DEFAULT, scrollListener);
    }

    private static void fullExample() throws IOException {
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        SearchRequest searchRequest = new SearchRequest("posts");
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", "Elasticsearch"));
        searchRequest.source(searchSourceBuilder);
        // 通过发送初始值来初始化搜索上下文 SearchRequest
        SearchResponse searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        // 通过循环调用Search Scroll API检索所有搜索结果，直到没有文档返回
        while (searchHits != null && searchHits.length > 0) {
            // 处理返回的搜索结果

            // 创建一个新的SearchScrollRequest保存最后返回的滚动标识符和滚动间隔
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            searchResponse = CLIENT.scroll(scrollRequest, RequestOptions.DEFAULT);
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();
        }

        // 滚动完成后，清除滚动上下文
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = CLIENT.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        boolean succeeded = clearScrollResponse.isSucceeded();
    }
}
