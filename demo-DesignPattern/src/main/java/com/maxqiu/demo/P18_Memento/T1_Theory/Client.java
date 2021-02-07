package com.maxqiu.demo.P18_Memento.T1_Theory;

/**
 * 客户端
 * 
 * @author Max_Qiu
 */
public class Client {

    public static void main(String[] args) {
        // 创建一个备忘录管理者
        Caretaker caretaker = new Caretaker();
        // 创建一个对象
        Originator originator = new Originator();
        originator.setState("状态#1 攻击力 100");
        // 保存了当前的状态
        caretaker.add(originator.saveStateMemento());
        // 修改并保存当前状态
        originator.setState("状态#2 攻击力 80");
        caretaker.add(originator.saveStateMemento());
        // 再次修改并保存当前状态
        originator.setState("状态#3 攻击力 50");
        caretaker.add(originator.saveStateMemento());

        System.out.println("当前的状态是：" + originator.getState());
        System.out.println("恢复到状态1");
        // 将 originator 恢复到状态1
        originator.getStateFromMemento(caretaker.get(0));
        System.out.println("当前的状态是：" + originator.getState());
    }

}
