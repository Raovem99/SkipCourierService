package com.skip.dish.dao;

import com.skip.dish.dao.dto.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
}
