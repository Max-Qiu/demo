package com.maxqiu.demo.P04_Builder.demo;

/**
 * 高楼
 * 
 * @author Max_Qiu
 */
public class HighBuilding extends AbstractHouse {

    @Override
    public void buildBasic() {
        System.out.println(" 高楼打地基 ");
    }

    @Override
    public void buildWalls() {
        System.out.println(" 高楼砌墙 ");
    }

    @Override
    public void roofed() {
        System.out.println(" 高楼封顶 ");
    }

}
