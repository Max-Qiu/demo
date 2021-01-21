package com.maxqiu.demo.P06_Bridge;

/**
 * 发送邮件消息
 * 
 * @author Max_Qiu
 */
public class EmailMsgSender implements IMessageSender {

    @Override
    public void send(String message) {
        System.out.println(message + "\t使用邮件发送");
    }

}
