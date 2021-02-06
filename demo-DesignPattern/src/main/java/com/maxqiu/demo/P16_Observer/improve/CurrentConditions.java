package com.maxqiu.demo.P16_Observer.improve;

/**
 * 第三方（气象站的网站）
 * 
 * @author Max_Qiu
 */
public class CurrentConditions implements Observer {

    // 温度，气压，湿度
    private float temperature;
    private float pressure;
    private float humidity;

    /**
     * 更新天气情况
     * 
     * @param temperature
     * @param pressure
     * @param humidity
     */
    @Override
    public void update(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        display();
    }

    /**
     * 显示
     */
    public void display() {
        System.out.println("***Today Temperature: " + temperature + "***");
        System.out.println("***Today Pressure: " + pressure + "***");
        System.out.println("***Today Humidity: " + humidity + "***");
    }

}
