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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class KafkaEventListener {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEventListener.class);

    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private BonusRepository bonusRepository;
    @Autowired
    private AdjustmentRepository adjustmentRepository;

    @KafkaListener(topics = "delivery", groupId = "dlvry_con")
    public void listenDeliveryTopic(Delivery delivery) {
        try {
            logger.info("Received Delivery Message in group dlvry_con: {}", delivery);
            deliveryRepository.saveAndFlush(delivery);
        } catch (Exception e) {
            logger.error("Error processing delivery message: ", e);
            // Handle the exception as per business logic
        }
    }

    @KafkaListener(topics = "adjustment", groupId = "dlvry_con")
    public void listenAdjustmentTopic(Adjustment adjustment) {
        try {
            logger.info("Received Adjustment Message in group dlvry_con: {}", adjustment);
            adjustmentRepository.saveAndFlush(adjustment);
        } catch (Exception e) {
            logger.error("Error processing adjustment message: ", e);
            // Handle the exception as per business logic
        }
    }

    @KafkaListener(topics = "bonus", groupId = "dlvry_con")
    public void listenBonusTopic(Bonus bonus) {
        try {
            logger.info("Received Bonus Message in group dlvry_con: {}", bonus);
            bonusRepository.saveAndFlush(bonus);
        } catch (Exception e) {
            logger.error("Error processing bonus message: ", e);
            // Handle the exception as per business logic
        }
    }
}
