package com.maxqiu.demo.principle2_segregation.improve;

/**
 * @author Max_Qiu
 */
public class Segregation {

    public static void main(String[] args) {
        AirPilot airPilot = new AirPilot();
        PlaneInterface plane = new C919Plane();
        // AirPilot类通过接口去依赖(使用)C919Plane类
        airPilot.carryGoods(plane);
        airPilot.runningInTheSky(plane);

        Captain captain = new Captain();
        Steamship steamship = new Titanic();
        // Captain类通过接口去依赖(使用)Titanic类
        captain.carryGoods(steamship);
        captain.runningOnWater(steamship);
    }

}

/**
 * 运输工具
 */
interface VehicleInterface {
    /**
     * 运输货物
     */
    void carryGoods();
}

/**
 * 飞机接口
 */
interface PlaneInterface extends VehicleInterface {
    /**
     * 在天上运行
     */
    void runningInTheSky();
}

/**
 * 轮船接口
 */
interface Steamship extends VehicleInterface {
    /**
     * 在水上运行
     */
    void runningOnWater();
}

/**
 * C919大飞机
 */
class C919Plane implements PlaneInterface {
    @Override
    public void carryGoods() {
        System.out.println("C919大飞机运输货物");
    }

    @Override
    public void runningInTheSky() {
        System.out.println("C919大飞机在天上运行");
    }
}

/**
 * 泰坦尼克号
 */
class Titanic implements Steamship {
    @Override
    public void carryGoods() {
        System.out.println("泰坦尼克号运输货物");
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
    public void carryGoods(PlaneInterface i) {
        i.carryGoods();
    }

    public void runningInTheSky(PlaneInterface i) {
        i.runningInTheSky();
    }
}

/**
 * 船长
 */
class Captain {
    public void carryGoods(Steamship i) {
        i.carryGoods();
    }

    public void runningOnWater(Steamship i) {
        i.runningOnWater();
    }
}
