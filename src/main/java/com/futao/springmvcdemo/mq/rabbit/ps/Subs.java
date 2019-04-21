package com.futao.springmvcdemo.mq.rabbit.ps;

import com.futao.springmvcdemo.mq.rabbit.RabbitMqConnectionTools;
import com.rabbitmq.client.*;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.IOException;

import static com.futao.springmvcdemo.mq.rabbit.ps.Publisher.exchangeName;

/**
 * @author futao
 * Created on 2019-04-21.
 */
public class Subs {

    @SneakyThrows
    @Test
    public void test() {
        Connection connection = RabbitMqConnectionTools.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("exq", false, false, false, null);
        channel.queueBind("exq", exchangeName, "");
        channel.basicQos(1);
        //定义一个消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume("exq", false, defaultConsumer);

    }
}
