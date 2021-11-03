package com.maxqiu.demo.miscellaneous;

import java.io.IOException;
import java.util.EnumSet;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.xpack.XPackInfoRequest;
import org.elasticsearch.client.xpack.XPackInfoResponse;

import com.maxqiu.demo.common.ClientUtils;

/**
 * @author Max_Qiu
 */
public class XPackInfoApi {
    private static final RestHighLevelClient CLIENT;

    static {
        // 获取客户端
        CLIENT = ClientUtils.create();
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
        XPackInfoRequest request = new XPackInfoRequest();
        // 启用详细模式。默认值为，false但true将返回更多信息。
        request.setVerbose(true);
        // 设置要检索的信息类别。默认设置为不返回任何信息，这对于检查是否已安装X-Pack很有用，而其他信息则不多。
        request.setCategories(EnumSet.of(XPackInfoRequest.Category.BUILD, XPackInfoRequest.Category.LICENSE,
            XPackInfoRequest.Category.FEATURES));

        // 执行
        XPackInfoResponse response = CLIENT.xpack().info(request, RequestOptions.DEFAULT);
        // 异步执行
        // CLIENT.xpack().infoAsync(request, RequestOptions.DEFAULT, listener);

        // BuildInfo 包含从其构建Elasticsearch的提交哈希以及创建x-pack模块的时间戳。
        XPackInfoResponse.BuildInfo build = response.getBuildInfo();

        // LicenseInfo 包含集群使用的许可证类型及其到期日期。
        XPackInfoResponse.LicenseInfo license = response.getLicenseInfo();

        // 基本许可证不会过期，并且将返回此常数。
        // assertThat(license.getExpiryDate(), is(greaterThan(Instant.now().toEpochMilli())));

        // FeatureSetsInfo包含Map从功能名称到功能信息的信息，例如该功能是否在当前许可下可用。
        XPackInfoResponse.FeatureSetsInfo features = response.getFeatureSetsInfo();
    }
}
