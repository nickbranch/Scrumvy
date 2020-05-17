
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
        //Stomp einai to protokolo gia to websocket
        registry.addEndpoint("/chat").setAllowedOrigins("*").withSockJS();
        //edo sou dixnei oti gia na sindethei enas client sto chat to endpoint pou akouei einai /chat
        //allowed origins einai oti kapios mporei na mpei apo padou dld an kapios exei url mporei na mpei sto chat enpo an thes mporeis na to perioriseis
        //px an thelame kapios na mpenei mono apo to scrumvy tha vazame scrumvy.com
    }
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //xeirizete ta minimata
        registry.enableSimpleBroker("/topic"); 
        //ta endpoints pou akoune kai stelnoune minimata
        registry.setApplicationDestinationPrefixes("/app");  
        
    //otan thelo na steilo kati sto socket ta epitrepta prefixes pou exo thelo einai /app
    //px mporeis na exeis socket video etc
    
    }
    
}
