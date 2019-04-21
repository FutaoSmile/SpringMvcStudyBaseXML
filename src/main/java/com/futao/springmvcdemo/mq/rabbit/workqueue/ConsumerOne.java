package com.futao.springmvcdemo.mq.rabbit.workqueue;

import com.futao.springmvcdemo.mq.rabbit.RabbitMqConnectionTools;
import com.rabbitmq.client.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.futao.springmvcdemo.mq.rabbit.workqueue.Sender.WORK_QUEUE;

/**
 * @author futao
 * Created on 2019-04-21.
 */
@Slf4j
public class ConsumerOne {

    @SneakyThrows
    public static void main(String[] args) {
        Connection connection = RabbitMqConnectionTools.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(WORK_QUEUE, false, false, false, null);
        //一次只分发一个
        channel.basicQos(1);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @SneakyThrows
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("<<<<<1111接收到消息[{}]", new String(body));
//                TimeUnit.SECONDS.sleep(2);
                //手动回执
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //监听队列(关闭自动应答)
        channel.basicConsume(WORK_QUEUE, false, consumer);

    }
}
