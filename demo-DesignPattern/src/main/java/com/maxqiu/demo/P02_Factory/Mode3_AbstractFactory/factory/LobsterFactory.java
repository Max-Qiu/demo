package com.maxqiu.demo.P02_Factory.Mode3_AbstractFactory.factory;

import com.maxqiu.demo.P02_Factory.entity.IHamburger;
import com.maxqiu.demo.P02_Factory.entity.IPizza;
import com.maxqiu.demo.P02_Factory.entity.LobsterHamburger;
import com.maxqiu.demo.P02_Factory.entity.LobsterPizza;

/**
 * 小龙虾披萨：生产小龙虾披萨，小龙虾汉堡包
 * 
 * @author Max_Qiu
 */
public class LobsterFactory implements IFactory {

    @Override
    public IPizza createPizza() {
        IPizza pizza = new LobsterPizza();
        pizza.make();
        return pizza;
    }

    @Override
    public IHamburger createHamburger() {
        IHamburger hamburger = new LobsterHamburger();
        hamburger.make();
        return hamburger;
    }

}
