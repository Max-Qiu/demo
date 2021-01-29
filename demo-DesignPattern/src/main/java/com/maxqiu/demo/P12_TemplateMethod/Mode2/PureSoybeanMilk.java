package com.maxqiu.demo.P12_TemplateMethod.Mode2;

/**
 * 纯豆浆
 * 
 * @author Max_Qiu
 */
public class PureSoybeanMilk extends SoybeanMilk {

    @Override
    void addCondiments() {
        // 空实现
    }

    @Override
    boolean customerWantCondiments() {
        return false;
    }

}
