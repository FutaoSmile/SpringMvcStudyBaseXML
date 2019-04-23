package com.futao.springmvcdemo.mq.rabbit.primitive.ps;

import com.futao.springmvcdemo.mq.rabbit.primitive.ExchangeTypeEnum;
import com.futao.springmvcdemo.mq.rabbit.primitive.RabbitMqConnectionTools;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 发布订阅-发布者
 *
 * @author futao
 * Created on 2019-04-22.
 */
@Slf4j
public class Publisher {
    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup
        Connection connection = RabbitMqConnectionTools.getConnection();
        @Cleanup
        Channel channel = connection.createChannel();
        //定义交换器类型
        channel.exchangeDeclare(ExchangeTypeEnum.FANOUT.getExchangeName(), BuiltinExchangeType.FANOUT);
        String msg = "Hello RabbitMq!";
        for (int i = 0; i < 20; i++) {
            channel.basicPublish(ExchangeTypeEnum.FANOUT.getExchangeName(), "", null, (msg + i).getBytes());
            log.info("Send msg:[{}] success", (msg + i));
        }
    }
}
