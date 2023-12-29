package config;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.KafkaException;

import java.util.Properties;
import java.util.Collections;

public class KafkaConsumerConfig {



        public void startConsumer() {
            Properties props = new Properties();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "event-consumer-group");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

            try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
                consumer.subscribe(Collections.singletonList("DeliveryCreated"));
                // Add logic to handle events from Kafka
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(100); // polling interval
                    // Add logic to process records
                }
            } catch (KafkaException e) {
                // Handle Kafka-specific exceptions
                System.err.println("Error in Kafka consumer: " + e.getMessage());
            } catch (Exception e) {
                // Handle any other exceptions
                System.err.println("An error occurred: " + e.getMessage());
            }
            }
        }



