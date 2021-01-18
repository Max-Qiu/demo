package com.maxqiu.demo.P04_Builder.demo;

/**
 * 基础房子
 * 
 * @author Max_Qiu
 */
public abstract class AbstractHouse {

    /**
     * 打地基
     */
    public abstract void buildBasic();

    /**
     * 砌墙
     */
    public abstract void buildWalls();

    /**
     * 封顶
     */
    public abstract void roofed();

    /**
     * 建造
     */
    public void build() {
        buildBasic();
        buildWalls();
        roofed();
    }

}
