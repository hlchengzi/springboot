package com.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Fanout模式
 * 扇形交换机
 * @author xcxu
 */
@Configuration
public class RabbitMQFanoutConfig {
    /**
     * Fanout模式 Fanout 就是我们熟悉的广播模式或者订阅模式，
    * 给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
     * 将消息绑定给它身上的所有队列
     * 路由键在在此类型上不启任何作用
     */

    public static final String FANOUT_QUEUE = "fanout.queue";
    public static final String FANOUT_QUEUE1 = "fanout.queue1";

    public static final String FANOUT_EXCHANGE = "fanout.exchange";

    /**
     * 建立fanout队列
     * @return
     */
    @Bean
    public Queue fanoutQueue() {
        return new Queue(FANOUT_QUEUE);
    }
    @Bean
    public Queue fanoutQueue1() {
        return new Queue(FANOUT_QUEUE1);
    }

    /**
     * 建立扇形交换机
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    /**
     * 绑定，将队列和exchange进行绑定，绑定了这个交换机的所有队列都能接受到消息
     * 绑定时候不需要路由键
     * @return
     */
    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }
    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }


    /**
     * 为了测试ack模式建立hello和helloobj队列
     */

    @Bean
    public Queue QueueA() {
        return new Queue("hello");
    }

    @Bean
    public Queue QueueB() {
        return new Queue("helloObj");
    }

    /**
     * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
     * @return
     */
    @Bean
    FanoutExchange fanoutExchangeack() {
        return new FanoutExchange("ABExchange");
    }


    @Bean
    Binding bindingExchangeA(Queue QueueA, FanoutExchange fanoutExchangeack) {
        return BindingBuilder.bind(QueueA).to(fanoutExchangeack);
    }

    @Bean
    Binding bindingExchangeB(Queue QueueB, FanoutExchange fanoutExchangeack) {
        return BindingBuilder.bind(QueueB).to(fanoutExchangeack);
    }

}
