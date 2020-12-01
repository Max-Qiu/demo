package com.maxqiu.demo.search;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;

import com.maxqiu.demo.CreateClient;

/**
 * @author Max_Qiu
 */
public class SearchApi {

    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            // searchRequest();
            // searchRequestConfig();
            // searchSourceBuilder();
            // matchQueryBuilder();
            // queryBuilders();
            // sort();
            // sourceFiltering();
            highlightBuilder();
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
     * 简单查询
     */
    private static void searchRequest() throws IOException {
        // 创建SearchRequest。如果没有参数，这将选型所有索引
        SearchRequest searchRequest = new SearchRequest();

        // 大多数搜索参数已添加到中SearchSourceBuilder。它为搜索请求正文中的所有内容提供设置器。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 向中SearchSourceBuilder添加match_all查询
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        // 将添加SearchSourceBuilder到中SearchRequest
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse);
    }

    /**
     * 配置查询请求
     */
    private static void searchRequestConfig() throws IOException {
        // 创建SearchRequest。同时指定搜索的索引
        SearchRequest searchRequest = new SearchRequest("posts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        // 设置路由
        // searchRequest.routing("routing");

        // 设置IndicesOptions控制如何解决不可用的索引以及如何扩展通配符表达式
        searchRequest.indicesOptions(IndicesOptions.lenientExpandOpen());

        // 使用首选项参数，例如执行搜索以偏爱本地分片。默认值是随机分片。
        searchRequest.preference("_local");

        SearchResponse searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse);
    }

    /**
     * 配置查询条件
     */
    private static void searchSourceBuilder() throws IOException {
        // 创建SearchRequest。同时指定搜索的索引
        SearchRequest searchRequest = new SearchRequest("posts");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        // 设置from确定结果索引以开始搜索的选项。预设为0。
        searchSourceBuilder.from(0);
        // 设置size用于确定要返回的搜索命中次数的选项。默认为10
        searchSourceBuilder.size(5);
        // 设置一个可选的超时时间，以控制允许搜索的时间。
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        // SearchSourceBuilder添加SearchRequest
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse);
    }

    /**
     * 搜索查询是使用QueryBuilder对象创建的。<br>
     * 一个QueryBuilder存在通过Elasticsearch支持的每一个搜索查询类型查询DSL。
     */
    private static void matchQueryBuilder() throws IOException {
        // 创建一个匹配查询
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("user", "kimchy");
        // 对匹配查询启用模糊匹配
        matchQueryBuilder.fuzziness(Fuzziness.AUTO);
        // 在匹配查询中设置前缀长度选项
        matchQueryBuilder.prefixLength(3);
        // 设置最大扩展选项以控制查询的模糊过程
        matchQueryBuilder.maxExpansions(10);

        SearchRequest searchRequest = new SearchRequest("posts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 将MatchQueryBuilder添加到SearchSourceBuilder
        searchSourceBuilder.query(matchQueryBuilder);
        // SearchSourceBuilder添加SearchRequest
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse);

    }

    /**
     * 搜索查询是使用QueryBuilder对象创建的。<br>
     * 一个QueryBuilder存在通过Elasticsearch支持的每一个搜索查询类型查询DSL。
     */
    private static void queryBuilders() throws IOException {
        // 创建一个匹配查询
        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("user", "kimchy")
            // 对匹配查询启用模糊匹配
            .fuzziness(Fuzziness.AUTO)
            // 在匹配查询中设置前缀长度选项
            .prefixLength(3)
            // 设置最大扩展选项以控制查询的模糊过程
            .maxExpansions(10);

        SearchRequest searchRequest = new SearchRequest("posts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 将MatchQueryBuilder添加到SearchSourceBuilder
        searchSourceBuilder.query(matchQueryBuilder);
        // SearchSourceBuilder添加SearchRequest
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse);

    }

    /**
     * 排序
     */
    private static void sort() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        // 降序排序_score（默认）
        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        // 也可以按时间字段升序排序（_id字段将在以后删除，不建议使用）
        // searchSourceBuilder.sort(new FieldSortBuilder("_id").order(SortOrder.ASC));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse);
    }

    /**
     * 数据源过滤<br>
     * 默认情况下，搜索时同时返回源数据
     */
    private static void sourceFiltering() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());

        // 不显示源数据
        sourceBuilder.fetchSource(false);

        // 设置特定的显示和排除字段
        // String[] includeFields = new String[] {"title", "innerObject.*"};
        // String[] excludeFields = new String[] {"user"};
        // sourceBuilder.fetchSource(includeFields, excludeFields);

        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse);
    }

    /**
     * 高亮显示
     */
    private static void highlightBuilder() throws IOException {
        // 创建一个匹配查询
        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("user", "kimchy")
            // 对匹配查询启用模糊匹配
            .fuzziness(Fuzziness.AUTO)
            // 在匹配查询中设置前缀长度选项
            .prefixLength(3)
            // 设置最大扩展选项以控制查询的模糊过程
            .maxExpansions(10);

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 创建一个新的 HighlightBuilder
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 为 user 字段设置高亮查询
        HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("user");
        // 设置高亮类型
        highlightUser.highlighterType("unified");
        // 高亮查询前缀
        highlightBuilder.preTags("<span>");
        // 高亮查询后缀
        highlightBuilder.postTags("</span>");
        // 将字段高亮设置添加到HighlightBuilder
        highlightBuilder.field(highlightUser);
        // searchSourceBuilder 添加高亮查询设置
        searchSourceBuilder.highlighter(highlightBuilder);
        // searchSourceBuilder 设置查询条件
        searchSourceBuilder.query(matchQueryBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse);

    }

    /**
     * TODO <br>
     * 向量
     */
    private static void aggregationBuilders() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_company").field("company.keyword");
        aggregation.subAggregation(AggregationBuilders.avg("average_age").field("age"));
        searchSourceBuilder.aggregation(aggregation);
    }

    private static void termSuggestionBuilder() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermSuggestionBuilder termSuggestionBuilder = SuggestBuilders.termSuggestion("user").text("kmichy");
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("suggest_user", termSuggestionBuilder);
        searchSourceBuilder.suggest(suggestBuilder);

    }

    private static void profile() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.profile(true);
    }

    private static void async() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        Cancellable cancellable =
            CLIENT.searchAsync(searchRequest, RequestOptions.DEFAULT, new ActionListener<SearchResponse>() {
                @Override
                public void onResponse(SearchResponse response) {
                    System.out.println("收到返回response：" + response);
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
     * TODO 详细返回结果待整理<br>
     * 
     * 搜索返回值详解
     */
    private static void searchResponse(SearchResponse searchResponse) {
        RestStatus status = searchResponse.status();
        TimeValue took = searchResponse.getTook();
        Boolean terminatedEarly = searchResponse.isTerminatedEarly();
        boolean timedOut = searchResponse.isTimedOut();

        int totalShards = searchResponse.getTotalShards();
        int successfulShards = searchResponse.getSuccessfulShards();
        int failedShards = searchResponse.getFailedShards();
        for (ShardSearchFailure failure : searchResponse.getShardFailures()) {
            // failures should be handled here
        }

        SearchHits hits = searchResponse.getHits();

        TotalHits totalHits = hits.getTotalHits();
        // the total number of hits, must be interpreted in the context of totalHits.relation
        long numHits = totalHits.value;
        // whether the number of hits is accurate (EQUAL_TO) or a lower bound of the total (GREATER_THAN_OR_EQUAL_TO)
        TotalHits.Relation relation = totalHits.relation;
        float maxScore = hits.getMaxScore();

        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            // do something with the SearchHit
            String index = hit.getIndex();
            String id = hit.getId();
            float score = hit.getScore();

            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String documentTitle = (String)sourceAsMap.get("title");
            List<Object> users = (List<Object>)sourceAsMap.get("user");
            Map<String, Object> innerObject = (Map<String, Object>)sourceAsMap.get("innerObject");

        }

        for (SearchHit hit : hits.getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlight = highlightFields.get("title");
            Text[] fragments = highlight.fragments();
            String fragmentString = fragments[0].string();
        }

        Aggregations aggregations = searchResponse.getAggregations();
        Terms byCompanyAggregation = aggregations.get("by_company");
        MultiBucketsAggregation.Bucket elasticBucket = byCompanyAggregation.getBucketByKey("Elastic");
        Avg averageAge = elasticBucket.getAggregations().get("average_age");
        double avg = averageAge.getValue();
    }

}
