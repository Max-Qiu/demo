package com.maxqiu.demo.P16_Observer.demo;

/**
 * 第三方（可以理解成是气象站的网站）
 * 
 * @author Max_Qiu
 */
public class CurrentConditions {

    // 温度，气压，湿度
    private float temperature;
    private float pressure;
    private float humidity;

    /**
     * 更新天气情况，是由 WeatherData 来调用，使用推送模式
     * 
     * @param temperature
     * @param pressure
     * @param humidity
     */
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
