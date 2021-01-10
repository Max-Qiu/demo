package com.maxqiu.demo.principle3_dependence_inversion.improve;

/**
 * @author Max_Qiu
 */
public class DependenceInversion {

    public static void main(String[] args) {
        // 客户端无需改变
        Person person = new Person();
        person.receive(new EmailMessage());
        person.receive(new WeiXinMessage());
    }

}

/**
 * 消息接口
 */
interface IMessage {
    String printInfo();
}

/**
 * 电子邮件消息
 */
class EmailMessage implements IMessage {
    @Override
    public String printInfo() {
        return "电子邮件信息: hello,world";
    }
}

/**
 * 微信消息
 */
class WeiXinMessage implements IMessage {
    @Override
    public String printInfo() {
        return "微信信息: hello,ok";
    }
}

class Person {
    // 依赖接口
    public void receive(IMessage message) {
        System.out.println(message.printInfo());
    }
}
