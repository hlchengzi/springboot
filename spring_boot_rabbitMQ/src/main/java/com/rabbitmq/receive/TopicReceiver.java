package com.rabbitmq.receive;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.config.RabbitMQTopicConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * topic模式消费者
 *
 * @author xcxu
 */
@Component
@Slf4j
public class TopicReceiver {

    /**
     *  queues是指要监听的队列的名字
     * @param obj
     */
    @RabbitListener(queues = RabbitMQTopicConfig.TOPIC_QUEUE1)
    public void receiveTopic1(JSONObject obj) {
        log.info("receiveTopic收到消息---> 1--:" + obj.toString());
    }

    @RabbitListener(queues = RabbitMQTopicConfig.TOPIC_QUEUE2)
    public void receiveTopic2(JSONObject obj) {
        log.info("receiveTopic收到消息---> 2--:" + obj.toString());
    }
}
