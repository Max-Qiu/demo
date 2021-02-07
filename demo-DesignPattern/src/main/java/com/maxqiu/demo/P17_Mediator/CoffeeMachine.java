package com.maxqiu.demo.P17_Mediator;

/**
 * 咖啡机（具体的同事类）
 * 
 * @author Max_Qiu
 */
public class CoffeeMachine extends Colleague {

    public CoffeeMachine(Mediator mediator) {
        super(1, mediator);
        mediator.register(this);
    }

    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.id);
    }

    public void startCoffee() {
        System.out.println("It's time to startCoffee!");
    }

    public void finishCoffee() {
        System.out.println("After 5 minutes!");
        System.out.println("Coffee is ok!");
        sendMessage(1);
    }

}
