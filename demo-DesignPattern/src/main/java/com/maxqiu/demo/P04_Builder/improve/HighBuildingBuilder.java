package com.maxqiu.demo.P04_Builder.improve;

/**
 * 高楼建造者
 * 
 * @author Max_Qiu
 */
public class HighBuildingBuilder extends HouseBuilder {

    @Override
    public void buildBasic() {
        System.out.println(" 高楼打地基100米 ");
        this.house.setBasic(100);
    }

    @Override
    public void buildWalls() {
        System.out.println(" 高楼砌墙20cm ");
        this.house.setWall(20);
    }

    @Override
    public void roofed() {
        System.out.println(" 高楼透明屋顶 ");
        this.house.setRoofed("透明屋顶");
    }

}
