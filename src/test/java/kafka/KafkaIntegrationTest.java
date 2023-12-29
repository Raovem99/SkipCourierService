package kafka;

import jakarta.inject.Inject;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class KafkaIntegrationTest {

    @Inject
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void testKafkaProducerAndConsumer() {
        // Your test code here
    }
}
