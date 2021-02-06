package com.maxqiu.demo.P16_Observer.improve;

/**
 * 观察者接口，有观察者来实现
 * 
 * @author Max_Qiu
 */
public interface Observer {

    /**
     * 更新
     * 
     * @param temperature
     * @param pressure
     * @param humidity
     */
    void update(float temperature, float pressure, float humidity);

}
