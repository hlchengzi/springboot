package com.rabbitmq.send;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class AckSender implements RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("sender return success" + message.toString()+"==="+i+"==="+s1+"==="+s2);
    }
    public void send() {
        String context = "你好现在是 " + new Date() +"";
        System.out.println("AckSender 发送内容 : " + context);
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.out.println("AckSender 消息发送失败" + cause + correlationData.toString());
            } else {
                System.out.println("AckSender 消息发送成功 ");
            }
        });
        this.rabbitTemplate.convertAndSend("hello", context);
    }

    public void sendObj() {
        String s="{\"routeKeyName\":\""+"hello_ACK"+"\"}";
        JSONObject obj = JSON.parseObject(s);
        System.out.println("发送 : " + obj);
        this.rabbitTemplate.convertAndSend("helloObj", obj);
    }

}
