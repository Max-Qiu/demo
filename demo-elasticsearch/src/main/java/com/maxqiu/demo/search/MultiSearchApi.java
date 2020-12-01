package com.maxqiu.demo.search;

import java.io.IOException;

import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.maxqiu.demo.CreateClient;

/**
 * 该multiSearchAPIsearch 在单个http请求中并行执行多个请求。
 * 
 * @author Max_Qiu
 */
public class MultiSearchApi {

    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            multiSearchRequest();
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

    private static void multiSearchRequest() throws IOException {
        // 创建一个空的MultiSearchRequest。
        MultiSearchRequest request = new MultiSearchRequest();

        // 创建一个空文件SearchRequest，然后像填充常规文件一样填充它search。
        SearchRequest firstSearchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "kimchy"));
        firstSearchRequest.source(searchSourceBuilder);
        // 将添加SearchRequest到中MultiSearchRequest。
        request.add(firstSearchRequest);

        // 建立第二个SearchRequest并将其添加到中MultiSearchRequest。
        SearchRequest secondSearchRequest = new SearchRequest();
        // 将请求限制到某个索引
        // SearchRequest searchRequest = new SearchRequest("posts");
        searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "luca"));
        secondSearchRequest.source(searchSourceBuilder);
        request.add(secondSearchRequest);

        // 执行
        MultiSearchResponse response = CLIENT.msearch(request, RequestOptions.DEFAULT);

        // 异步执行
        // CLIENT.msearchAsync(request, RequestOptions.DEFAULT, listener);

        // 首次搜索的项目
        MultiSearchResponse.Item firstResponse = response.getResponses()[0];
        // 成功完成，因此getFailure返回null。
        if (firstResponse.getFailure() == null) {
            System.out.println("成功完成，因此getFailure返回null。");
        }
        SearchResponse searchResponse = firstResponse.getResponse();
        // 而且还有一个SearchResponse在 getResponse。
        if (searchResponse.getHits().getTotalHits().value == 4) {
            System.out.println("而且还有一个SearchResponse在 getResponse。");
        }

        // 第二次搜索的项目。
        // MultiSearchResponse.Item secondResponse = response.getResponses()[1];
        // assertNull(secondResponse.getFailure());
        // searchResponse = secondResponse.getResponse();
        // assertEquals(1, searchResponse.getHits().getTotalHits().value);

    }

}
