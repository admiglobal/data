package com.admi.data.repositories;

import com.admi.data.entities.DealerMasterEntity;
import com.admi.data.entities.ZigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DealerMasterRepository extends JpaRepository<DealerMasterEntity, Long> {

	DealerMasterEntity findFirstByPaCode(String paCode);
	DealerMasterEntity findFirstByPaCodeAndPrimaryManufacturerIdAndTerminationDateIsNull(String paCode, Integer primaryManufacturerId);
	List<DealerMasterEntity> findAll();
	DealerMasterEntity findByDealerId(long dealerId);

	@Query(value = "select dm.* \n" +
			"from DEALER_MASTER dm \n" +
			"join GOLDD_IMPORTER g \n" +
			"	on dm.PA_CODE = g.PA_CODE\n" +
			"where g.QUICK_LANE = 'Y'\n" +
			"  and g.COUNTRY_CODE = 'USA' \n" +
			"  and dm.PRIMARY_MANUFACTURER_ID = 1 \n" +
			"  and dm.TERMINATION_DATE is null \n" +
			"  and dm.DEALERSHIP_COUNTRY = 'USA' \n" +
			"order by dm.PA_CODE",
			nativeQuery = true)
	List<DealerMasterEntity> findAllQuickLaneDealers();
}