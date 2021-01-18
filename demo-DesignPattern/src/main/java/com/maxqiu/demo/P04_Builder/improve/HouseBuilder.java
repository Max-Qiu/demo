package com.maxqiu.demo.P04_Builder.improve;

/**
 * 抽象房子建造者
 * 
 * @author Max_Qiu
 */
public abstract class HouseBuilder {

    public House house = new House();

    /**
     * 建造细节抽象方法
     */
    public abstract void buildBasic();

    public abstract void buildWalls();

    public abstract void roofed();

    /**
     * 房子建造好， 将产品（房子）返回
     */
    public House buildHouse() {
        return house;
    }

}
