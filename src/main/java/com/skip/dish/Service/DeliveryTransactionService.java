package com.skip.dish.Service;

import com.skip.dish.dao.AdjustmentRepository;
import com.skip.dish.dao.BonusRepository;
import com.skip.dish.dao.DeliveryRepository;
import com.skip.dish.domain.DeliveryTransaction;
import com.skip.dish.dao.dto.Adjustment;
import com.skip.dish.dao.dto.Bonus;
import com.skip.dish.dao.dto.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DeliveryTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryTransactionService.class);

    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private BonusRepository bonusRepository;
    @Autowired
    private AdjustmentRepository adjustmentRepository;

    public DeliveryTransaction getDeliveryTransactionsByCourierAndPeriod(String courierId, LocalDate startDate, LocalDate endDate) {
        try {
            List<Delivery> deliveries = deliveryRepository.findByCourierIdAndPeriod(courierId, startDate.atStartOfDay(), endDate.atStartOfDay());
            return getDeliveryTransaction(deliveries);
        } catch (Exception e) {
            logger.error("Error fetching transactions by courier and period: ", e);
            throw e;
        }
    }

    public DeliveryTransaction getWeeklyStatementForCourier(String courierId) {
        try {
            LocalDate endDate = LocalDate.now().minusDays(6);
            LocalDate startDate = LocalDate.now();
            return getDeliveryTransactionsByCourierAndPeriod(courierId, startDate, endDate);
        } catch (Exception e) {
            logger.error("Error fetching weekly statement for courier: ", e);
            // Handle or rethrow the exception
            throw e;
        }
    }

    public DeliveryTransaction getAllDeliveries() {
        try {
            List<Delivery> deliveries = deliveryRepository.findAll();
            return getDeliveryTransaction(deliveries);
        } catch (Exception e) {
            logger.error("Error fetching all deliveries: ", e);
            // Handle or rethrow the exception
            throw e;
        }
    }

    private DeliveryTransaction getDeliveryTransaction(List<Delivery> deliveries) {
        try {
            List<Bonus> bonuses = new ArrayList<>();
            List<Adjustment> adjustments = new ArrayList<>();
            deliveries.forEach(delivery -> {
                bonuses.addAll(bonusRepository.findBonusesByDelivery(delivery));
                adjustments.addAll(adjustmentRepository.findAdjustmentsByDelivery(delivery));
            });
            double deliveriesValue = deliveries.stream().mapToDouble(Delivery::getValue).sum();
            double bonusValue = bonuses.stream().mapToDouble(Bonus::getValue).sum();
            double adjustmentValue = adjustments.stream().mapToDouble(Adjustment::getValue).sum();
            DeliveryTransaction deliveryTransaction = new DeliveryTransaction();
            deliveryTransaction.setDeliveries(deliveries);
            deliveryTransaction.setValue(deliveriesValue + bonusValue + adjustmentValue);
            return deliveryTransaction;
        } catch (Exception e) {
            logger.error("Error aggregating delivery transaction: ", e);
            // Handle or rethrow the exception
            throw e;
        }
    }
}



