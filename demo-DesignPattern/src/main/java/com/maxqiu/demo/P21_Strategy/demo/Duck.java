package com.maxqiu.demo.P21_Strategy.demo;

/**
 * 抽象鸭子
 * 
 * @author Max_Qiu
 */
public abstract class Duck {

    /**
     * 显示鸭子信息
     */
    public abstract void display();

    public void quack() {
        System.out.println("鸭子嘎嘎叫~~");
    }

    public void swim() {
        System.out.println("鸭子会游泳~~");
    }

    public void fly() {
        System.out.println("鸭子会飞翔~~~");
    }

}
