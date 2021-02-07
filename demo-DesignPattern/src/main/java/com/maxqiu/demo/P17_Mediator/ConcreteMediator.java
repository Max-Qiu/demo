package com.maxqiu.demo.P17_Mediator;

import java.util.HashMap;

/**
 * 具体的中介者类
 * 
 * @author Max_Qiu
 */
public class ConcreteMediator extends Mediator {

    /**
     * 集合，放入所有的同事对象
     */
    private HashMap<Integer, Colleague> colleagueMap = new HashMap<>();

    @Override
    public void register(Colleague colleague) {
        colleagueMap.put(colleague.id, colleague);
    }

    /**
     * 具体中介者的核心方法
     * 
     * 1. 根据得到消息，完成对应任务
     * 
     * 2. 中介者在这个方法，协调各个具体的同事对象，完成任务
     * 
     * @param stateChange
     * @param id
     */
    @Override
    public void getMessage(int stateChange, int id) {
        // 处理闹钟发出的消息
        if (colleagueMap.get(id) instanceof Alarm) {
            if (stateChange == 0) {
                ((CoffeeMachine)(colleagueMap.get(1))).startCoffee();
                ((Television)(colleagueMap.get(4))).startTv();
            } else if (stateChange == 1) {
                ((Television)(colleagueMap.get(4))).stopTv();
            }
        } else if (colleagueMap.get(id) instanceof CoffeeMachine) {
            ((Curtains)(colleagueMap.get(2))).upCurtains();
        } else if (colleagueMap.get(id) instanceof Television) {
            // 如果电视发出消息，这里处理
        } else if (colleagueMap.get(id) instanceof Curtains) {
            // 如果窗帘发出消息，这里处理
        }
    }

}
