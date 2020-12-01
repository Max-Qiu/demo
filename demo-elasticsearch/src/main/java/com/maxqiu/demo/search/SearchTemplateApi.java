package com.maxqiu.demo.search;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;

import com.maxqiu.demo.CreateClient;

/**
 * @author Max_Qiu
 */
public class SearchTemplateApi {

    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            searchTemplateRequest();
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

    private static void searchTemplateRequest() throws IOException {
        SearchTemplateRequest request = new SearchTemplateRequest();
        // 搜索针对posts索引执行。
        request.setRequest(new SearchRequest("posts"));

        request.setScriptType(ScriptType.INLINE);
        // 模板定义了搜索源的结构。它是作为字符串传递的，因为mustache模板并不总是有效的JSON。
        request.setScript("{\"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } },\"size\" : \"{{size}}\"}");

        Map<String, Object> scriptParams = new HashMap<>(3);
        scriptParams.put("field", "title");
        scriptParams.put("value", "elasticsearch");
        scriptParams.put("size", 5);
        // 在运行搜索之前，将使用提供的参数来呈现模板。
        request.setScriptParams(scriptParams);

        // 一些设置
        // request.setSimulate(true);
        // request.setExplain(true);
        // request.setProfile(true);

        // 执行
        SearchTemplateResponse response = CLIENT.searchTemplate(request, RequestOptions.DEFAULT);

        // 异步执行
        // CLIENT.searchTemplateAsync(request, RequestOptions.DEFAULT, listener);

        SearchResponse searchResponse = response.getResponse();

        BytesReference source = response.getSource();

    }
}
