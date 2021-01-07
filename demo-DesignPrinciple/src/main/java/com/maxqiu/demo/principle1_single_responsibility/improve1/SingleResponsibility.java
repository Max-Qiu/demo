package com.maxqiu.demo.principle1_single_responsibility.improve1;

/**
 * @author Max_Qiu
 */
public class SingleResponsibility {

    public static void main(String[] args) {
        RoadVehicle roadVehicle = new RoadVehicle();
        roadVehicle.run("摩托车");
        roadVehicle.run("汽车");

        AirVehicle airVehicle = new AirVehicle();
        airVehicle.run("飞机");

        WaterVehicle waterVehicle = new WaterVehicle();
        waterVehicle.run("轮船");
    }

}

// 方案1的分析
// 1. 遵守单一职责原则
// 2. 问题：改动太大，即将类分解，同时修改客户端
// 3. 改进：直接修改类，改动的代码会比较少=>方案2

class RoadVehicle {
    public void run(String vehicle) {
        System.out.println(vehicle + "公路运行");
    }
}

class AirVehicle {
    public void run(String vehicle) {
        System.out.println(vehicle + "天空运行");
    }
}

class WaterVehicle {
    public void run(String vehicle) {
        System.out.println(vehicle + "水中运行");
    }
}
