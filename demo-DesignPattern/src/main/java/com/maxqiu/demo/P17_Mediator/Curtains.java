package com.maxqiu.demo.P17_Mediator;

/**
 * 窗帘（具体的同事类）
 * 
 * @author Max_Qiu
 */
public class Curtains extends Colleague {

    public Curtains(Mediator mediator) {
        super(2, mediator);
        mediator.register(this);
    }

    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.id);
    }

    public void upCurtains() {
        System.out.println("I am holding Up Curtains!");
    }

}
