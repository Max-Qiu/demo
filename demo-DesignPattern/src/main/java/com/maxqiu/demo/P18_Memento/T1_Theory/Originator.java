package com.maxqiu.demo.P18_Memento.T1_Theory;

import lombok.Getter;
import lombok.Setter;

/**
 * 要被记录的对象
 * 
 * @author Max_Qiu
 */
@Getter
@Setter
public class Originator {

    /**
     * 状态信息
     */
    private String state;

    /**
     * 编写一个方法，创建一个状态对象 Memento，并返回 Memento
     */
    public Memento saveStateMemento() {
        return new Memento(state);
    }

    /**
     * 通过备忘录对象，恢复状态
     */
    public void getStateFromMemento(Memento memento) {
        state = memento.getState();
    }

}
