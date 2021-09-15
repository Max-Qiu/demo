package com.maxqiu.demo.Mode1_HelloWorld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消费者
 *
 * @author Max_Qiu
 */
public class Consumer {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        System.out.println("等待接收消息....");
        // 消费者消费消息
        channel.basicConsume(
            // 消费哪个队列
            QUEUE_NAME,
            // 消费成功之后是否要自动应答 true 代表自动应答 false 手动应答
            true,
            // 推送的消息如何进行消费的接口回调
            (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());
                System.out.println(message);
            },
            // 取消消费的一个回调接口 如在消费的时候队列被删除掉了
            (consumerTag) -> {
                System.out.println("消息消费被中断");
            });
    }
}
