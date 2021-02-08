package com.maxqiu.demo.P21_Strategy.improve;

/**
 * 玩具鸭
 * 
 * @author Max_Qiu
 */
public class ToyDuck extends Duck {

    public ToyDuck() {
        flyBehavior = new NoFlyBehavior();
    }

    @Override
    public void display() {
        System.out.println("玩具鸭");
    }

}
