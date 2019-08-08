package com.rabbitmq.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.commons.R;
import com.rabbitmq.send.DirectSender;
import com.rabbitmq.send.FanoutSender;
import com.rabbitmq.send.TopicSender;

/**
 * 消息路由规则：四种模式（topic、direct、fanout、header）
 * topic：根据绑定关键字通配符规则匹配、比较灵活
 * direct：默认，根据routingKey完全匹配，好处是先匹配再发送
 * fanout：不需要指定routingkey，相当于群发
 * header：不太常用，可以自定义匹配规则
 *
 * @author xcxu
 */
@Slf4j
@Controller
@RequestMapping("/rabbit")
public class MessageController {

    @Autowired
    private TopicSender topicSender;

    @Autowired
    private DirectSender directSender;

    @Autowired
    private FanoutSender fanoutSender;

    String s="[{\"success\":true,\"data\":[{\"building_id\":\"***\",\"building_num\":\"**\",\"room_name\":\"**\",\"door_name\":\"**\",\"electric\":\"**\"}]}]";
    String b= s.substring(0,s.length()-1);
    String c=b.substring(1, b.length());
    JSONObject obj = JSON.parseObject(c);

    /**
     * topic 模式
     * http://localhost:8081/rabbit/sendTopic
     * @return
     */
    @GetMapping("/sendTopic")
    @ResponseBody
    public R sendTopic() {

        for (int i =0 ;i<10000;i++) {
            topicSender.sendTopic(obj);
        }
        return R.ok();
    }

    /**
     * direct 模式 <strong>发送者与接收者的Queue名字一定要相同，否则接收收不到消息</strong>
     *  http://localhost:8081/rabbit/sendDirect
     * @return
     */
    @GetMapping("/sendDirect")
    public R sendDirect() {
        for (int i = 0; i < 100; i++) {
            directSender.sendDirect(obj);
        }
        return R.ok();
    }

    /**
     * fanout模式
     * http://localhost:8081/rabbit/sendFanout
     * @return
     */
    @GetMapping("/sendFanout")
    public R sendFanout() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            fanoutSender.sendFanout(obj);
        }
        return R.ok();
    }
}
