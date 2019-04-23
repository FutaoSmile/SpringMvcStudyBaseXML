package com.futao.springmvcdemo.mq.rabbit.primitive.remote.procedure.call;

import com.futao.springmvcdemo.mq.rabbit.primitive.RabbitMqConnectionTools;
import com.futao.springmvcdemo.mq.rabbit.primitive.RabbitMqQueueEnum;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author futao
 * Created on 2019-04-22.
 */
@Slf4j
public class RpcClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        //获取channel
        Channel channel = RabbitMqConnectionTools.getChannel();
        //定义队列
        channel.queueDeclare(RabbitMqQueueEnum.RPC_QUEUE.getQueueName(), true, false, false, null);
        //设置quality of service。number of messages that the server will deliver, 0 if unlimited
        channel.basicQos(1);
        //这次请求的唯一标识ID
        final String corrId = UUID.randomUUID().toString();

        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder().
                replyTo(RabbitMqQueueEnum.RPC_QUEUE.getQueueName())
                //2为持久态
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .contentType("application/json")
                .correlationId(corrId)
                .build();
        channel.basicPublish("", RabbitMqQueueEnum.RPC_QUEUE.getQueueName(), props, "牛逼".getBytes());

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        String ctag = channel.basicConsume(RabbitMqQueueEnum.RPC_QUEUE.getQueueName(), false, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response.offer(new String(delivery.getBody(), StandardCharsets.UTF_8));
            }
        }, consumerTag -> {
        });

        String result = response.take();
        channel.basicCancel(ctag);
        log.info("rpc 调用返回结果:[{}]", result);

    }
}
