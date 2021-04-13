package com.websocket.websocket.controller;

import com.websocket.websocket.constants.KafkaConstants;
import com.websocket.websocket.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
@RestController
public class ChatController {
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @PostMapping(value = "/api/send", consumes="application/json", produces = "application/json")
    public void sendMessage(@RequestBody Message message){
        message.setTimestamp(LocalDateTime.now().toString());
        try{
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
        }catch (InterruptedException | ExecutionException e){
            throw  new RuntimeException(e);
        }
    }

//    Web socket API
    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public Message broadcastGroupMessage(@Payload Message message){
        return message;
    }

    @MessageMapping("/newUser")
    @SendTo("/topic/group")
    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }
}
