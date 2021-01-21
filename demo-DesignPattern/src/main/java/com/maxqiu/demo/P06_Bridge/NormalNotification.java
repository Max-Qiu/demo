package com.maxqiu.demo.P06_Bridge;

/**
 * 普通的消息
 * 
 * @author Max_Qiu
 */
public class NormalNotification extends AbsNotification {

    public NormalNotification(IMessageSender sender) {
        super(sender);
    }

    @Override
    public void notify(String message) {
        sender.send(message + "\t普通的");
    }

}
