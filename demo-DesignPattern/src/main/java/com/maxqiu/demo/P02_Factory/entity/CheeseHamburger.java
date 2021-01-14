package com.maxqiu.demo.P02_Factory.entity;

/**
 * 奶酪汉堡包
 * 
 * @author Max_Qiu
 */
public class CheeseHamburger implements IHamburger {

    @Override
    public void make() {
        System.out.println("制作奶酪汉堡包");
    }

}
