package com.futao.springmvcdemo.mq.rabbit.primitive;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 交换器类型定义枚举
 *
 * @author futao
 * Created on 2019-04-22.
 */
@AllArgsConstructor
@Getter
public enum ExchangeTypeEnum {
    /**
     * fanout扇出类型(发布订阅，广播)
     */
    FANOUT("myExchangeType-fanout"),

    /**
     * 直接(路由键)
     */
    DIRECT("myExchangeType-direct"),

    /**
     * 主题模式交换器
     */
    TOPIC("myExchangeType-topic");


    private String exchangeName;
}
