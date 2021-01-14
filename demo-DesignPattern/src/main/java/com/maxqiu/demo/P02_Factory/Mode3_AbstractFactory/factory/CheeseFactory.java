package com.maxqiu.demo.P02_Factory.Mode3_AbstractFactory.factory;

import com.maxqiu.demo.P02_Factory.entity.CheeseHamburger;
import com.maxqiu.demo.P02_Factory.entity.CheesePizza;
import com.maxqiu.demo.P02_Factory.entity.IHamburger;
import com.maxqiu.demo.P02_Factory.entity.IPizza;

/**
 * 奶酪工厂：生产奶酪披萨，奶酪汉堡包
 * 
 * @author Max_Qiu
 */
public class CheeseFactory implements IFactory {

    @Override
    public IPizza createPizza() {
        IPizza pizza = new CheesePizza();
        pizza.make();
        return pizza;
    }

    @Override
    public IHamburger createHamburger() {
        IHamburger hamburger = new CheeseHamburger();
        hamburger.make();
        return hamburger;
    }

}
