package com.maxqiu.demo.search;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.MultiSearchTemplateRequest;
import org.elasticsearch.script.mustache.MultiSearchTemplateResponse;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;

import com.maxqiu.demo.CreateClient;

/**
 * 
 * 该multiSearchTemplateAPI在单个http请求中并行执行多个search template请求。
 * 
 * @author Max_Qiu
 */
public class MultiSearchTemplateApi {

    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            multiSearchTemplateRequest();
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

    private static void multiSearchTemplateRequest() throws IOException {
        String[] searchTerms = {"elasticsearch", "logstash", "kibana"};

        // 创建一个空的MultiSearchTemplateRequest。
        MultiSearchTemplateRequest templateRequest = new MultiSearchTemplateRequest();
        for (String searchTerm : searchTerms) {
            // 创建一个或多个SearchTemplateRequest对象，然后像填充常规对象一样填充它们search template。
            SearchTemplateRequest request = new SearchTemplateRequest();
            request.setRequest(new SearchRequest("posts"));

            request.setScriptType(ScriptType.INLINE);
            request.setScript("{" + "  \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } },"
                + "  \"size\" : \"{{size}}\"" + "}");

            Map<String, Object> scriptParams = new HashMap<>();
            scriptParams.put("field", "title");
            scriptParams.put("value", searchTerm);
            scriptParams.put("size", 5);
            request.setScriptParams(scriptParams);

            // 将添加SearchTemplateRequest到中MultiSearchTemplateRequest。
            templateRequest.add(request);
        }

        MultiSearchTemplateResponse multiResponse = CLIENT.msearchTemplate(templateRequest, RequestOptions.DEFAULT);

        // 异步执行
        // client.msearchTemplateAsync(multiRequest, RequestOptions.DEFAULT, listener);

        // 迭代
        for (MultiSearchTemplateResponse.Item item : multiResponse.getResponses()) {
            if (item.isFailure()) {
                String error = item.getFailureMessage();
            } else {
                SearchTemplateResponse searchTemplateResponse = item.getResponse();
                SearchResponse searchResponse = searchTemplateResponse.getResponse();
                searchResponse.getHits();
            }
        }
    }
}
