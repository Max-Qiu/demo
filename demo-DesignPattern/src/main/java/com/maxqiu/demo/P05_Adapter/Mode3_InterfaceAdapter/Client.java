package com.maxqiu.demo.P05_Adapter.Mode3_InterfaceAdapter;

/**
 * @author Max_Qiu
 */
public class Client {

    public static void main(String[] args) {
        MobilePhone mobilePhone = new MobilePhone();
        Voltage220V voltage220V = new Voltage220V();
        AbsAdapter mobileAdapter = new AbsAdapter() {
            @Override
            public Integer output5V() {
                // 重写电源转换方法
                return voltage220V.output220V() / 44;
            }
        };
        mobilePhone.charging(mobileAdapter);
    }

}
