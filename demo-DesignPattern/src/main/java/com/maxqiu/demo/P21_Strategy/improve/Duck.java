package com.maxqiu.demo.P21_Strategy.improve;

/**
 * 抽象鸭子
 * 
 * @author Max_Qiu
 */
public abstract class Duck {

    /**
     * 飞的属性（策略接口）
     */
    FlyBehavior flyBehavior;

    /**
     * 显示鸭子信息
     */
    public abstract void display();

    public void fly() {
        // 改进
        if (flyBehavior != null) {
            flyBehavior.fly();
        }
    }

}
