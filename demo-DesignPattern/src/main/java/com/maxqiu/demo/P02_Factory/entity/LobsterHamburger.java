package com.maxqiu.demo.P02_Factory.entity;

/**
 * 小龙虾汉堡包
 * 
 * @author Max_Qiu
 */
public class LobsterHamburger implements IHamburger {

    @Override
    public void make() {
        System.out.println("制作小龙虾汉堡包");
    }

}
