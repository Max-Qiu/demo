package com.maxqiu.demo.P17_Mediator;

/**
 * @author Max_Qiu
 */
public class Client {

    public static void main(String[] args) {
        // 创建一个中介者对象
        Mediator mediator = new ConcreteMediator();
        // 创建 Alarm 等，并且加入到 ConcreteMediator 对象的HashMap
        Alarm alarm = new Alarm(mediator);
        CoffeeMachine coffeeMachine = new CoffeeMachine(mediator);
        Curtains curtains = new Curtains(mediator);
        Television tV = new Television(mediator);
        // 让闹钟等发出消息
        alarm.sendMessage(0);
        System.out.println("================");
        coffeeMachine.finishCoffee();
        System.out.println("================");
        alarm.sendMessage(1);
    }

}
