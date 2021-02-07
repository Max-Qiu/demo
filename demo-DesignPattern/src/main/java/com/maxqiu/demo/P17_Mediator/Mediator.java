package com.maxqiu.demo.P17_Mediator;

/**
 * 中介着
 * 
 * @author Max_Qiu
 */
public abstract class Mediator {

    /**
     * 将给中介者对象，加入到集合中
     * 
     * @param colleague
     */
    public abstract void register(Colleague colleague);

    /**
     * 接收消息, 具体的同事对象发出
     * 
     * @param stateChange
     * @param id
     */
    public abstract void getMessage(int stateChange, int id);

}
