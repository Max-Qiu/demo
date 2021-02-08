package com.maxqiu.demo.P21_Strategy.improve;

/**
 * 野鸭
 * 
 * @author Max_Qiu
 */
public class WildDuck extends Duck {

    /**
     * 构造器，决定具体的 flyBehavior 的对象
     */
    public WildDuck() {
        flyBehavior = new GoodFlyBehavior();
    }

    @Override
    public void display() {
        System.out.println("野鸭");
    }

}
