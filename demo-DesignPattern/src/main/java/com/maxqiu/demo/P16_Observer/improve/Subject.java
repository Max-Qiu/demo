package com.maxqiu.demo.P16_Observer.improve;

/**
 * 接口, 让 WeatherData 来实现
 * 
 * @author Max_Qiu
 */
public interface Subject {

    /**
     * 注册
     * 
     * @param o
     */
    void registerObserver(Observer o);

    /**
     * 移除
     * 
     * @param o
     */
    void removeObserver(Observer o);

    /**
     * 通知
     */
    void notifyObservers();

}
