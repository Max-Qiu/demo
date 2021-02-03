package com.maxqiu.demo.P14_Visitor;

/**
 * 人（被访问者）
 * 
 * @author Max_Qiu
 */
public abstract class Person {

    /**
     * 提供一个方法，让访问者可以访问
     * 
     * @param action
     */
    public abstract void accept(Action action);

}
