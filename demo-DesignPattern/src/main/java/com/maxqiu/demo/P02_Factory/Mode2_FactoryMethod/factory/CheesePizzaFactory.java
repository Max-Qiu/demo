package com.maxqiu.demo.P02_Factory.Mode2_FactoryMethod.factory;

import com.maxqiu.demo.P02_Factory.entity.CheesePizza;
import com.maxqiu.demo.P02_Factory.entity.IPizza;

/**
 * 奶酪披萨工厂
 * 
 * @author Max_Qiu
 */
public class CheesePizzaFactory implements IPizzaFactory {

    @Override
    public IPizza createPizza() {
        IPizza pizza = new CheesePizza();
        pizza.make();
        return pizza;
    }

}
