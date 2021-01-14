package com.maxqiu.demo.P02_Factory.Mode3_AbstractFactory;

import com.maxqiu.demo.P02_Factory.Mode3_AbstractFactory.factory.CheeseFactory;
import com.maxqiu.demo.P02_Factory.Mode3_AbstractFactory.factory.IFactory;
import com.maxqiu.demo.P02_Factory.Mode3_AbstractFactory.factory.LobsterFactory;
import com.maxqiu.demo.P02_Factory.entity.IHamburger;
import com.maxqiu.demo.P02_Factory.entity.IPizza;

/**
 * 商店
 * 
 * @author Max_Qiu
 */
public class Store {

    public static void main(String[] args) {
        System.out.println("使用不同的工厂生产对应的商品");
        IFactory cheeseFactory = new CheeseFactory();
        IPizza cheesePizza = cheeseFactory.createPizza();
        IHamburger cheeseHamburger = cheeseFactory.createHamburger();
        IFactory lobsterFactory = new LobsterFactory();
        IPizza lobsterPizza = lobsterFactory.createPizza();
        IHamburger lobsterHamburger = lobsterFactory.createHamburger();
    }

}
