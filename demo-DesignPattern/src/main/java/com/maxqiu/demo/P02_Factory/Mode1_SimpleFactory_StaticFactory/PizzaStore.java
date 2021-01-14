package com.maxqiu.demo.P02_Factory.Mode1_SimpleFactory_StaticFactory;

import com.maxqiu.demo.P02_Factory.Mode1_SimpleFactory_StaticFactory.factory.SimplePizzaFactory;
import com.maxqiu.demo.P02_Factory.Mode1_SimpleFactory_StaticFactory.factory.StaticPizzaFactory;

/**
 * 商店，调用工厂生产披萨
 * 
 * @author Max_Qiu
 */
public class PizzaStore {

    public static void main(String[] args) {
        System.out.println("使用简单工厂模式");
        SimplePizzaFactory simplePizzaFactory = new SimplePizzaFactory();
        simplePizzaFactory.createPizza("lobster");
        simplePizzaFactory.createPizza("cheese");
        simplePizzaFactory.createPizza("other");
        System.out.println("-------------------------------");
        System.out.println("使用静态工厂模式");
        StaticPizzaFactory.createPizza("lobster");
        StaticPizzaFactory.createPizza("cheese");
        StaticPizzaFactory.createPizza("other");
    }

}
