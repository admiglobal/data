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

	@Query(value = " select *\n" +
					"from FORD_DEALER_INVENTORY i\n" +
					"inner join DEALER_MASTER d\n" +
					"    on d.PA_CODE = i.PA_CODE\n" +
					"where d.DEALER_ID = :dealerId\n" +
					"  and i.DATA_DATE = (\n" +
					"    select max(i2.DATA_DATE)\n" +
					"    from FORD_DEALER_INVENTORY i2\n" +
					"    where i2.pa_code = d.PA_CODE\n" +
					")\n" +
					"  and DEALERSHIP_COUNTRY = 'USA'\n" +
					"  and TERMINATION_DATE is null\n" +
					"  and PRIMARY_MANUFACTURER_ID = 1\n" +
					"  and ROWNUM = 1", nativeQuery = true)
	AipInventoryEntity findFirstByDealerIdAndMaxDataDate(@Param("dealerId") Long dealerId);

	@Query(value = " select *\n" +
					"from FORD_DEALER_INVENTORY i\n" +
					"where i.DATA_DATE = (\n" +
					"    select max(i2.DATA_DATE)\n" +
					"    from FORD_DEALER_INVENTORY i2\n" +
					")\n" +
					"and ROWNUM = 1", nativeQuery = true)
	AipInventoryEntity findFirstByMaxDataDate();

	@Query(value =  "select i.DEALER_ID, i.PARTNO, pt.PC_VALUE * 100 CENTS, i.QOH, pt.DESCRIPTION, i.STATUS, i.ADMI_STATUS, i.LAST_SALE, i.LAST_RECEIPT, i.BIN, i.SOURCE, h.STATUS MFG_CONTROLLED, i.DATA_DATE, i.MANUFACTURER, i.QOO, i.TWELVE_MONTH_SALES, i.ENTRY_DATE, 0 YTD_MONTHS_WITH_SALES " +
					"from FORD_DEALER_INVENTORY i \n" +
					"LEFT OUTER JOIN FORD_DEALER_RIM_HISTORY h \n" +
					"    on h.PA_CODE = i.PA_CODE \n" +
					"    and h.PART_NUMBER = i.PARTNO \n" +
					"INNER JOIN FORD_PT pt \n" +
					"    on pt.PARTNO = i.PARTNO \n" +
					"where i.PA_CODE = :pa", nativeQuery = true)
	List<AipInventoryEntity> findFordInventoryByPaCode(@Param("pa") String pa_code);

	default LocalDate getMaxDateByDealerId(Long dealerId) {
		AipInventoryEntity entity = findFirstByDealerIdAndMaxDataDate(dealerId);

		if (entity == null)
			return LocalDate.now();
		else
			return entity.getDataDate();
	}

	default LocalDate getMaxDate() {
		AipInventoryEntity entity = findFirstByMaxDataDate();
		return entity.getDataDate();
	}

}
