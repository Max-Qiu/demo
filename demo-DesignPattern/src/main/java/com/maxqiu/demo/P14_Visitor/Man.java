package com.maxqiu.demo.P14_Visitor;

/**
 * 男性
 * 
 * @author Max_Qiu
 */
public class Man extends Person {

    @Override
    public void accept(Action action) {
        action.getResult(this);
    }

}
