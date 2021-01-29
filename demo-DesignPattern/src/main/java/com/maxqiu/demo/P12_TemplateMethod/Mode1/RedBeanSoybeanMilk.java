package com.maxqiu.demo.P12_TemplateMethod.Mode1;

/**
 * 红豆豆浆
 * 
 * @author Max_Qiu
 */
public class RedBeanSoybeanMilk extends SoybeanMilk {

    @Override
    void addCondiments() {
        System.out.println("加入上好的红豆");
    }

}
