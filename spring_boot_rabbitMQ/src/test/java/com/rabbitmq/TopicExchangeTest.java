package com.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.send.AckSender;
import com.rabbitmq.send.TopicSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicExchangeTest {
    @Autowired
    TopicSender topicSender;
    @Autowired
    AckSender ackSender;

    String key1 =  "com.rabbitmq.client";
    String key2=  "com.hidden.client";
    String key3 =  "com.rabbitmq.demo";
    String key4 =  "java.rabbitmq.client";
    String key5 =  "com.util.concurrent";

    public void topicTest(String key){
        String s="{\"routeKeyName\":\""+key+"\"}";
        JSONObject obj = JSON.parseObject(s);
        topicSender.sendTopic(obj,key);
    }

    @Test
    public void topicTest1(){
        topicTest(key1);
    }

    @Test
    public void topicTest2(){
        topicTest(key2);
    }

    @Test
    public void topicTest3(){
        topicTest(key3);
    }
    @Test
    public void topicTest4(){
        topicTest(key4);
    }
    @Test
    public void topicTest5(){
        topicTest(key5);
    }

    @Test
    public void ackTest(){
        ackSender.send();
    }


}
