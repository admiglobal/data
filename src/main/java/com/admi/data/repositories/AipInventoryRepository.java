package com.admi.data.repositories;

import com.admi.data.entities.AipInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface AipInventoryRepository extends JpaRepository<AipInventoryEntity, Long> {

	List<AipInventoryEntity> findAllByDealerIdAndDataDate(Long dealerId, LocalDate dataDate);


	@Transactional
	@Modifying
	@Query( value = "CALL DELETE_AIP_INVENTORY_P()",
			nativeQuery = true)
	void deleteOldInventory();

//	@Query( value = "SELECT AI.*\n" +
//			"FROM AIP_INVENTORY AI\n" +
//			"LEFT OUTER JOIN FORD_PT FPT\n" +
//			"ON AI.PARTNO = FPT.PARTNO\n" +
//			"WHERE AI.QOH > 0\n" +
//			"AND AI.DEALER_ID = :dealerId\n" +
//			"AND AI.DATA_DATE = :dataDate\n" +
//			"AND FPT.TAPE = 'FORD_US'\n" +
//			"AND FPT.COLLISION != 'Y'", nativeQuery = true)
//	List<AipInventoryEntity> findAllNonCollisionPartsInInventory(
//			@Param("dealerId") Long dealerId,
//			@Param("dataDate") LocalDate dataDate);

	@Query( value = "SELECT DM.DEALER_ID, FDI.PARTNO, FDI.CENTS, FDI.QOH, FDI.DESCRIPTION, FDI.STATUS, FDI.LAST_SALE, FDI.LAST_RECEIPT, FDI.BIN, FDI.SOURCE, FDI.MFG_CONTROLLED, FDI.DATA_DATE, FDI.ADMI_STATUS, FDI.MANUFACTURER, FDI.QOO, FDI.TWELVE_MONTH_SALES, FDI.ENTRY_DATE\n" +
			"FROM FORD_DEALER_INVENTORY FDI\n" +
			"INNER JOIN FORD_PT FPT\n" +
			"ON FDI.PARTNO = FPT.PARTNO\n" +
			"LEFT OUTER JOIN DEALER_MASTER DM\n" +
			"ON FDI.PA_CODE = DM.PA_CODE\n" +
			"WHERE FDI.QOH > 0\n" +
			"AND DM.DEALER_ID = :dealerId\n" +
			"AND FDI.DATA_DATE = :dataDate\n" +
			"AND FPT.TAPE = 'FORD_US'\n" +
			"AND FPT.COLLISION = 'Y'\n" +
			"AND DM.PRIMARY_MANUFACTURER_ID = 1\n" +
			"AND DM.TERMINATION_DATE IS NULL\n" +
			"AND DM.DEALERSHIP_COUNTRY = 'USA'", nativeQuery = true)
	List<AipInventoryEntity> findAllCollisionPartsInInventory(
			@Param("dealerId") Long dealerId,
			@Param("dataDate") LocalDate dataDate);

	@Query( value = "SELECT DM.DEALER_ID, FDI.PARTNO, FDI.CENTS, FDI.QOH, FDI.DESCRIPTION, FDI.STATUS, FDI.LAST_SALE, FDI.LAST_RECEIPT, FDI.BIN, FDI.SOURCE, FDI.MFG_CONTROLLED, FDI.DATA_DATE, FDI.ADMI_STATUS, FDI.MANUFACTURER, FDI.QOO, FDI.TWELVE_MONTH_SALES, FDI.ENTRY_DATE\n" +
			"FROM FORD_DEALER_INVENTORY FDI\n" +
			"INNER JOIN FORD_PT FPT\n" +
			"ON FDI.PARTNO = FPT.PARTNO\n" +
			"LEFT OUTER JOIN DEALER_MASTER DM\n" +
			"ON FDI.PA_CODE = DM.PA_CODE\n" +
			"WHERE FDI.QOH > 0\n" +
			"AND DM.DEALER_ID = :dealerId\n" +
			"AND FDI.DATA_DATE = :dataDate\n" +
			"AND FPT.TAPE = 'FORD_US'\n" +
			"AND FPT.COLLISION != 'Y'\n" +
			"AND DM.PRIMARY_MANUFACTURER_ID = 1\n" +
			"AND DM.TERMINATION_DATE IS NULL\n" +
			"AND DM.DEALERSHIP_COUNTRY = 'USA'", nativeQuery = true)
	List<AipInventoryEntity> findAllNonCollisionPartsInInventory(
			@Param("dealerId") Long dealerId,
			@Param("dataDate") LocalDate dataDate);


}
