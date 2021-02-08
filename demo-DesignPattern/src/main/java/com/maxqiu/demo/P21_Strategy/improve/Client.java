package com.maxqiu.demo.P21_Strategy.improve;

/**
 * 客户端
 * 
 * @author Max_Qiu
 */
public class Client {

    public static void main(String[] args) {
        WildDuck wildDuck = new WildDuck();
        wildDuck.display();
        wildDuck.fly();
        ToyDuck toyDuck = new ToyDuck();
        toyDuck.display();
        toyDuck.fly();
    }

}
