package com.maxqiu.demo.P16_Observer.improve;

import java.util.ArrayList;

/**
 * 气象站
 * 
 * 1. 包含最新的天气情况信息
 * 
 * 2. 含有观察者集合，使用ArrayList管理
 * 
 * 3. 当数据有更新时，就主动的调用 ArrayList, 通知所有的（接入方）就看到最新的信息
 *
 * @author Max_Qiu
 */
public class WeatherData implements Subject {

    private float temperature;
    private float pressure;
    private float humidity;

    // 观察者集合
    private ArrayList<Observer> observers = new ArrayList<>();;

    /**
     * 当数据有更新时，就调用 setData
     * 
     * @param temperature
     * @param pressure
     * @param humidity
     */
    public void setData(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        // 调用 接入方的 update
        notifyObservers();
    }

    /**
     * 注册一个观察者
     * 
     * @param o
     */
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    /**
     * 移除一个观察者
     * 
     * @param o
     */
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * 遍历所有的观察者，并通知
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this.temperature, this.pressure, this.humidity);
        }
    }

}
