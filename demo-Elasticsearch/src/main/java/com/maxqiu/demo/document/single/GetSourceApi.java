package com.maxqiu.demo.document.single;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.GetSourceRequest;
import org.elasticsearch.client.core.GetSourceResponse;

import com.maxqiu.demo.common.ClientUtils;

/**
 * 根据ID获取文档，且仅获取源
 *
 * @author Max_Qiu
 */
public class GetSourceApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
    }

    public static void main(String[] args) {
        try {
            getSourceRequest();
            // getSourceRequestConfig();
            // getSourceRequestAsync();
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

    private static void getSourceRequest() throws IOException {
        // 创建一个 GetSourceRequest 请求，设置索引为 posts ，文档ID 为 1
        GetSourceRequest request = new GetSourceRequest("posts", "1");
        // 获取返回
        GetSourceResponse response = CLIENT.getSource(request, RequestOptions.DEFAULT);
        System.out.println(response);
        // 转换为map
        Map<String, Object> source = response.getSource();
        System.out.println(source);
    }

    /**
     * 配置查询
     */
    private static void getSourceRequestConfig() throws IOException {
        GetSourceRequest request = new GetSourceRequest("posts", "1");

        /// 1. 为特定字段配置包含
        // String[] includes = new String[] {"message", "*Date"};// 包含字段
        // String[] excludes = Strings.EMPTY_ARRAY;// 排除的字段
        // FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);// 配置
        // request.fetchSourceContext(fetchSourceContext);

        /// 2. 为特定字段配置屏蔽
        // String[] includes = Strings.EMPTY_ARRAY;// 包含字段
        // String[] excludes = new String[] {"message"};// 排除的字段
        // FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);// 配置
        // request.fetchSourceContext(fetchSourceContext);

        // 设置路由
        request.routing("routing");

        // 偏好值
        request.preference("preference");

        // 实时（默认情况下true）
        request.realtime(false);

        // 在检索文档之前执行刷新（默认情况下false）
        request.refresh(true);

        // 查询并输出
        GetSourceResponse response = CLIENT.getSource(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * 异步查询
     */
    private static void getSourceRequestAsync() throws IOException {
        // 创建一个 request 请求 posts 索引，文档ID为1
        GetSourceRequest request = new GetSourceRequest("posts", "1");
        // 同步查询
        Cancellable cancellable =
            CLIENT.getSourceAsync(request, RequestOptions.DEFAULT, new ActionListener<GetSourceResponse>() {
                @Override
                public void onResponse(GetSourceResponse response) {
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
}
