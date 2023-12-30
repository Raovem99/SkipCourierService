package com.skip.dish.dao;

import com.skip.dish.dao.dto.Delivery;
import com.skip.dish.dao.dto.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface BonusRepository extends JpaRepository<Bonus, Long> {
    List<Bonus> findBonusesByDelivery(Delivery delivery);
}
