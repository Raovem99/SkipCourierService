package com.skip.dish.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg, String topicName) {
        try {
            kafkaTemplate.send(topicName, msg).get(); // Blocking call to ensure message is sent
            logger.info("Message sent to topic {}: {}", topicName, msg);
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Failed to send message to topic {}: {}", topicName, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void sendMessageCon(String message, String topicName) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
            } else {
                logger.error("Unable to send message=[{}] due to: {}", message, ex.getMessage());
            }
        });
    }
}
