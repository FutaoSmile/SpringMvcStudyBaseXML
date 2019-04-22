package com.futao.springmvcdemo.mq.rabbit.topic;

import com.futao.springmvcdemo.mq.rabbit.ExchangeTypeEnum;
import com.futao.springmvcdemo.mq.rabbit.RabbitMqConnectionTools;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Topic模式-生产者
 *
 * @author futao
 * Created on 2019-04-22.
 */
@Slf4j
public class Producer {
    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup
        Connection connection = RabbitMqConnectionTools.getConnection();
        @Cleanup
        Channel channel = connection.createChannel();
        //定义交换器类型
        channel.exchangeDeclare(ExchangeTypeEnum.TOPIC.getExchangeName(), BuiltinExchangeType.TOPIC);
        String msg = "Hello RabbitMq!";
        for (int i = 0; i < 10000; i++) {
            channel.basicPublish(ExchangeTypeEnum.TOPIC.getExchangeName(), "log.info", null, (msg + i + "log.info").getBytes());
            log.info("Send msg:[{}] ,routingKey:[{}] success", (msg + i + "log.info"), "log.info");
        }
        for (int i = 0; i < 10000; i++) {
            channel.basicPublish(ExchangeTypeEnum.TOPIC.getExchangeName(), "log.error", null, (msg + i + "log.error").getBytes());
            log.info("Send msg:[{}] ,routingKey:[{}] success", (msg + i + "log.error"), "log.error");
        }
        for (int i = 0; i < 10000; i++) {
            channel.basicPublish(ExchangeTypeEnum.TOPIC.getExchangeName(), "log.error.rabbitmq", null, (msg + i + "log.error.rabbitmq").getBytes());
            log.info("Send msg:[{}] ,routingKey:[{}] success", (msg + i + "log.error.rabbitmq"), "log.error.rabbitmq");
        }
    }
}
