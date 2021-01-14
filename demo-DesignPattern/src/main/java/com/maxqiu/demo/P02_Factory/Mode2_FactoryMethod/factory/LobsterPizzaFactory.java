package com.maxqiu.demo.P02_Factory.Mode2_FactoryMethod.factory;

import com.maxqiu.demo.P02_Factory.entity.IPizza;
import com.maxqiu.demo.P02_Factory.entity.LobsterPizza;

/**
 * 小龙虾披萨工厂
 * 
 * @author Max_Qiu
 */
public class LobsterPizzaFactory implements IPizzaFactory {

    @Override
    public IPizza createPizza() {
        IPizza pizza = new LobsterPizza();
        pizza.make();
        return pizza;
    }

}
