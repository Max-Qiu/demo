package com.maxqiu.demo.miscellaneous;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.xpack.XPackUsageRequest;
import org.elasticsearch.client.xpack.XPackUsageResponse;

import com.maxqiu.demo.CreateClient;

/**
 * 检索有关X-Pack功能使用情况的详细信息
 * 
 * @author Max_Qiu
 */
public class XPackUsageApi {

    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            xPackInfoRequest();
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
     * 获取X-Pack信息
     */
    private static void xPackInfoRequest() throws IOException {
        XPackUsageRequest request = new XPackUsageRequest();
        // 查询
        XPackUsageResponse response = CLIENT.xpack().usage(request, RequestOptions.DEFAULT);
        // 异步
        // CLIENT.xpack().usageAsync(request, RequestOptions.DEFAULT, listener);

        // 返回结果
        Map<String, Map<String, Object>> usages = response.getUsages();
        Map<String, Object> monitoringUsage = usages.get("monitoring");
        // assertThat(monitoringUsage.get("available"), is(true));
        // assertThat(monitoringUsage.get("enabled"), is(true));
        // assertThat(monitoringUsage.get("collection_enabled"), is(false));
    }
}
