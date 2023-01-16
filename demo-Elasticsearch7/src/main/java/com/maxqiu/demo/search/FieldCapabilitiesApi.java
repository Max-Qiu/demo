package com.maxqiu.demo.search;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.fieldcaps.FieldCapabilities;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesRequest;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import com.maxqiu.demo.common.ClientUtils;

/**
 * @author Max_Qiu
 */
public class FieldCapabilitiesApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
    }

    public static void main(String[] args) {
        try {
            fieldCapabilitiesRequest();
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

    private static void fieldCapabilitiesRequest() throws IOException {
        FieldCapabilitiesRequest request =
            new FieldCapabilitiesRequest().fields("user").indices("posts", "authors", "contributors");

        // 设置IndicesOptions控制如何解决不可用的索引以及如何扩展通配符表达式。
        request.indicesOptions(IndicesOptions.lenientExpandOpen());

        FieldCapabilitiesResponse response = CLIENT.fieldCaps(request, RequestOptions.DEFAULT);

        // 异步执行
        // client.fieldCapsAsync(request, RequestOptions.DEFAULT, listener);

        // 一个Map条目字段的可能的类型，在这种情况下keyword和text。
        Map<String, FieldCapabilities> userResponse = response.getField("user");
        FieldCapabilities textCapabilities = userResponse.get("keyword");

        boolean isSearchable = textCapabilities.isSearchable();
        boolean isAggregatable = textCapabilities.isAggregatable();

        // user字段类型为的所有索引keyword。
        String[] indices = textCapabilities.indices();
        // user字段不可搜索的这些索引的子集；如果始终可搜索，则返回null。
        String[] nonSearchableIndices = textCapabilities.nonSearchableIndices();
        // 这些索引的另一个子集，其中的user字段不可聚合；如果始终可聚合，则为null。
        String[] nonAggregatableIndices = textCapabilities.nonAggregatableIndices();

    }
}
