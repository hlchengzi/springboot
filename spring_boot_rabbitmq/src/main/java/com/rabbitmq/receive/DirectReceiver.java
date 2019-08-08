package com.rabbitmq.receive;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.config.RabbitMQDirectConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * direct模式消费者
 *
 * @author xcxu
 */
@Component
@Slf4j
public class DirectReceiver {
    /**
     * 注解上是要里面需要注解的名称
     * @param obj
     * @throws InterruptedException
     */
    static int count1=0;
    static int count2=0;

    @RabbitListener(queues = RabbitMQDirectConfig.DIRECT_QUEUE)
    public void receiverDirect(JSONObject obj) throws InterruptedException {
        Thread.sleep(100);
        log.info("receiverDirectQueue-1111-收到消息" +(++count1)+ obj.toString());
    }

    @RabbitListener(queues = RabbitMQDirectConfig.DIRECT_QUEUE)
    public void receiverDirect2(JSONObject obj) throws InterruptedException {
        int count=0;
        Thread.sleep(1000);
        log.info("receiverDirectQueue-2222-收到消息"  +(++count2)+ obj.toString());
    }
}
