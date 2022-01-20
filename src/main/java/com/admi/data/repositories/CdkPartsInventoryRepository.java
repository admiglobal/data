package com.admi.data.repositories;

import com.admi.data.entities.CdkPartsInventoryChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CdkPartsInventoryRepository extends JpaRepository<CdkPartsInventoryChild, Long> {

	List<CdkPartsInventoryChild> findAllByDealerIdAndInventoryDate(String cdkDealerId, LocalDate date);

	@Transactional
	@Modifying
	@Query( value = "CALL DELETE_CDK_INVENTORY_P()",
			nativeQuery = true)
	void deleteOldInventory();

}
