package com.maxqiu.demo.P02_Factory.Mode2_FactoryMethod;

import com.maxqiu.demo.P02_Factory.Mode2_FactoryMethod.factory.CheesePizzaFactory;
import com.maxqiu.demo.P02_Factory.Mode2_FactoryMethod.factory.IPizzaFactory;
import com.maxqiu.demo.P02_Factory.Mode2_FactoryMethod.factory.LobsterPizzaFactory;

/**
 * 商店，调用工厂生产披萨
 * 
 * @author Max_Qiu
 */
public class PizzaStore {

    public static void main(String[] args) {
        System.out.println("使用不同的工厂生产对应的披萨");
        IPizzaFactory cheesePizzaFactory = new CheesePizzaFactory();
        cheesePizzaFactory.createPizza();
        IPizzaFactory greekPizzaFactory = new LobsterPizzaFactory();
        greekPizzaFactory.createPizza();
    }

}
