package com.maxqiu.demo.P17_Mediator;

/**
 * 电视剧（具体的同事类）
 * 
 * @author Max_Qiu
 */
public class Television extends Colleague {

    public Television(Mediator mediator) {
        super(4, mediator);
        mediator.register(this);
    }

    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.id);
    }

    public void startTv() {
        System.out.println("It's time to StartTv!");
    }

    public void stopTv() {
        System.out.println("StopTv!");
    }
}
