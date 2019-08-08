package spring.boot.activem.spring_boot_activemq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xcxu
 * @data 2019/8/8
 **/
@Configuration
@EnableAutoConfiguration
public  class ActivemqConfig {

    /**
     * 声明queue模式下的队列名称
     * @return
     */
    @Bean
    public static ActiveMQQueue queue(){
        return new ActiveMQQueue("QueuesMessage");
    }

    /**
     * 声明 topic模式下的队列名称
     * @return
     */
    @Bean
    public static  ActiveMQTopic topic(){
        return new ActiveMQTopic("topic message");
    }

}
