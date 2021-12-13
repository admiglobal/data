package com.admi.data.repositories;

import com.admi.data.entities.MixPartsInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface MixPartsInventoryRepository extends JpaRepository<MixPartsInventoryEntity, Long> {

	List<MixPartsInventoryEntity> findAllByDealerIdAndInventoryDate(Long dealerId, LocalDate date);

	@Transactional
	@Modifying
	@Query( value = "CALL DELETE_MIX_INVENTORY_P()",
			nativeQuery = true)
	void deleteOldInventory();

}
