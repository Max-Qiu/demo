package com.maxqiu.demo.P05_Adapter.Mode1_ClassAdapter;

/**
 * @author Max_Qiu
 */
public class Client {

    /**
     * 充电
     */
    public static void main(String[] args) {
        // 准备一个手机
        MobilePhone phone = new MobilePhone();
        // 准备一个适配器
        IVoltage5V voltage5V = new VoltageAdapter();
        // 充电
        phone.charging(voltage5V);
    }

}
