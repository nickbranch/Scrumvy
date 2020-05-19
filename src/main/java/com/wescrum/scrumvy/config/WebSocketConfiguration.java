package com.wescrum.scrumvy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer{

    @Override
    public void registerStompEndpoints (StompEndpointRegistry registry){
        //Stomp websocket protocol
        registry.addEndpoint("/chat").setAllowedOrigins("*").withSockJS();
    }
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //message broker
        registry.enableSimpleBroker("/topic"); 
        //message endpoints
        registry.setApplicationDestinationPrefixes("/app");   
    }
    
}