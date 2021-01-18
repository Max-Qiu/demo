package com.maxqiu.demo.P04_Builder.improve;

/**
 * 别墅建造者
 * 
 * @author Max_Qiu
 */
public class VillaBuilder extends HouseBuilder {

    @Override
    public void buildBasic() {
        System.out.println(" 别墅打地基10米 ");
        this.house.setBasic(10);
    }

    @Override
    public void buildWalls() {
        System.out.println(" 别墅砌墙10cm ");
        this.house.setWall(10);
    }

    @Override
    public void roofed() {
        System.out.println(" 别墅三角屋顶 ");
        this.house.setRoofed("三角屋顶");
    }

}
