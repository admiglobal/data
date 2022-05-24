package com.admi.data.repositories;

import com.admi.data.entities.TipOrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TipOrderDetailRepository extends JpaRepository<TipOrderDetailEntity, Long> {

	List<TipOrderDetailEntity> findAllByDealerIdAndDataDateEquals(Long dealerId, LocalDateTime dataDate);

}
