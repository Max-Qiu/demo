package com.maxqiu.demo.utils;

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
