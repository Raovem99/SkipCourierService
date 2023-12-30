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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryTransactionService {

    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private BonusRepository bonusRepository;
    @Autowired
    private AdjustmentRepository adjustmentRepository;

    public DeliveryTransaction getDeliveryTransactionsByCourierAndPeriod(String courierId, LocalDate startDate, LocalDate endDate) {
        // Fetch transactions from the repository
        List<Delivery> deliveries = deliveryRepository.findByCourierIdAndPeriod(courierId, startDate.atStartOfDay(), endDate.atStartOfDay());
        return getDeliveryTransaction(deliveries);
    }

    public DeliveryTransaction getWeeklyStatementForCourier(String courierId) {
        LocalDate endDate = LocalDate.now().minusDays(6);
        LocalDate startDate = LocalDate.now();
        return getDeliveryTransactionsByCourierAndPeriod(courierId, startDate, endDate);
    }

    public DeliveryTransaction getAllDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll();

        return getDeliveryTransaction(deliveries);
    }

    private DeliveryTransaction getDeliveryTransaction(List<Delivery> deliveries) {

        List<Bonus> bonuses = new ArrayList<>();
        List<Adjustment> adjustments = new ArrayList<>();
        deliveries.stream().forEach(delivery -> {
            bonuses.addAll(bonusRepository.findBonusesByDelivery(delivery));
            adjustments.addAll(adjustmentRepository.findAdjustmentsByDelivery(delivery));
        });
        double deliveriesValue = deliveries.stream().mapToDouble(Delivery::getValue).sum();
        double bonusValue = bonuses.stream().mapToDouble(Bonus::getValue).sum();
        double adjustmentValue = adjustments.stream().mapToDouble(Adjustment::getValue).sum();
        DeliveryTransaction deliveryTransaction = new DeliveryTransaction();
        deliveryTransaction.setDeliveries(deliveries);
        deliveryTransaction.setValue((deliveriesValue+bonusValue+adjustmentValue));
        return deliveryTransaction;
    }

}


