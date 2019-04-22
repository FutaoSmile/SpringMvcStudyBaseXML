package com.futao.springmvcdemo.mq.rabbit;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * queue名称枚举
 *
 * @author futao
 * Created on 2019-04-19.
 */
@Getter
@AllArgsConstructor
public enum RabbitMqQueueEnum {

    /**
     * 简单queue
     */
    SIMPLE("simple-queue"),
    /**
     * WorkQueue 工作队列
     */
    WORK_QUEUE("work-queue"),
    /**
     * 发布订阅-fanout
     */
    EXCHANGE_QUEUE_FANOUT_ONE("exchange-queue-fanout-1"),

    EXCHANGE_QUEUE_FANOUT_TWO("exchange-queue-fanout-2"),

    EXCHANGE_QUEUE_DIRECT_ONE("exchange-queue-direct-1"),
    EXCHANGE_QUEUE_DIRECT_TWO("exchange-queue-direct-2"),

    EXCHANGE_QUEUE_TOPIC_ONE("exchange-queue-topic-1"),
    EXCHANGE_QUEUE_TOPIC_TWO("exchange-queue-topic-2"),
    EXCHANGE_QUEUE_TOPIC_THREE("exchange-queue-topic-3"),
    EXCHANGE_QUEUE_TOPIC_FOUR("exchange-queue-topic-4"),

    /**
     * RPC队列
     */
    RPC_QUEUE("rpc-queue");


    /**
     * queue名称
     */
    private String queueName;

}
