package com.skip.dish.dao;

import com.skip.dish.dao.dto.Adjustment;
import com.skip.dish.dao.dto.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface AdjustmentRepository extends JpaRepository<Adjustment, Long> {

    List<Adjustment> findAdjustmentsByDelivery(Delivery delivery);
}
