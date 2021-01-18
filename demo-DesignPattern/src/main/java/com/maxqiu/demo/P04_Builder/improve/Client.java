package com.maxqiu.demo.P04_Builder.improve;

/**
 * @author Max_Qiu
 */
public class Client {

    public static void main(String[] args) {
        // 盖别墅
        // 准备建造者
        VillaBuilder villaBuilder = new VillaBuilder();
        // 准备创建房子的指挥者
        HouseDirector houseDirector = new HouseDirector(villaBuilder);
        // 完成盖房子，返回产品（别墅）
        House villa = houseDirector.constructHouse();
        System.out.println(villa);
        System.out.println("--------------------------");
        // 盖高楼
        HighBuildingBuilder highBuildingBuilder = new HighBuildingBuilder();
        // setter方式传入建造者
        houseDirector.setHouseBuilder(highBuildingBuilder);
        // 完成盖房子，返回产品（高楼）
        House highBuilding = houseDirector.constructHouse();
        System.out.println(highBuilding);
    }

}
