package com.futao.springmvcdemo.mq.rabbit.simple;

import com.futao.springmvcdemo.mq.rabbit.RabbitMqConnectionTools;
import com.futao.springmvcdemo.mq.rabbit.RabbitMqQueueEnum;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 简单消费者
 *
 * @author futao
 * Created on 2019-04-22.
 */
@Slf4j
public class Recv {
    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = RabbitMqConnectionTools.getChannel();
        channel.queueDeclare(RabbitMqQueueEnum.SIMPLE.getQueueName(), false, false, false, null);
        log.info("Waiting for message...");
        DeliverCallback deliverCallback = ((consumerTag, message) -> {
            log.info("收到消息:[{}],tag:[{}]", new java.lang.String(message.getBody()), consumerTag);
        });
        channel.basicConsume(RabbitMqQueueEnum.SIMPLE.getQueueName(), true, deliverCallback, consumerTag -> {
        });
    }
}
