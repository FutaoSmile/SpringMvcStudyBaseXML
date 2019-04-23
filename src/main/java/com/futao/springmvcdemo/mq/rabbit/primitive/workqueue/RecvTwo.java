package com.futao.springmvcdemo.mq.rabbit.primitive.workqueue;

import com.futao.springmvcdemo.mq.rabbit.primitive.RabbitMqConnectionTools;
import com.futao.springmvcdemo.mq.rabbit.primitive.RabbitMqQueueEnum;
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
public class RecvTwo {
    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = RabbitMqConnectionTools.getChannel();
        //开启持久化队列
        boolean durable = true;
        channel.queueDeclare(RabbitMqQueueEnum.WORK_QUEUE.getQueueName(), durable, false, false, null);
        //告诉rabbitmq一次只发送一条消息，并且在前一个消息未被处理或者消费之前，不继续发送下一个消息
        channel.basicQos(1);
        log.info("Waiting for message...");
        DeliverCallback deliverCallback = ((consumerTag, message) -> {
            log.info("收到消息:[{}],tag:[{}]", new String(message.getBody()), consumerTag);
            //acknowledgment应答
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            try {
                Thread.sleep(2000);
            } catch (Exception e) {

            }
        });
        //关闭自动应答
        boolean autoAck = false;
        channel.basicConsume(RabbitMqQueueEnum.WORK_QUEUE.getQueueName(), autoAck, deliverCallback, consumerTag -> {
        });
    }
}
