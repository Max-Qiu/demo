package com.maxqiu.demo.P06_Bridge;

/**
 * 客户端
 * 
 * @author Max_Qiu
 */
public class Client {

    public static void main(String[] args) {
        String message = "报警啦！";
        IMessageSender emailMsgSender = new EmailMsgSender();
        IMessageSender smsMsgSender = new SmsMsgSender();
        System.out.println("--------------------");
        AbsNotification severeNotification1 = new SevereNotification(emailMsgSender);
        severeNotification1.notify(message);
        AbsNotification severeNotification2 = new SevereNotification(smsMsgSender);
        severeNotification2.notify(message);
        System.out.println("--------------------");
        AbsNotification normalNotification1 = new NormalNotification(emailMsgSender);
        normalNotification1.notify(message);
        AbsNotification normalNotification2 = new NormalNotification(smsMsgSender);
        normalNotification2.notify(message);
    }

}
