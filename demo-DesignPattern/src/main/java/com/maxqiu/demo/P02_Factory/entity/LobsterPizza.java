package com.maxqiu.demo.P02_Factory.entity;

/**
 * 小龙虾披萨
 * 
 * @author Max_Qiu
 */
public class LobsterPizza implements IPizza {

    @Override
    public void make() {
        System.out.println("制作小龙虾披萨");
    }

}
