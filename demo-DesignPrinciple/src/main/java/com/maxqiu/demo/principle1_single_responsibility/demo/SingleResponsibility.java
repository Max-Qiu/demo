package com.maxqiu.demo.principle1_single_responsibility.demo;

/**
 * @author Max_Qiu
 */
public class SingleResponsibility {

    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle();
        vehicle.run("摩托车");
        vehicle.run("汽车");
        vehicle.run("飞机");
    }

}

// 示例的问题：
// 1. run方法中，违反了单一职责原则
// 2. 解决方案：根据交通工具运行方法不同，分解成不同类即可

/**
 * 交通工具类
 */
class Vehicle {
    /**
     * 职责：运行
     */
    public void run(String vehicle) {
        System.out.println(vehicle + " 在公路上运行....");
    }
}
