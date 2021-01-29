package com.maxqiu.demo.P12_TemplateMethod.Mode2;

/**
 * 客户端
 * 
 * @author Max_Qiu
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("----制作红豆豆浆----");
        RedBeanSoybeanMilk redBeanSoybeanMilk = new RedBeanSoybeanMilk();
        redBeanSoybeanMilk.make();
        System.out.println("----制作花生豆浆----");
        SoybeanMilk peanutSoybeanMilk = new PeanutSoybeanMilk();
        peanutSoybeanMilk.make();
        System.out.println("----制作纯豆浆----");
        SoybeanMilk pureSoybeanMilk = new PureSoybeanMilk();
        pureSoybeanMilk.make();
    }

}
