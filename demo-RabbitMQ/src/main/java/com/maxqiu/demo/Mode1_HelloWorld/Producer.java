package com.maxqiu.demo.Mode1_HelloWorld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 生产者
 *
 * @author Max_Qiu
 */
public class Producer {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        // 创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 生成一个队列
        channel.queueDeclare(
            // 队列名称
            QUEUE_NAME,
            // 队列里面的消息是否持久化 默认消息存储在内存中
            false,
            // 该队列是否只供一个消费者进行消费 是否进行共享 true 可以多个消费者消费
            false,
            // 是否自动删除 最后一个消费者端开连接以后该队列是否自动删除 true 自动删除
            false,
            // 其他参数
            null);
        String message = "hello world";
        // 发送一个消息
        channel.basicPublish(
            // 发送到那个交换机
            "",
            // 路由的 key 是哪个
            QUEUE_NAME,
            // 其他的参数信息
            null,
            // 发送消息的消息体
            message.getBytes());
        System.out.println("消息发送完毕");
        channel.close();
        connection.close();
    }
}
