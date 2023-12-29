package kafka;

import dto.Delivery;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class KafkaProducerTest {
    private MockProducer<String, String> mockProducer;
    private KafkaProducer eventProducer;

    @BeforeEach
    public void setUp() {
        mockProducer = new MockProducer<>(true, new StringSerializer(), new StringSerializer());
        KafkaProducer KafkaProducer = new KafkaProducer((Map<String, Object>) mockProducer);
    }

    @Test
    public void testSendEvent() throws Exception {
        Delivery event = new Delivery(/* ... */);


        assertTrue(mockProducer.history().size() == 1);
        ProducerRecord<String, String> sentRecord = mockProducer.history().get(0);
        // Assertions on the sent record
    }
}

