package com.maxqiu.demo.P07_Decorator;

/**
 * 牛奶
 * 
 * @author Max_Qiu
 */
public class Milk extends Coffee {

    public Milk(Coffee coffee) {
        super(coffee);
        des = "牛奶";
        price = 1.5;
    }

}
