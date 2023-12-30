package com.skip.dish.handler;

import com.skip.dish.dao.AdjustmentRepository;
import com.skip.dish.dao.BonusRepository;
import com.skip.dish.dao.DeliveryRepository;
import com.skip.dish.dao.dto.Adjustment;
import com.skip.dish.dao.dto.Delivery;
import com.skip.dish.dao.dto.Bonus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventListener {

@Autowired
DeliveryRepository deliveryRepository;
@Autowired
BonusRepository bonusRepository;
@Autowired
AdjustmentRepository adjustmentRepository;


   /* public void processRecord( ConsumerRecord<String, String> record) throws JsonProcessingException {
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
    }*/
    @KafkaListener(topics = "delivery", groupId = "dlvry_con")
    public void listenDeliveryTopic(Delivery delivery) {
        System.out.println("Received Message in group dlvry_con: " + delivery);
        deliveryRepository.saveAndFlush(delivery);
    }
    @KafkaListener(topics = "adjustment", groupId = "dlvry_con")
    public void listenAdjustmentTopic(Adjustment adjustment) {
        System.out.println("Received Message in group dlvry_con: " + adjustment);
        adjustmentRepository.saveAndFlush(adjustment);
    }
    @KafkaListener(topics = "bonus", groupId = "dlvry_con")
    public void listenBonusTopic(Bonus bonus) {
        System.out.println("Received Message in group dlvry_con: " + bonus);
        bonusRepository.saveAndFlush(bonus);
    }
    }







