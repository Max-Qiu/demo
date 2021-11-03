package com.maxqiu.demo.search;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.search.Explanation;
import org.elasticsearch.action.explain.ExplainRequest;
import org.elasticsearch.action.explain.ExplainResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import com.maxqiu.demo.common.ClientUtils;

/**
 * @author Max_Qiu
 */
public class ExplainApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
    }

    public static void main(String[] args) {
        try {
            explainRequest();
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

    private static void explainRequest() throws IOException {
        ExplainRequest request = new ExplainRequest("contributors", "1");
        request.query(QueryBuilders.termQuery("user", "tanguy"));

        request.routing("routing");
        request.preference("_local");
        request.fetchSourceContext(new FetchSourceContext(true, new String[] {"user"}, null));
        request.storedFields(new String[] {"user"});

        ExplainResponse response = CLIENT.explain(request, RequestOptions.DEFAULT);

        // CLIENT.explainAsync(request, RequestOptions.DEFAULT, listener);

        String index = response.getIndex();
        String id = response.getId();
        boolean exists = response.isExists();
        boolean match = response.isMatch();
        boolean hasExplanation = response.hasExplanation();
        Explanation explanation = response.getExplanation();
        GetResult getResult = response.getGetResult();

        Map<String, Object> source = getResult.getSource();
        Map<String, DocumentField> fields = getResult.getFields();
    }
}
