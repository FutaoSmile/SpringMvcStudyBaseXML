package com.futao.springmvcdemo.mq.rabbit.remote.procedure.call;

import com.futao.springmvcdemo.mq.rabbit.RabbitMqConnectionTools;
import com.futao.springmvcdemo.mq.rabbit.RabbitMqQueueEnum;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author futao
 * Created on 2019-04-22.
 */
@Slf4j
public class RpcServer {

    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = RabbitMqConnectionTools.getChannel();
        channel.queueDeclare(RabbitMqQueueEnum.RPC_QUEUE.getQueueName(), true, false, false, null);
        channel.queuePurge(RabbitMqQueueEnum.RPC_QUEUE.getQueueName());
        channel.basicQos(1);
        System.out.println(" [x] Awaiting RPC requests");
        Object monitor = new Object();

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(delivery.getProperties().getCorrelationId())
                    .build();

            String response = "";

            try {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                log.info("收到消息：[{}]", message);
                response += "已被处理过的" + message;
            } catch (RuntimeException e) {
                System.out.println(" [.] " + e.toString());
            } finally {
                channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes(StandardCharsets.UTF_8));
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                // RabbitMq consumer worker thread notifies the RPC server owner thread
                synchronized (monitor) {
                    monitor.notify();
                }
            }
        };
        channel.basicConsume(RabbitMqQueueEnum.RPC_QUEUE.getQueueName(), false, deliverCallback, (consumerTag -> {
        }));
        // Wait and be prepared to consume the message from RPC client.
        while (true) {
            synchronized (monitor) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
