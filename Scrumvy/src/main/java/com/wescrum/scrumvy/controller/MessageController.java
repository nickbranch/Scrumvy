
package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.MessageModel;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
//handle messages and send it to the users
    
    @MessageMapping("/message/{projectId}") //from which url u send the message maybe add the whole project id here
    @SendTo("/topic/messages/{projectId}")//pou tha staloun ta minimata - opios client einai gramenos sto topic/messages tha lamvanei ta minimata
    public MessageModel sendMessage(@DestinationVariable int projectId, MessageModel message){
        System.out.println("handling send message: " + message + " for project " + projectId);
        //simpMessagingTemplate.convertAndSend("/topic/messages/", message);
        return message;
    }
    
}
