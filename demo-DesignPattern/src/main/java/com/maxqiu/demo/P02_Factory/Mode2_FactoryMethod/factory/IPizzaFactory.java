package com.maxqiu.demo.P02_Factory.Mode2_FactoryMethod.factory;

import com.maxqiu.demo.P02_Factory.entity.IPizza;

/**
 * 披萨工厂接口
 * 
 * @author Max_Qiu
 */
public interface IPizzaFactory {

    IPizza createPizza();

}
