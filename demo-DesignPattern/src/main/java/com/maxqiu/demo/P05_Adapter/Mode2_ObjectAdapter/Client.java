package com.maxqiu.demo.P05_Adapter.Mode2_ObjectAdapter;

/**
 * @author Max_Qiu
 */
public class Client {

    /**
     * 充电
     */
    public static void main(String[] args) {
        // 准备220V电压
        Voltage220V voltage220V = new Voltage220V();
        // 准备5V充电器，并插入220电压
        IVoltage5V voltage5V = new VoltageAdapter(voltage220V);
        // 准备手机
        MobilePhone phone = new MobilePhone();
        // 充电
        phone.charging(voltage5V);
    }

}
