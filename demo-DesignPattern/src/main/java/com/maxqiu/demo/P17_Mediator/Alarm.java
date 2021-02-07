package com.maxqiu.demo.P17_Mediator;

/**
 * 闹钟（具体的同事类）
 * 
 * @author Max_Qiu
 */
public class Alarm extends Colleague {

    public Alarm(Mediator mediator) {
        super(3, mediator);
        // 在创建Alarm 同事对象时，将自己放入到ConcreteMediator 对象中[集合]
        mediator.register(this);
    }

    @Override
    public void sendMessage(int stateChange) {
        // 调用的中介者对象的getMessage
        this.getMediator().getMessage(stateChange, this.id);
    }

}
