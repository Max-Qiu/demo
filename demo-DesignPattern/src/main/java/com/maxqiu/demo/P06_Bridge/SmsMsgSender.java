package com.maxqiu.demo.P06_Bridge;

/**
 * 发送短信消息
 * 
 * @author Max_Qiu
 */
public class SmsMsgSender implements IMessageSender {

    @Override
    public void send(String message) {
        System.out.println(message + "\t使用短信发送");
    }

}
