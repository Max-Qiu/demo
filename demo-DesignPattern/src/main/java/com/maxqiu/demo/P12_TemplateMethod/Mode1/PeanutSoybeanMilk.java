package com.maxqiu.demo.P12_TemplateMethod.Mode1;

/**
 * 花生豆浆
 * 
 * @author Max_Qiu
 */
public class PeanutSoybeanMilk extends SoybeanMilk {

    @Override
    void addCondiments() {
        System.out.println("加入上好的花生");
    }

}
