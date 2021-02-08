package com.maxqiu.demo.P21_Strategy.improve;

/**
 * 不可以飞的策略
 * 
 * @author Max_Qiu
 */
public class NoFlyBehavior implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("不会飞翔");
    }

}
