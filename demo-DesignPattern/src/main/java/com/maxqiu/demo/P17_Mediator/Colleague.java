package com.maxqiu.demo.P17_Mediator;

/**
 * 同事抽象类
 * 
 * @author Max_Qiu
 */
public abstract class Colleague {

    protected Integer id;
    private Mediator mediator;

    public Colleague(Integer id, Mediator mediator) {
        this.id = id;
        this.mediator = mediator;
    }

    public Mediator getMediator() {
        return this.mediator;
    }

    public abstract void sendMessage(int stateChange);

}
