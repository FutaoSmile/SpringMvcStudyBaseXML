package com.futao.springmvcdemo.mq.rabbit.workqueue;

import com.futao.springmvcdemo.mq.rabbit.RabbitMqConnectionTools;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author futao
 * Created on 2019-04-21.
 */
@Slf4j
public class Sender {

    public static final String WORK_QUEUE = "futao_work_queue";


    @SneakyThrows
    @Test
    public void test() {
        @Cleanup
        Connection connection = RabbitMqConnectionTools.getConnection();
        @Cleanup
        Channel channel = connection.createChannel();
        channel.queueDeclare(WORK_QUEUE, false, false, false, null);
        //每次只发送一条消息。每个消费者发送确认消息之前，不发送下一条消息
        channel.basicQos(1);
        String msg = "hello work queue :";
        for (int i = 0; i < 2000; i++) {
            channel.basicPublish("", WORK_QUEUE, false, null, (msg + i).getBytes());
            log.info(">>>>>send rabbit mq {}", msg + i);
//            TimeUnit.SECONDS.sleep(1);
        }
    }
}
