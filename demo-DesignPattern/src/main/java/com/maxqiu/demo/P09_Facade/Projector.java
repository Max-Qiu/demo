package com.maxqiu.demo.P09_Facade;

/**
 * 投影仪
 * 
 * @author Max_Qiu
 */
public class Projector {

    public void on() {
        System.out.println("投影仪\t打开");
    }

    public void focus() {
        System.out.println("投影仪\t校正");
    }

    public void off() {
        System.out.println("投影仪\t关闭");
    }

}
