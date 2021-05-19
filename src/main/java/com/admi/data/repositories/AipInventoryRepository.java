package com.admi.data.repositories;

import com.admi.data.entities.AipInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AipInventoryRepository extends JpaRepository<AipInventoryEntity, Long> {

	List<AipInventoryEntity> findAllByDealerIdAndDataDate(Long dealerId, LocalDate dataDate);

}
