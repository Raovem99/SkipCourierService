package config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaProducerConfig {
    private final KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaProducerConfig() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        this.producer = new KafkaProducer<>(props);
    }

    public <T> void sendEvent(String topic, T event) throws Exception {
        try {
            String message = objectMapper.writeValueAsString(event);
            // Synchronous send to capture exceptions effectively
            RecordMetadata metadata = producer.send(new ProducerRecord<>(topic, message)).get();
            // Optionally log successful send
            System.out.println("Message sent successfully to topic " + metadata.topic());
        } catch (ExecutionException e) {
            // Handle exceptions related to record sending
            System.err.println("Error sending message to Kafka: " + e.getMessage());
        } catch (InterruptedException e) {
            // Handle the case where to send was interrupted
            Thread.currentThread().interrupt();
            System.err.println("Send operation was interrupted: " + e.getMessage());
        } catch (Exception e) {
            // Handle serialization and other exceptions
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // Call this method to close the producer
    public void close() {
        producer.close();
    }
}

