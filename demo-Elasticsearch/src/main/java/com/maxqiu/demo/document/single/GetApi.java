package com.maxqiu.demo.document.single;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import com.maxqiu.demo.common.ClientUtils;

/**
 * 根据ID获取相关文档
 *
 * @author Max_Qiu
 */
public class GetApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
    }

    public static void main(String[] args) {
        try {
            getRequest();
            // getRequestConfig();
            // storedFields();
            // getRequestAsync();
            // getRequestToOtherType();
            // getRequestException();
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
     * 根据文档ID，索引名称查询数据
     */
    private static void getRequest() throws IOException {
        // 创建一个 GetRequest 请求，设置索引为 posts ，文档ID 为 1
        GetRequest request = new GetRequest("posts", "1");
        // 同步查询
        GetResponse response = CLIENT.get(request, RequestOptions.DEFAULT);
        // 输出查询结果
        System.out.println(response);
    }

    /**
     * 配置查询
     */
    private static void getRequestConfig() throws IOException {
        GetRequest request = new GetRequest("posts", "1");

        /// 1. 禁用源检索（默认为 FETCH_SOURCE 返还所有字段）
        // request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);

        /// 2. 为特定字段配置包含
        // String[] includes = new String[] {"message", "*Date"};// 包含字段
        // String[] excludes = Strings.EMPTY_ARRAY;// 排除的字段
        // FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);// 配置
        // request.fetchSourceContext(fetchSourceContext);

        /// 3. 为特定字段配置屏蔽
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

        // 版本（版本不对查询报错）
        request.version(2);

        // 查询并输出
        GetResponse response = CLIENT.get(request, RequestOptions.DEFAULT);
        System.out.println(response);

    }

    /**
     * 配置针对特定存储字段的检索
     */
    private static void storedFields() throws IOException {
        GetRequest request = new GetRequest("posts", "1");
        request.fetchSourceContext(FetchSourceContext.FETCH_SOURCE);
        request.storedFields("message");
        GetResponse response = CLIENT.get(request, RequestOptions.DEFAULT);
        System.out.println(response);
        // TODO 报错
        String message = response.getField("message").getValue();
        System.out.println(message);
    }

    /**
     * 异步查询
     */
    private static void getRequestAsync() throws IOException {
        // 创建一个 request 请求 posts 索引，文档ID为1
        GetRequest request = new GetRequest("posts", "1");
        // 同步查询
        Cancellable cancellable = CLIENT.getAsync(request, RequestOptions.DEFAULT, new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse response) {
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
     * 获取后，文档转换为其他格式
     *
     */
    private static void getRequestToOtherType() throws IOException {
        // 创建一个 request 请求 posts 索引，文档ID为1
        GetRequest request = new GetRequest("posts", "1");
        // 同步查询
        GetResponse response = CLIENT.get(request, RequestOptions.DEFAULT);
        // 输出查询结果
        System.out.println(response);
        String index = response.getIndex();
        String id = response.getId();
        if (response.isExists()) {
            // 获取文档版本
            long version = response.getVersion();
            System.out.println("版本：" + version);
            // 将该文档检索为 String
            String sourceAsString = response.getSourceAsString();
            System.out.println("String文档：" + sourceAsString);
            // 将该文档检索为 Map<String, Object>
            Map<String, Object> sourceAsMap = response.getSourceAsMap();
            System.out.println("Map文档：" + sourceAsMap);
            // 将该文档检索为 byte[]
            byte[] sourceAsBytes = response.getSourceAsBytes();
            System.out.println("byte文档：" + Arrays.toString(sourceAsBytes));
        } else {
            // 处理找不到文档的情况。
            System.out.println("找不到文档");
        }
    }

    /**
     * 异常处理
     */
    private static void getRequestException() throws IOException {
        try {
            GetRequest request = new GetRequest("does_not_exist", "11111");
            GetResponse getResponse = CLIENT.get(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                System.out.println("索引不存在");
            }
            e.printStackTrace();
        }

        try {
            GetRequest request = new GetRequest("posts", "1").version(2222);
            GetResponse getResponse = CLIENT.get(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                System.out.println("文档版本不正确");
            }
            e.printStackTrace();
        }
    }
}
