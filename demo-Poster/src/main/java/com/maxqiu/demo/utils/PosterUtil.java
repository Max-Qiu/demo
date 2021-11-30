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
