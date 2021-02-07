package com.maxqiu.demo.P18_Memento.T1_Theory;

import java.util.ArrayList;
import java.util.List;

/**
 * 备忘录管理者，管理多个备忘
 * 
 * @author Max_Qiu
 */
public class Caretaker {

    /**
     * 在 List 集合中会有很多的备忘录对象
     */
    private List<Memento> mementoList = new ArrayList<>();

    /**
     * 新增一个备忘录
     * 
     * @param memento
     */
    public void add(Memento memento) {
        mementoList.add(memento);
    }

    /**
     * 获取到第index个Originator的备忘录对象（即保存状态）
     * 
     * @param index
     * @return
     */
    public Memento get(int index) {
        return mementoList.get(index);
    }

}
