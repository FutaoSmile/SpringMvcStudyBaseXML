package com.futao.springmvcdemo.mq.rabbit.primitive.topic;

import com.futao.springmvcdemo.mq.rabbit.primitive.ExchangeTypeEnum;
import com.futao.springmvcdemo.mq.rabbit.primitive.RabbitMqConnectionTools;
import com.futao.springmvcdemo.mq.rabbit.primitive.RabbitMqQueueEnum;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Topic模式-消费者4
 *
 * @author futao
 * Created on 2019-04-22.
 */
@Slf4j
public class ConsumerFour {
    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = RabbitMqConnectionTools.getChannel();
        //开启持久化队列
        boolean durable = true;
        channel.queueDeclare(RabbitMqQueueEnum.EXCHANGE_QUEUE_TOPIC_FOUR.getQueueName(), durable, false, false, null);
        //定义交换器类型
        channel.exchangeDeclare(ExchangeTypeEnum.DIRECT.getExchangeName(), BuiltinExchangeType.TOPIC);
        //将消息队列与Exchange交换器与路由键绑定（同时订阅路由key为"log.info"和"log.error"的消息）
        channel.queueBind(RabbitMqQueueEnum.EXCHANGE_QUEUE_TOPIC_FOUR.getQueueName(), ExchangeTypeEnum.TOPIC.getExchangeName(), "log.#");
        //告诉rabbitmq一次只发送一条消息，并且在前一个消息未被处理或者消费之前，不继续发送下一个消息
        channel.basicQos(1);
        log.info("Waiting for message...");
        DeliverCallback deliverCallback = ((consumerTag, message) -> {
            log.info("收到消息:[{}],routingKey：[{}],tag:[{}]", new String(message.getBody()), message.getEnvelope().getRoutingKey(), consumerTag);
            //acknowledgment应答
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
        });
        //关闭自动应答
        boolean autoAck = false;
        channel.basicConsume(RabbitMqQueueEnum.EXCHANGE_QUEUE_TOPIC_FOUR.getQueueName(), autoAck, deliverCallback, consumerTag -> {
        });
    }
}
