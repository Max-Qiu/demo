package com.maxqiu.demo.P06_Bridge;

/**
 * 严重的消息
 * 
 * @author Max_Qiu
 */
public class SevereNotification extends AbsNotification {

    public SevereNotification(IMessageSender sender) {
        super(sender);
    }

    @Override
    public void notify(String message) {
        sender.send(message + "\t严重的");
    }

}
