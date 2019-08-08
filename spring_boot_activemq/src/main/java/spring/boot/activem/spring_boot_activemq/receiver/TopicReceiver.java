package spring.boot.activem.spring_boot_activemq.receiver;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author xcxu
 * @data 2019/8/8
 **/

@Slf4j
@Component
@EnableJms
public class TopicReceiver {

    @JmsListener(destination = "Topics message")
    public void receiveQueue(String message) throws InterruptedException {
        log.info("接收消息 topic--------------：" + message);
        Thread.sleep(100);
    }
}
