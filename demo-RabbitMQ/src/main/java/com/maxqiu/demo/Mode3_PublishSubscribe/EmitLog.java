package com.maxqiu.demo.Mode3_PublishSubscribe;

import java.util.Scanner;

import com.maxqiu.demo.Mode2_WorkQueues.RabbitMqUtils;
import com.rabbitmq.client.Channel;

/**
 * @author Max_Qiu
 */
public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        try (Channel channel = RabbitMqUtils.getChannel()) {
            /**
             * 声明一个 exchange 1.exchange 的名称 2.exchange 的类型
             */
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入信息");
            while (sc.hasNext()) {
                String message = sc.nextLine();
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                System.out.println("生产者发出消息" + message);
            }
        }
    }
}