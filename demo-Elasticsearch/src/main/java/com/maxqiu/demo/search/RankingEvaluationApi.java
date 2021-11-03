package com.maxqiu.demo.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.rankeval.EvaluationMetric;
import org.elasticsearch.index.rankeval.PrecisionAtK;
import org.elasticsearch.index.rankeval.RankEvalRequest;
import org.elasticsearch.index.rankeval.RankEvalResponse;
import org.elasticsearch.index.rankeval.RankEvalSpec;
import org.elasticsearch.index.rankeval.RatedDocument;
import org.elasticsearch.index.rankeval.RatedRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.maxqiu.demo.common.ClientUtils;

/**
 * @author Max_Qiu
 */
public class RankingEvaluationApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
    }

    public static void main(String[] args) {
        try {
            rankEvalRequest();
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

    private static void rankEvalRequest() throws IOException {
        EvaluationMetric metric = new PrecisionAtK();
        List<RatedDocument> ratedDocs = new ArrayList<>();
        ratedDocs.add(new RatedDocument("posts", "1", 1));
        SearchSourceBuilder searchQuery = new SearchSourceBuilder();
        searchQuery.query(QueryBuilders.matchQuery("user", "kimchy"));
        RatedRequest ratedRequest = new RatedRequest("kimchy_query", ratedDocs, searchQuery);
        List<RatedRequest> ratedRequests = Collections.singletonList(ratedRequest);
        RankEvalSpec specification = new RankEvalSpec(ratedRequests, metric);
        RankEvalRequest request = new RankEvalRequest(specification, new String[] {"posts"});
        RankEvalResponse response = CLIENT.rankEval(request, RequestOptions.DEFAULT);

        // 异步
        // CLIENT.rankEvalAsync(request, RequestOptions.DEFAULT, listener);

        // 结果
        // double evaluationResult = response.getMetricScore();
        // assertEquals(1.0 / 3.0, evaluationResult, 0.0);
        // Map<String, EvalQueryQuality> partialResults =
        // response.getPartialResults();
        // EvalQueryQuality evalQuality =
        // partialResults.get("kimchy_query");
        // assertEquals("kimchy_query", evalQuality.getId());
        // double qualityLevel = evalQuality.metricScore();
        // assertEquals(1.0 / 3.0, qualityLevel, 0.0);
        // List<RatedSearchHit> hitsAndRatings = evalQuality.getHitsAndRatings();
        // RatedSearchHit ratedSearchHit = hitsAndRatings.get(2);
        // assertEquals("3", ratedSearchHit.getSearchHit().getId());
        // assertFalse(ratedSearchHit.getRating().isPresent());
        // MetricDetail metricDetails = evalQuality.getMetricDetails();
        // String metricName = metricDetails.getMetricName();
        // assertEquals(PrecisionAtK.NAME, metricName);
        // PrecisionAtK.Detail detail = (PrecisionAtK.Detail) metricDetails;
        // assertEquals(1, detail.getRelevantRetrieved());
        // assertEquals(3, detail.getRetrieved());
    }
}
