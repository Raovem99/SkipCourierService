package dao;

import dto.Adjustment;
import dto.Bonus;
import dto.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdjustmentRepository extends JpaRepository<Adjustment, Long> {

    List<Adjustment> findAdjustmentsByDelivery(Delivery delivery);
}
