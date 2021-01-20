package com.maxqiu.demo.P05_Adapter.Mode3_InterfaceAdapter;

/**
 * 通用电源适配器的默认实现
 * 
 * @author Max_Qiu
 */
public abstract class AbsAdapter implements IVoltage {

    @Override
    public Integer output5V() {
        return null;
    }

    @Override
    public Integer output9V() {
        return null;
    }

    @Override
    public Integer output12V() {
        return null;
    }

}
