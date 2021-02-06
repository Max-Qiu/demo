> 推荐阅读：[漫画：设计模式中的 “观察者模式”](https://mp.weixin.qq.com/s/JHIzGc1c0EyT-LfuUhyTtA)

# 情景介绍

- 气象站可以将每天测量到的温度、湿度、气压等发布出去
- 需要设计开放型 API，便于其他第三方也能接入气象站获取数据
- 提供温度、气压和湿度的接口
- 测量数据更新时，要能实时的通知给第三方

## 传统方案

```java
/**
 * 第三方（可以理解成是气象站的网站）
 */
public class CurrentConditions {
    // 温度，气压，湿度
    private float temperature;
    private float pressure;
    private float humidity;
    /**
     * 更新天气情况，是由 WeatherData 来调用，使用推送模式
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
```

```java
/**
 * 气象站
 *
 * 1. 包含最新的天气情况信息
 *
 * 2. 含有 CurrentConditions 对象
 *
 * 3. 当数据有更新时，就主动的调用 CurrentConditions对象update方法（含display），这样他们（接入方）就看到最新的信息
 *
 */
public class WeatherData {
    private float temperature;
    private float pressure;
    private float humidity;
    private CurrentConditions currentConditions;
    /**
     * 加入新的第三方
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
     */
    public void setData(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        // 调用dataChange， 将最新的信息 推送给 接入方 currentConditions
        dataChange();
    }
}
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        // 创建接入方 currentConditions
        CurrentConditions currentConditions = new CurrentConditions();
        // 创建 WeatherData 并将 接入方 currentConditions 传递到 WeatherData 中
        WeatherData weatherData = new WeatherData(currentConditions);
        // 更新天气情况
        weatherData.setData(30, 150, 40);
        // 天气情况变化
        System.out.println("============天气情况变化=============");
        weatherData.setData(40, 160, 20);
    }
}
```

运行结果如下：

    ***Today Temperature: 30.0***
    ***Today Pressure: 150.0***
    ***Today Humidity: 40.0***
    ============天气情况变化=============
    ***Today Temperature: 40.0***
    ***Today Pressure: 160.0***
    ***Today Humidity: 20.0***

# 观察者模式 Observer Pattern

> 简介

对象之间多对一依赖的一种设计方案。被依赖的对象为`Subject`，依赖的对象为`Observer`，`Subject`通知`Observer`变化

## 改进方案

> UML类图

![](https://cdn.maxqiu.com/upload/72b65d6df6ce4f689f0fee3be09daf2b.jpg)

> 代码实现

```java
/**
 * 观察者接口，有观察者来实现
 */
public interface Observer {
    /**
     * 更新
     */
    void update(float temperature, float pressure, float humidity);
}
```

```java
/**
 * 第三方（气象站的网站）
 */
public class CurrentConditions implements Observer {
    // 温度，气压，湿度
    private float temperature;
    private float pressure;
    private float humidity;
    /**
     * 更新天气情况
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
```

```java
/**
 * 第三方（百度网站）
 */
public class BaiduSite implements Observer {
    // 温度，气压，湿度
    private float temperature;
    private float pressure;
    private float humidity;
    /**
     * 更新天气情况
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
        System.out.println("===百度网站====");
        System.out.println("***百度网站 气温 : " + temperature + "***");
        System.out.println("***百度网站 气压: " + pressure + "***");
        System.out.println("***百度网站 湿度: " + humidity + "***");
    }
}
```

```java
/**
 * 接口, 让 WeatherData 来实现
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
```

```java
/**
 * 气象站
 *
 * 1. 包含最新的天气情况信息
 *
 * 2. 含有观察者集合，使用ArrayList管理
 *
 * 3. 当数据有更新时，就主动的调用 ArrayList, 通知所有的（接入方）就看到最新的信息
 */
public class WeatherData implements Subject {
    private float temperature;
    private float pressure;
    private float humidity;
    // 观察者集合
    private ArrayList<Observer> observers = new ArrayList<>();;
    /**
     * 当数据有更新时，就调用 setData
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
     */
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }
    /**
     * 移除一个观察者
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
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        // 创建一个WeatherData
        WeatherData weatherData = new WeatherData();
        // 创建观察者
        CurrentConditions currentConditions = new CurrentConditions();
        BaiduSite baiduSite = new BaiduSite();
        // 注册到weatherData
        weatherData.registerObserver(currentConditions);
        weatherData.registerObserver(baiduSite);
        // 测试
        System.out.println("通知各个注册的观察者, 看看信息");
        weatherData.setData(10f, 100f, 30.3f);
        System.out.println("========================");
        // 移除currentConditions
        weatherData.removeObserver(currentConditions);
        // 测试
        System.out.println("通知各个注册的观察者, 看看信息");
        weatherData.setData(10f, 100f, 30.3f);
    }
}
```

运行结果：

    通知各个注册的观察者, 看看信息
    ***Today Temperature: 10.0***
    ***Today Pressure: 100.0***
    ***Today Humidity: 30.3***
    ===百度网站====
    ***百度网站 气温 : 10.0***
    ***百度网站 气压: 100.0***
    ***百度网站 湿度: 30.3***
    ========================
    通知各个注册的观察者, 看看信息
    ===百度网站====
    ***百度网站 气温 : 10.0***
    ***百度网站 气压: 100.0***
    ***百度网站 湿度: 30.3***
