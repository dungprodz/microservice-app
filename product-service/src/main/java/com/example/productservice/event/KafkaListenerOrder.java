package com.example.productservice.event;

import com.example.productservice.util.GroupKafka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerOrder {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListenerOrder.class);
    @KafkaListener(topics = "${spring.kafka.order-topic}", groupId = GroupKafka.ORDER_TOPIC_GROUP, containerFactory = "kafkaListenerContainerFactory")
    public void receiveMessage(String message) {

    }
}
