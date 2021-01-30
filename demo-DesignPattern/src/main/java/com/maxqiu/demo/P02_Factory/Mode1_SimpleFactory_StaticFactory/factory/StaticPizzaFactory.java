package com.maxqiu.demo.P02_Factory.Mode1_SimpleFactory_StaticFactory.factory;

import com.maxqiu.demo.P02_Factory.entity.CheesePizza;
import com.maxqiu.demo.P02_Factory.entity.IPizza;
import com.maxqiu.demo.P02_Factory.entity.LobsterPizza;

/**
 * 静态工厂模式
 * 
 * @author Max_Qiu
 */
public class StaticPizzaFactory {

    public static IPizza createPizza(String orderType) {
        IPizza pizza;
        if ("lobster".equals(orderType)) {
            pizza = new LobsterPizza();
        } else if ("cheese".equals(orderType)) {
            pizza = new CheesePizza();
        } else {
            System.out.println("生产披萨失败");
            return null;
        }
        pizza.make();
        return pizza;
    }

}
