package com.maxqiu.demo.P05_Adapter.Mode1_ClassAdapter;

/**
 * 充电器
 * 
 * @author Max_Qiu
 */
public class VoltageAdapter extends Voltage220V implements IVoltage5V {

    /**
     * 实现输出5V
     * 
     * @return 5V电压
     */
    @Override
    public Integer output5V() {
        // 获取到220V电压
        int srcV = output220V();
        // 转成5V
        return srcV / 44;
    }

}
