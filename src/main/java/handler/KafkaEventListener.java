package handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.tools.json.JSONUtil;
import dao.AdjustmentRepository;
import dao.BonusRepository;
import dao.DeliveryRepository;
import dto.Adjustment;
import dto.Bonus;
import dto.Delivery;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.JsonPOJOSerializer;

import java.util.Arrays;
import java.util.Properties;
import java.time.Duration;
@Component
public class KafkaEventListener {

@Autowired
    DeliveryRepository deliveryRepository;
@Autowired
    BonusRepository bonusRepository;
@Autowired
AdjustmentRepository adjustmentRepository;
    private final KafkaConsumer<String, String> consumer;

    public KafkaEventListener(String bootstrapServers, String groupId, String... topics) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topics));
    }

    public void listen() throws JsonProcessingException {
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Received message: key = %s, value = %s, topic = %s, partition = %d, offset = %d\n",
                            record.key(), record.value(), record.topic(), record.partition(), record.offset());

                    // Handle the record (deserialize and process)
                    processRecord(record);
                }
            }
        } finally {
            consumer.close();
        }
    }

    public void processRecord( ConsumerRecord<String, String> record) throws JsonProcessingException {
        if (record.key().startsWith("Delivery")){
            String value = record.value();
            Delivery d = (Delivery) JsonPOJOSerializer.fromJson(record.value(), Delivery.class);
            deliveryRepository.save(d);
            } else if (record.key().startsWith("Bonus")) {
            String value = record.value();
            Bonus d = (Bonus) JsonPOJOSerializer.fromJson(record.value(), Bonus.class);
            bonusRepository.save(d);
        } else if (record.key().startsWith("Adjustment")) {
            String value = record.value();
            Adjustment d = (Adjustment) JsonPOJOSerializer.fromJson(record.value(), Adjustment.class);
            adjustmentRepository.save(d);

        }
    }
    }







