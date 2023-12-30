package com.skip.dish.config;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTopicConfig.class);
    /* @Value(value = "${spring.kafka.bootstrap-servers}")*/
    private final String bootstrapAddress = "localhost:9092";

    @Bean
    public KafkaAdmin kafkaAdmin() {
        try {
            Map<String, Object> configs = new HashMap<>();
            configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            return new KafkaAdmin(configs);
        } catch (Exception e) {
            logger.error("Failed to configure Kafka Admin: ", e);
            throw e; // Rethrowing the exception to be handled by the Spring context
        }
    }

    @Bean
    public NewTopic topic1() {
        try {
            return new NewTopic("delivery", 1, (short) 1);
        } catch (Exception e) {
            logger.error("Failed to create Kafka topic: ", e);
            throw e; // Rethrowing the exception to be handled by the Spring context
        }
    }
    @Bean
    public NewTopic topic2() {
        try {
            return new NewTopic("adjustment", 1, (short) 1);
        } catch (Exception e) {
            logger.error("Failed to create Kafka topic: ", e);
            throw e; // Rethrowing the exception to be handled by the Spring context
        }    }
    @Bean
    public NewTopic topic3() {
        try {
            return new NewTopic("bonus", 1, (short) 1);
        } catch (Exception e) {
            logger.error("Failed to create Kafka topic: ", e);
            throw e; // Rethrowing the exception to be handled by the Spring context
        }    }

}
