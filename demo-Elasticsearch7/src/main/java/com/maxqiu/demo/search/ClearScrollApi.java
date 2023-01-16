package com.maxqiu.demo.search;

import java.io.IOException;

import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import com.maxqiu.demo.common.ClientUtils;

/**
 * 滚动超时时，将自动删除<br>
 * Search Scroll API使用的搜索上下文。<br>
 * 但是建议不要使用Clear Scroll API尽快释放搜索上下文。
 *
 * @author Max_Qiu
 */
public class ClearScrollApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
    }

    public static void main(String[] args) {
        try {
            clearScrollRequest("scrollId");
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

    private static void clearScrollRequest(String scrollId) throws IOException {
        // 创建一个新的 ClearScrollRequest
        ClearScrollRequest request = new ClearScrollRequest();

        // 将滚动ID添加到要清除的滚动标识符列表中
        request.addScrollId(scrollId);

        // 或者添加一个集合
        // request.setScrollIds(scrollIds);

        ClearScrollResponse response = CLIENT.clearScroll(request, RequestOptions.DEFAULT);

        // 异步执行
        // CLIENT.clearScrollAsync(request, RequestOptions.DEFAULT, listener);

        // 如果请求成功，则返回true
        boolean success = response.isSucceeded();
        // 返回已发布搜索上下文的数量
        int released = response.getNumFreed();
    }
}
