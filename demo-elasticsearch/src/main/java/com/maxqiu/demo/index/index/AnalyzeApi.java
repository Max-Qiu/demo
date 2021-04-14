package com.maxqiu.demo.index.index;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.client.indices.DetailAnalyzeResponse;

import com.maxqiu.demo.CreateClient;

/**
 * 
 * 分析
 * 
 * @author Max_Qiu
 */
public class AnalyzeApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            // analyzeRequest();
            analyzeRequest2();
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

    private static void analyzeRequest() throws IOException {
        AnalyzeRequest request = AnalyzeRequest.withGlobalAnalyzer(
            // 内置分析仪
            "english",
            // 要包含的文本。多个字符串被视为多值字段
            "Some text to analyze", "Some more text to analyze");

        AnalyzeResponse response = CLIENT.indices().analyze(request, RequestOptions.DEFAULT);
        System.out.println(response);

        // AnalyzeToken 持有有关通过分析产生的各个代币的信息
        List<AnalyzeResponse.AnalyzeToken> tokens = response.getTokens();

        // 如果explain设置为true，则从detail() 方法返回信息：
        DetailAnalyzeResponse detail = response.detail();
    }

    private static void analyzeRequest2() throws IOException {
        // 1. 自定义令牌过滤器的配置
        Map<String, Object> stopFilter = new HashMap<>(2);
        stopFilter.put("type", "stop");
        stopFilter.put("stopwords", new String[] {"to"});
        AnalyzeRequest request = AnalyzeRequest
            // 配置令牌生成器
            .buildCustomAnalyzer("standard")
            // 配置字符过滤器
            .addCharFilter("html_strip")
            // 添加内置令牌过滤器
            .addTokenFilter("lowercase")
            // 添加自定义令牌过滤器
            .addTokenFilter(stopFilter).build("<b>Some text to analyze</b>");

        /// 2. 通过仅包含charfilters和tokenfilters来构建自定义规范化器
        // AnalyzeRequest request =
        // AnalyzeRequest.buildCustomNormalizer().addTokenFilter("lowercase").build("<b>BaR</b>");

        /// 3.使用现有索引中定义的分析器来分析文本：
        // AnalyzeRequest request = AnalyzeRequest.withIndexAnalyzer(
        // // 包含映射的索引
        // "my_index",
        // // 在此索引上定义要使用的分析器
        // "my_analyzer", "some text to analyze");

        /// 4. 您可以使用规范化器
        // AnalyzeRequest request = AnalyzeRequest.withNormalizer("my_index", "my_normalizer", "some text to analyze");

        /// 5. 使用索引中特定字段的映射来分析文本：
        // AnalyzeRequest request = AnalyzeRequest.withField("my_index", "my_field", "some text to analyze");

        // 设置explain为true将为响应添加更多详细信息
        request.explain(true);
        // 设置attributes允许您仅返回您感兴趣的令牌属性
        request.attributes("keyword", "type");

        AnalyzeResponse response = CLIENT.indices().analyze(request, RequestOptions.DEFAULT);
        System.out.println(response);

        // 异步
        // CLIENT.indices().analyzeAsync(request, RequestOptions.DEFAULT, listener);

    }
}
