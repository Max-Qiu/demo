package com.maxqiu.demo.P07_Decorator;

/**
 * 焦糖
 * 
 * @author Max_Qiu
 */
public class Caramel extends Coffee {

    public Caramel(Coffee coffee) {
        super(coffee);
        des = "焦糖";
        price = 2.0;
    }

}
