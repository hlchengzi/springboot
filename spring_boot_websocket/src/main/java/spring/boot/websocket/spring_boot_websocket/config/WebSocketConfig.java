package spring.boot.websocket.spring_boot_websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;
import spring.boot.websocket.spring_boot_websocket.handler.WebSocketErrorHandler;

/**
 * WebSocket 配置
 *
 * @author xcxu
 * @date 2019/8/10
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.setErrorHandler(this.webSocketHandler())
                // addEndpoint注册STOMP协议节点
                .addEndpoint("/endpointNiu")
                .setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 启用一个简单的基于内存的消息代理，以在前缀为“/topic”的目标上将问候消息传回客户端(广播方式)
        //Spring就能知道所有目的地前缀为“/topic”的消息都会发送到STOMP代理中
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setUserDestinationPrefix("/user");
    }

    /**
     * WebSocket Error 处理
     *
     * @return WebSocket Error 处理器
     */
    @Bean
    public StompSubProtocolErrorHandler webSocketHandler() {
        return new WebSocketErrorHandler();
    }
}