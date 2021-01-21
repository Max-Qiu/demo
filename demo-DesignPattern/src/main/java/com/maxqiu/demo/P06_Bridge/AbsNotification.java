package com.maxqiu.demo.P06_Bridge;

/**
 * 消息类型抽象类
 * 
 * @author Max_Qiu
 */
public abstract class AbsNotification {

    protected IMessageSender sender;

    public AbsNotification(IMessageSender sender) {
        this.sender = sender;
    }

    public abstract void notify(String message);

}
