package com.maxqiu.demo.principle1_single_responsibility.improve2;

/**
 * @author Max_Qiu
 */
public class SingleResponsibility {

    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle();
        vehicle.run("汽车");
        vehicle.runWater("轮船");
        vehicle.runAir("飞机");
    }

}

// 方案2的分析
// 1. 没有对原来的类做大的修改，只是增加方法
// 2. 没有在类这个级别上遵守单一职责原则，但是在方法级别上，仍然是遵守单一职责
class Vehicle {
    public void run(String vehicle) {
        // 如果需要对某一个职责处理，仅需要修改方法即可
        System.out.println(vehicle + " 在公路上运行....");

    }

    public void runAir(String vehicle) {
        System.out.println(vehicle + " 在天空上运行....");
    }

    public void runWater(String vehicle) {
        System.out.println(vehicle + " 在水中行....");
    }
}
