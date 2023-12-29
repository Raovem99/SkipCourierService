package dao;

import domain.DeliveryTransaction;
import dto.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Query(value = "SELECT * from DELIVERIES WHERE courierId=?1 and trunc(createdTime) between ?2 and ?3", nativeQuery = true)
    List<Delivery> findByCourierIdAndPeriod(String courierId, LocalDateTime startDate, LocalDateTime endDate);
}
