package com.maxqiu.demo.document.single;

import java.io.IOException;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import com.maxqiu.demo.CreateClient;

/**
 * 判断文档是否存在
 * 
 * @author Max_Qiu
 */
public class ExistsApi {

    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = CreateClient.createClient();
    }

    public static void main(String[] args) {
        try {
            // checkDocumentExists();
            checkDocumentExistsAsync();
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
     * 检查文档是否存在
     */
    private static void checkDocumentExists() throws IOException {
        // 创建一个 GetRequest 请求，设置索引为 posts ，文档ID 为 1
        GetRequest request = new GetRequest("posts", "1");
        // 禁用获取_source。
        request.fetchSourceContext(new FetchSourceContext(false));
        // 禁用获取存储的字段。
        request.storedFields("_none_");
        // 查询
        boolean exists = CLIENT.exists(request, RequestOptions.DEFAULT);
        System.out.println("是否存在：" + exists);
    }

    /**
     * 异步检查
     */
    private static void checkDocumentExistsAsync() throws IOException {
        GetRequest request = new GetRequest("posts", "1");
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        // 异步查询
        Cancellable cancellable = CLIENT.existsAsync(request, RequestOptions.DEFAULT, new ActionListener<Boolean>() {
            @Override
            public void onResponse(Boolean aBoolean) {
                System.out.println("是否存在：" + aBoolean);
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
