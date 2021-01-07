package com.maxqiu.demo.principle2_segregation.demo;

/**
 * @author Max_Qiu
 */
public class Segregation {

    public static void main(String[] args) {
        AirPilot airPilot = new AirPilot();
        VehicleInterface plane = new C919Plane();
        // AirPilot类通过接口去依赖(使用)C919Plane类
        airPilot.carryGoods(plane);
        airPilot.runningInTheSky(plane);
        airPilot.runningOnWater(plane);

        Captain captain = new Captain();
        VehicleInterface steamship = new Titanic();
        // Captain类通过接口去依赖(使用)Titanic类
        captain.carryGoods(steamship);
        captain.runningInTheSky(steamship);
        captain.runningOnWater(steamship);
    }

}

/**
 * 交通工具接口
 */
interface VehicleInterface {
    /**
     * 运输货物
     */
    void carryGoods();

    /**
     * 在天上运行
     */
    void runningInTheSky();

    /**
     * 在水上运行
     */
    void runningOnWater();
}

/**
 * C919大飞机
 */
class C919Plane implements VehicleInterface {
    @Override
    public void carryGoods() {
        System.out.println("C919大飞机运输货物");
    }

    @Override
    public void runningInTheSky() {
        System.out.println("C919大飞机在天上运行");
    }

    @Override
    public void runningOnWater() {
        System.out.println("C919大飞机不能在水上运行！！！");
    }

}

/**
 * 泰坦尼克号
 */
class Titanic implements VehicleInterface {
    @Override
    public void carryGoods() {
        System.out.println("泰坦尼克号运输货物");
    }

    @Override
    public void runningInTheSky() {
        System.out.println("泰坦尼克号不能在天上运行！！！");
    }

    @Override
    public void runningOnWater() {
        System.out.println("泰坦尼克号在水上运行");
    }
}

/**
 * 飞机驾驶员
 */
class AirPilot {
    public void carryGoods(VehicleInterface i) {
        i.carryGoods();
    }

    public void runningInTheSky(VehicleInterface i) {
        i.runningInTheSky();
    }

    public void runningOnWater(VehicleInterface i) {
        i.runningOnWater();
    }
}

/**
 * 船长
 */
class Captain {
    public void carryGoods(VehicleInterface i) {
        i.carryGoods();
    }

    public void runningInTheSky(VehicleInterface i) {
        i.runningInTheSky();
    }

    public void runningOnWater(VehicleInterface i) {
        i.runningOnWater();
    }
}
