package com.maxqiu.demo.P04_Builder.improve;

/**
 * 建造者指挥官
 * 
 * @author Max_Qiu
 */
public class HouseDirector {

    HouseBuilder houseBuilder;

    /**
     * 方式1：构造器传入 HouseBuilder
     */
    public HouseDirector(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }

    /**
     * 方式2：通过 setter 传入 HouseBuilder
     */
    public void setHouseBuilder(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }

    /**
     * 如何处理建造房子的流程，交给指挥者
     * 
     * @return House
     */
    public House constructHouse() {
        houseBuilder.buildBasic();
        houseBuilder.buildWalls();
        houseBuilder.roofed();
        return houseBuilder.buildHouse();
    }

}
