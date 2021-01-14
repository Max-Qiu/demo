package com.maxqiu.demo.P02_Factory.entity;

/**
 * 奶酪披萨
 * 
 * @author Max_Qiu
 */
public class CheesePizza implements IPizza {

    @Override
    public void make() {
        System.out.println("制作奶酪披萨");
    }

}
