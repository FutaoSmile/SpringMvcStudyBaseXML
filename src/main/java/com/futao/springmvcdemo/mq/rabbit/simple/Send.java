package com.futao.springmvcdemo.mq.rabbit.simple;

import com.futao.springmvcdemo.mq.rabbit.RabbitMqConnectionTools;
import com.futao.springmvcdemo.mq.rabbit.RabbitMqQueueEnum;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 简单发送者
 *
 * @author futao
 * Created on 2019-04-22.
 */
@Slf4j
public class Send {
    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup
        Connection connection = RabbitMqConnectionTools.getConnection();
        @Cleanup
        Channel channel = connection.createChannel();
        //定义一个队列
        channel.queueDeclare(RabbitMqQueueEnum.SIMPLE.getQueueName(), false, false, false, null);
        String msg = "Hello RabbitMq!";
        channel.basicPublish("", RabbitMqQueueEnum.SIMPLE.getQueueName(), null, msg.getBytes());
        log.info("Send msg:[{}] success", msg);
    }
}
