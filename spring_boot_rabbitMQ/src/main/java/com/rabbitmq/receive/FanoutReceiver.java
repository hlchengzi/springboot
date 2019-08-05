package com.rabbitmq.receive;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.config.RabbitMQFanoutConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * fanout模式消费者
 *
 * @author xcxu
 */
@Component
@Slf4j
public class FanoutReceiver {

     static int count1=0;
    static int count2=0;

    /**
     * queues是指要监听的队列的名字
     *
     */
    @RabbitListener(queues = RabbitMQFanoutConfig.FANOUT_QUEUE)
    public synchronized void receiveFanout(JSONObject obj) {
        log.info("receiveFanout收到消息---1---" +(++count1)+ obj.toString());
    }

    @RabbitListener(queues = RabbitMQFanoutConfig.FANOUT_QUEUE1)
    public synchronized void receiveFanout1(JSONObject obj) {
        log.info("receiveFanout收到消息----2----" +(++count2)+ obj.toString());
    }
}
