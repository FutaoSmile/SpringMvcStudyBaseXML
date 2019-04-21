package com.futao.springmvcdemo.mq.rabbit.simple;

import com.futao.springmvcdemo.mq.rabbit.RabbitMqConnectionTools;
import com.futao.springmvcdemo.mq.rabbit.RabbitMqQueueEnum;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * 简单消息队列
 *
 * @author futao
 * Created on 2019-04-19.
 */
public class SimpleProducer {


    @Test
    @SneakyThrows
    public void test1() {
        //获取链接
        @Cleanup
        Connection connection = RabbitMqConnectionTools.getConnection();
        //获取通道
        @Cleanup
        Channel channel = connection.createChannel();
        //定义queue
        channel.queueDeclare(RabbitMqQueueEnum.SIMPLE.getQueueName(), false, false, false, null);
        channel.basicPublish("", RabbitMqQueueEnum.SIMPLE.getQueueName(), false, null, "i am simple queue".getBytes(Charset.forName("utf-8")));
    }


}
