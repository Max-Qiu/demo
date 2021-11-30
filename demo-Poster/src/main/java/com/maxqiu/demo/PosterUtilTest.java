package com.maxqiu.demo;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.maxqiu.demo.utils.PosterEntity;
import com.maxqiu.demo.utils.PosterUtil;

/**
 * 测试
 *
 * @author Max_Qiu
 */
public class PosterUtilTest {
    /**
     * 文件输出根路径
     */
    private static final String BASE_OUTPUT_FILE_PATH = "/basePath/";

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // 创建背景
        PosterEntity.Background background = new PosterEntity.Background("poster/background.jpg");
        // 也可以使用纯色背景
        PosterEntity.Background background2 = new PosterEntity.Background(1700, 1200, Color.WHITE);

        // 创建多个文本
        List<PosterEntity.Text> textList = new ArrayList<>();
        // 分别指定字体、大小、颜色（RGB创建颜色）、内容、坐标x、坐标y
        PosterEntity.Text title =
            new PosterEntity.Text("poster/SourceHanSerifCN-Bold.otf", 120, new Color(213, 28, 28), "标题", 600, 200);
        textList.add(title);
        String contentStr = "这是一段测试文本！This is a test text!这是一段测试文本！\n这是一段测试文本！This is a test text!";
        // 分别指定字体、大小、颜色（也可以使用枚举）、内容、坐标x、坐标y、长文本最大宽度
        PosterEntity.Text content =
            new PosterEntity.Text("poster/SourceHanSerifCN-Regular.otf", 50, Color.BLACK, contentStr, 400, 300, 800);
        textList.add(content);

        // 创建多个图片
        List<PosterEntity.Image> imageList = new ArrayList<>();
        PosterEntity.Image image1 = new PosterEntity.Image("poster/face.jpg", 400, 400, 300, 600);
        imageList.add(image1);
        PosterEntity.Image image2 = new PosterEntity.Image("poster/Q.png", 100, 100, 1200, 800);
        imageList.add(image2);

        // 创建海报实体
        PosterEntity posterEntity = new PosterEntity(background, textList, imageList);
        // 指定输出后的文件格式（一般同背景图片文件格式）
        String formatName = "jpg";
        // 指定文件名（此处使用UUID）
        String fileName = UUID.randomUUID() + "." + formatName;
        // 生成
        try {
            PosterUtil.drawPoster(posterEntity, BASE_OUTPUT_FILE_PATH + fileName, formatName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("生成成功！");
        System.out.println("生成时长: " + (System.currentTimeMillis() - startTime) / 1000.0 + "s");
        System.out.println("文件路径: " + BASE_OUTPUT_FILE_PATH + fileName);

    }
}
