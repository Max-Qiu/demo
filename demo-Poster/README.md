> 参考文档：
沉默王二：[Java生成二维码分享海报](http://www.itwanger.com/java/2019/11/14/java-qrcode-poster.html)
CSDN：[JAVA后台生成海报分享图片](https://blog.csdn.net/tanqingfu1/article/details/104891980)

# 功能

- 背景
    - 自定义背景图片
    - 自定义纯色背景
- 文字
    - 自定义字体文件
    - 短标题文本
    - 长内容文本（自动换行）
- 图片
    - 贴图

# 代码

## 海报对象实体

```java
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import lombok.Getter;

/**
 * 海报实体
 *
 * @author Max_Qiu
 */
@Getter
public class PosterEntity {
    private Background background;

    private List<Text> textList;

    private List<Image> imageList;

    /**
     * 创建海报对象
     *
     * @param background
     *            背景图片位置（resources目录下）
     * @param textList
     *            文本集合
     * @param imageList
     *            图片集合
     */
    public PosterEntity(Background background, List<Text> textList, List<Image> imageList) {
        this.background = background;
        this.textList = textList;
        this.imageList = imageList;
    }

    /**
     * 背景对象
     */
    @Getter
    public static class Background {
        private boolean fromImage;
        private InputStream background;
        private int width;
        private int height;
        private Color color;

        /**
         * 创建背景对象（从图片）
         *
         * @param backgroundPath
         *            背景图片路径
         */
        public Background(String backgroundPath) {
            this.fromImage = true;
            ClassLoader loader = getClass().getClassLoader();
            this.background = loader.getResourceAsStream(backgroundPath);
        }

        /**
         * 创建背景对象（指定宽高和背景色）
         *
         * @param width
         *            宽
         * @param height
         *            高
         * @param color
         *            颜色
         */
        public Background(int width, int height, Color color) {
            this.fromImage = false;
            this.width = width;
            this.height = height;
            this.color = color;
        }
    }

    /**
     * 文本
     *
     * @author Max_Qiu
     */
    @Getter
    public static class Text {
        private Font font;
        private Color color;
        private String text;
        private int textX;
        private int textY;
        private boolean needBr;
        private int width;

        /**
         * 创建文本实体（短文本，例如标题）
         *
         * @param fontPath
         *            字体文件路径（resources目录下）
         * @param size
         *            字体大小
         * @param color
         *            字体颜色
         * @param text
         *            文本
         * @param textX
         *            坐标X（开始位置为文本左上角）
         * @param textY
         *            坐标Y（开始位置为文本左上角）
         */
        public Text(String fontPath, float size, Color color, String text, int textX, int textY) {
            ClassLoader loader = getClass().getClassLoader();
            try {
                // 从 resources 目录下加载字体文本
                InputStream inputStream = Objects.requireNonNull(loader.getResourceAsStream(fontPath));
                // 创建字体
                this.font = Font.createFont(0, inputStream).deriveFont(size);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
            this.color = color;
            this.text = text;
            this.textX = textX;
            this.textY = textY;
            this.needBr = false;
        }

        /**
         * 创建文本实体（短文本，例如内容）
         *
         * @param fontPath
         *            字体文件路径（resources目录下）
         * @param size
         *            字体大小
         * @param color
         *            字体颜色
         * @param text
         *            文本
         * @param textX
         *            坐标X（开始位置为文本左上角）
         * @param textY
         *            坐标Y（开始位置为文本左上角）
         * @param width
         *            长文本自动换行宽度
         */
        public Text(String fontPath, float size, Color color, String text, int textX, int textY, int width) {
            ClassLoader loader = getClass().getClassLoader();
            try {
                // 从 resources 目录下加载字体文本
                InputStream inputStream = Objects.requireNonNull(loader.getResourceAsStream(fontPath));
                // 创建字体
                this.font = Font.createFont(0, inputStream).deriveFont(size);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
            this.color = color;
            this.text = text;
            this.textX = textX;
            this.textY = textY;
            this.needBr = true;
            this.width = width;
        }
    }

    /**
     * 图片
     *
     * @author Max_Qiu
     */
    @Getter
    public static class Image {
        private InputStream image;
        private int imageWidth;
        private int imageHeight;
        private int imageX;
        private int imageY;

        /**
         * 创建图片对象
         *
         * @param imagePath
         *            图片路径（resources目录下）
         * @param imageWidth
         *            图片宽度（输出宽度）
         * @param imageHeight
         *            图片高度（输出高度）
         * @param imageX
         *            图片X（开始位置为图片左上角）
         * @param imageY
         *            图片Y（开始位置为图片左上角）
         */
        public Image(String imagePath, int imageWidth, int imageHeight, int imageX, int imageY) {
            ClassLoader loader = getClass().getClassLoader();
            this.image = loader.getResourceAsStream(imagePath);
            this.imageWidth = imageWidth;
            this.imageHeight = imageHeight;
            this.imageX = imageX;
            this.imageY = imageY;
        }
    }
}
```

## 海报生成工具类

```java
package com.maxqiu.demo.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import sun.font.FontDesignMetrics;

/**
 * 海报生成工具类
 *
 * @author Max_Qiu
 */
public class PosterUtil {
    /**
     * 生成工具
     *
     * @param poster
     *            海报实体
     * @param outputFile
     *            目标文件
     * @param formatName
     *            图片格式（jpg/png）
     * @throws IOException
     *             IO异常
     */
    public static void drawPoster(PosterEntity poster, String outputFile, String formatName) throws IOException {
        BufferedImage bufferedImage;
        Graphics2D graphics;
        PosterEntity.Background background = poster.getBackground();
        if (background.isFromImage()) {
            // 读取背景图片
            bufferedImage = ImageIO.read(background.getBackground());
            // 获取 Graphics2D 对象
            graphics = (Graphics2D)bufferedImage.getGraphics();
        } else {
            // 创建背景图片
            bufferedImage =
                new BufferedImage(background.getWidth(), background.getHeight(), BufferedImage.TYPE_INT_RGB);
            // 获取 Graphics2D 对象
            graphics = (Graphics2D)bufferedImage.getGraphics();
            // 设置背景色
            graphics.setBackground(Color.WHITE);
            // 刷新颜色
            graphics.clearRect(0, 0, background.getWidth(), background.getHeight());
        }

        // 设置字体平滑
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // 遍历字体
        for (PosterEntity.Text text : poster.getTextList()) {
            if (text.isNeedBr()) {
                FontDesignMetrics metrics = FontDesignMetrics.getMetrics(text.getFont());
                String[] zhWraps = makeLineFeed(text.getText(), metrics, text.getWidth()).split("\n");
                // 字体
                graphics.setFont(text.getFont());
                // 颜色
                graphics.setColor(text.getColor());
                int y = text.getTextY();
                // 将每一行在海报背景上打印
                for (String wrap : zhWraps) {
                    graphics.drawString(wrap, text.getTextX(), y);
                    y += metrics.getHeight();
                }
            } else {
                // 字体
                graphics.setFont(text.getFont());
                // 颜色
                graphics.setColor(text.getColor());
                // 写入文本
                graphics.drawString(text.getText(), text.getTextX(), text.getTextY());
            }
        }

        // 遍历图片
        for (PosterEntity.Image image : poster.getImageList()) {
            // 读取贴图
            BufferedImage face = ImageIO.read(image.getImage());
            // 设置图片输出宽度和高度
            Image instance = face.getScaledInstance(image.getImageWidth(), image.getImageHeight(), Image.SCALE_DEFAULT);
            // 写入图片
            graphics.drawImage(instance, image.getImageX(), image.getImageY(), null);
        }
        // 释放资源
        graphics.dispose();

        // 输出文件
        ImageIO.write(bufferedImage, formatName, new File(outputFile));
    }

    /**
     * 切割字符串
     *
     * 本方法摘自：http://www.itwanger.com/java/2019/11/14/java-qrcode-poster.html
     *
     * @param zh
     *            文本
     * @param metrics
     *            FontDesignMetrics
     * @param maxWidth
     *            最大宽度
     */
    public static String makeLineFeed(String zh, FontDesignMetrics metrics, int maxWidth) {
        StringBuilder sb = new StringBuilder();
        int lineWidth = 0;
        for (int i = 0; i < zh.length(); i++) {
            char c = zh.charAt(i);
            sb.append(c);
            // 如果主动换行则跳过
            if (sb.toString().endsWith("\n")) {
                lineWidth = 0;
                continue;
            }
            // FontDesignMetrics 的 charWidth() 方法可以计算字符的宽度
            int charWidth = metrics.charWidth(c);
            lineWidth += charWidth;
            // 如果当前字符的宽度加上之前字符串的已有宽度超出了海报的最大宽度，则换行
            if (lineWidth >= maxWidth - charWidth) {
                lineWidth = 0;
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
```

## 测试

```
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
```
