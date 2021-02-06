package com.maxqiu.demo.P16_Observer.demo;

/**
 * 气象站
 * 
 * 1. 包含最新的天气情况信息
 * 
 * 2. 含有 CurrentConditions 对象
 * 
 * 3. 当数据有更新时，就主动的调用 CurrentConditions对象update方法（含display），这样他们（接入方）就看到最新的信息
 * 
 * @author Max_Qiu
 *
 */
public class WeatherData {

    private float temperature;

    private float pressure;

    private float humidity;

    private CurrentConditions currentConditions;

    /**
     * 加入新的第三方
     * 
     * @param currentConditions
     */
    public WeatherData(CurrentConditions currentConditions) {
        this.currentConditions = currentConditions;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void dataChange() {
        // 调用 接入方的 update
        currentConditions.update(getTemperature(), getPressure(), getHumidity());
    }

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
        // 调用dataChange， 将最新的信息 推送给 接入方 currentConditions
        dataChange();
    }

}
