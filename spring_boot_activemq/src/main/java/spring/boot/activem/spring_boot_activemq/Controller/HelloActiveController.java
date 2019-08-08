package spring.boot.activem.spring_boot_activemq.Controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Queue;

/**
 * @author xcxu
 * @data 2019/8/8
 **/

@Slf4j
@Controller
@RequestMapping("/activemq")
public class HelloActiveController {

    @Autowired
    private ActiveMQQueue queue;

    @Autowired
    private ActiveMQTopic topic;

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     *  http://localhost:8004/activemq/queue
     * @return
     */
    @GetMapping("/queue")
    public ResponseEntity<String> activemqQueue(){
        log.info("Execute HelloActiveController activemqQueue method ");

        String message = "hello activemq  QUEUE  !";
        jmsTemplate.convertAndSend(queue, message);
        return new ResponseEntity(message, HttpStatus.OK);
    }

    /**
     *  http://localhost:8004/activemq/topic
     * @return
     */
    @GetMapping("/topic")
    public ResponseEntity<String> activemqTopic(){
        log.info("Execute HelloActiveController activemqTopic method ");

        String message = "hello activemq  Topic  !";
        jmsTemplate.convertAndSend(queue, message);
        return new ResponseEntity(message, HttpStatus.OK);
    }

    /**
     *  http://localhost:8004/activemq/queueBatch
     * @return
     */
    @GetMapping("/queueBatch")
    public ResponseEntity<String> activemqQueueBatch(){
        log.info("Execute HelloActiveController activemqQueueBatch method ");

        String message = "hello activemq  QUEUEBatch  !";
        for(int i=0;i<100;i++) {
            jmsTemplate.convertAndSend(queue, String.valueOf(i)+"-->"+message);
        }
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
