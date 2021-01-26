package com.maxqiu.demo.P09_Facade;

/**
 * 灯光
 * 
 * @author Max_Qiu
 */
public class Light {

    public void on() {
        System.out.println("灯光\t打开");
    }

    public void off() {
        System.out.println("灯光\t关闭");
    }

    public void faint() {
        System.out.println("灯光\t微光");
    }

}
