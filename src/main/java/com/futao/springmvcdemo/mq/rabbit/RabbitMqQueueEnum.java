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
    SIMPLE("simple-queue");
    /**
     * queue名称
     */
    private String queueName;

}
