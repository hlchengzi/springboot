package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Direct模式
 *
 * @author xcxu
 */
@Configuration
public class RabbitMQDirectConfig {

    public static final String DIRECT_QUEUE = "direct.queue";
    public static final String DIERECT_EXCHANGE = "direct.exchange";
    public static final String DIRECT_ROUTE_KEY = "com.rabbit.direct";

    /**
     * Direct模式
     * Rabbit 默认的消息传递模式
     * 四个参数
     * 1，队列名字
     * 2，持久化消息队列 默认是false
     * 3，表示队列在没人使用的时候将会被删除  默认是false
     * 4，表示消息队列是否在当前connnecttion生效，默认是false
     * @return
     */


    /**
     * 建立队列
     * @return
     */
    @Bean
    public Queue directQueue() {
        /**
         * 1，队列名称
         * 2，是否支持持久化
         */
        return new Queue(DIRECT_QUEUE);
    }

    /**
     * 建立交换机,
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        /**
         * 交换机名称
         */
        return new DirectExchange(DIERECT_EXCHANGE);
    }

    /**
     * 建立绑定
     * @return
     */
    @Bean
    public Binding directBinding() {
        /**
         * 将队列和交换机进行绑定，并设置绑定绑定键
         */
        return BindingBuilder.bind(directQueue()).
                to(directExchange()).
                with(DIRECT_ROUTE_KEY);
    }


}
