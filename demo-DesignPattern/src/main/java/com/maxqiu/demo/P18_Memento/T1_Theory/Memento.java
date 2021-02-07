package com.maxqiu.demo.P18_Memento.T1_Theory;

/**
 * 备忘录
 * 
 * @author Max_Qiu
 */
public class Memento {

    /**
     * 存储的状态
     */
    private String state;

    /**
     * 构造器，存储状态
     */
    public Memento(String state) {
        this.state = state;
    }

    /**
     * 返回状态
     */
    public String getState() {
        return state;
    }

}
