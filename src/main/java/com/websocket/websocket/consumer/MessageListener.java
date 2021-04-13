package com.websocket.websocket.consumer;

import com.websocket.websocket.constants.KafkaConstants;
import com.websocket.websocket.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @Autowired
    SimpMessagingTemplate template;
    // anotation to set the method that will listen the kafka queues messages
    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = KafkaConstants.GROUP_ID
    )
    public void listen(Message message){
        System.out.println("Sending via kafka listener ...");
        // convert and send the message to the webSocket topic
        template.convertAndSend("/topic/group", message);
    }
}
