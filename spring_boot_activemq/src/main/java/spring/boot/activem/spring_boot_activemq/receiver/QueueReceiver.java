package spring.boot.activem.spring_boot_activemq.receiver;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import spring.boot.activem.spring_boot_activemq.config.ActivemqConfig;

/**
 * @author xcxu
 * @data 2019/8/8
 *
 * @description:  监听相关的队列，然后处理消息
 **/
@Slf4j
@Component
@EnableJms
public class QueueReceiver {


    @JmsListener(destination = "QueuesMessage")
    public void receiveQueue(String message){
       log.info("接收消息 queue--------------：" + message);

    }
}
