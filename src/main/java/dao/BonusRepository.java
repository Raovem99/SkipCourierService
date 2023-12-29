package dao;

import dto.Bonus;
import dto.Courier;
import dto.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
    List<Bonus> findBonusesByDelivery(Delivery delivery);
}
