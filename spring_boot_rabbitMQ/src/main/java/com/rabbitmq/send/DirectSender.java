package com.rabbitmq.send;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.config.RabbitMQDirectConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * direct模式生产者
 *
 * @author xcxu
 */
@Component
@Slf4j
public class DirectSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendDirectSimple(JSONObject obj) {

        log.info("sendDirectQueue已发送消息");
        // 第一个参数是指要发送到哪个队列里面， 第二个参数是指要发送的内容
        this.amqpTemplate.convertAndSend(RabbitMQDirectConfig.DIRECT_QUEUE, obj);
    }

    public void sendDirect(JSONObject obj) {

        // 第一个参数是指要发送到哪个队列里面， 第二个参数是指要发送的内容
        this.amqpTemplate.convertAndSend(RabbitMQDirectConfig.DIERECT_EXCHANGE,RabbitMQDirectConfig.DIRECT_ROUTE_KEY,obj);
        log.info("sendDirectQueue已发送消息");
    }
}
