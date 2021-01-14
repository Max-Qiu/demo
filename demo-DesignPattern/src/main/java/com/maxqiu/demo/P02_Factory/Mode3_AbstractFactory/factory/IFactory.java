package com.maxqiu.demo.P02_Factory.Mode3_AbstractFactory.factory;

import com.maxqiu.demo.P02_Factory.entity.IHamburger;
import com.maxqiu.demo.P02_Factory.entity.IPizza;

/**
 * 工厂接口，可以生产披萨、汉堡包
 * 
 * @author Max_Qiu
 */
public interface IFactory {

    IPizza createPizza();

    IHamburger createHamburger();

}
