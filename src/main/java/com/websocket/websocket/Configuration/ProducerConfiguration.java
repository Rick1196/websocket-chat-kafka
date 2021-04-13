package com.websocket.websocket.Configuration;

import org.springframework.kafka.support.serializer.JsonSerializer;
import com.websocket.websocket.model.Message;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ProducerConfiguration {
    @Bean
    public ProducerFactory<String, Message> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfigurations());
    }

    @Bean
    public Map<String, Object> producerConfigurations(){
        Map<String, Object> configurations = new HashMap<>();
        // address on which kafka is running
        configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // to deserialize the key and value from kafka queue
        configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configurations;
    }

    /**
     * perform high-level operations on the producer.
     * The template can do operations such as sending a message to a topic.
     * @return kafka template
     */
    @Bean
    public KafkaTemplate<String, Message> KafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
