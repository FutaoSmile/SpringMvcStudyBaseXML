package com.futao.springmvcdemo.mq.rabbit.simple;

import com.futao.springmvcdemo.mq.rabbit.RabbitMqConnectionTools;
import com.rabbitmq.client.*;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.IOException;

/**
 * 简单消费者消费消息
 *
 * @author futao
 * Created on 2019-04-21.
 */
public class SimpleConsumer {

    @SneakyThrows
    @Test
    public void test() {
        //获取链接
        @Cleanup
        Connection connection = RabbitMqConnectionTools.getConnection();

        //创建频道
        @Cleanup
        Channel channel = connection.createChannel();

        channel.setDefaultConsumer(new Consumer() {
            @Override
            public void handleConsumeOk(String consumerTag) {

            }

            @Override
            public void handleCancelOk(String consumerTag) {

            }

            @Override
            public void handleCancel(String consumerTag) throws IOException {

            }

            @Override
            public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {

            }

            @Override
            public void handleRecoverOk(String consumerTag) {

            }

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

            }
        });




    }
}
