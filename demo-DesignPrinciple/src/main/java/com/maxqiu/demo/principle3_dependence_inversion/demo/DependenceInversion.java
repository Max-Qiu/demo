package com.maxqiu.demo.principle3_dependence_inversion.demo;

/**
 * @author Max_Qiu
 */
public class DependenceInversion {

    public static void main(String[] args) {
        Person person = new Person();
        person.receiveMessage(new EmailMessage());
    }

}

// 问题分析
// 1. 简单，比较容易想到
// 2. 如果我们获取的对象是微信，短信等等，则新增类，同时Peron也要增加相应的接收方法
// 解决思路：
// 引入一个抽象的接口IMessage, 表示消息, 这样Person类与接口IMessage发生依赖，
// 因为EmailMessage, WeiXinMessage等等属于消息的范围，他们各自实现IMessage接口就ok, 这样我们就符号依赖倒转原则

/**
 * 电子邮件消息
 */
class EmailMessage {
    public String printInfo() {
        return "电子邮件信息: hello,world";
    }
}

class Person {
    /**
     * 接收消息
     */
    public void receiveMessage(EmailMessage emailMessage) {
        System.out.println(emailMessage.printInfo());
    }
}