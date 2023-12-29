package kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.record.TimestampType;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.mockito.Mockito.*;

public class KafkaConsumerTest {

    @Test
    public void testProcessRecord() {
        // Assuming processRecord is the method that contains business logic
        KafkaConsumer consumer = mock(KafkaConsumer.class);

        ConsumerRecord<String, String> record = new ConsumerRecord<>(
                "DeliveryCreated", 0, 0, "key", "{\"deliveryId\":\"123\"}");

        consumer.resume((Collection<TopicPartition>) record);
        verify(consumer, times(1)).resume((Collection<TopicPartition>) record);
        // Add more verifications/assertions based on your business logic
    }
}
