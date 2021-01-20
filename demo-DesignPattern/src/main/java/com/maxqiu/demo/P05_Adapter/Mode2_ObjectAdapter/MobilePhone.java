package com.maxqiu.demo.P05_Adapter.Mode2_ObjectAdapter;

/**
 * 手机
 *
 * @author Max_Qiu
 */
public class MobilePhone {

    /**
     * 充电（需要一个实现5V转换接口的类）
     *
     * @param iVoltage5V
     *            适配器接口
     */
    public void charging(IVoltage5V iVoltage5V) {
        if (iVoltage5V.output5V() == 5) {
            System.out.println("电压为5V, 可以充电~~");
        } else {
            System.out.println("电压不是5V, 不能充电~~");
        }
    }

}