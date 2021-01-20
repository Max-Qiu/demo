package com.maxqiu.demo.P05_Adapter.Mode2_ObjectAdapter;

/**
 * 充电器
 *
 * @author Max_Qiu
 */
public class VoltageAdapter implements IVoltage5V {

    private Voltage220V voltage220V;

    /**
     * 通过构造方法传入220V电压，即充电器要工作必须有220V电压
     * 
     * @param voltage220V
     *            220电压
     */
    public VoltageAdapter(Voltage220V voltage220V) {
        this.voltage220V = voltage220V;
    }

    /**
     * 实现输出5V
     *
     * @return 5V电压
     */
    @Override
    public Integer output5V() {
        int dst = 0;
        if (null != voltage220V) {
            // 获取220V 电压并转换
            dst = voltage220V.output220V() / 44;
        }
        return dst;
    }

}
