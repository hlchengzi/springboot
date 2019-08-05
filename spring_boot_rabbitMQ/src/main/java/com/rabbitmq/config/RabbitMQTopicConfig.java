package com.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Topic模式
 *主题交换机
 * @author xcxu
 */
@Configuration
public class RabbitMQTopicConfig {

    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";

    public static final String TOPIC_EXCHANGE = "topic.exchange";


    public static  final String TOPIC_BINDING_KEY1 = "*.rabbitmq.*";

    public static  final String TOPIC_BINDING_KEY2 = "*.*.client";

    public static  final String TOPIC_BINDING_KEY3 = "com.#";

    @Bean
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE1, false);
    }
    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE, true, false);
    }

    /**
     * 声明routing key和exchang绑定，当消息传到该exchange并拥有此routing key的消息将会被映射到相应的queue当中
     * @return
     */
    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(TOPIC_BINDING_KEY1);
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(TOPIC_BINDING_KEY2);
    }

    @Bean
    public Binding topicBinding3() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(TOPIC_BINDING_KEY3);
    }

}
