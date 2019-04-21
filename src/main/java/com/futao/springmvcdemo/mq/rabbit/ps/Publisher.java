package com.futao.springmvcdemo.mq.rabbit.ps;

import com.futao.springmvcdemo.mq.rabbit.RabbitMqConnectionTools;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author futao
 * Created on 2019-04-21.
 */
@Slf4j
public class Publisher {

    public static final String exchangeName = "futao_exchange";

    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup
        Connection connection = RabbitMqConnectionTools.getConnection();
        @Cleanup
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT);
        channel.basicPublish(exchangeName, "", null, "牛逼".getBytes());

    }
}
