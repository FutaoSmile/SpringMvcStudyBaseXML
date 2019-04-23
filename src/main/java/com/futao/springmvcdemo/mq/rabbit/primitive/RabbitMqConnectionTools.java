package com.futao.springmvcdemo.mq.rabbit.primitive;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitMq配置类
 *
 * @author futao
 * Created on 2019-04-19.
 */
public class RabbitMqConnectionTools {
    /**
     * 获取链接
     *
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Connection getConnection() {
        try {
            return getConnectionFactory().newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    /**
     * 连接工厂
     *
     * @return
     */
    private static ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("futao");
        factory.setPassword("123456");
        factory.setVirtualHost("/springmvc");
        return factory;
    }

    /**
     * 创建并获取通道
     *
     * @return
     */
    @SneakyThrows
    public static Channel getChannel() {
        Connection connection = RabbitMqConnectionTools.getConnection();
        return connection.createChannel();
    }
}
