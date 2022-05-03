package com.admi.data.repositories;

import com.admi.data.entities.DealerMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealerMasterRepository extends JpaRepository<DealerMasterEntity, Long> {

	DealerMasterEntity findFirstByPaCode(String paCode);
	DealerMasterEntity findFirstByPaCodeAndPrimaryManufacturerIdAndTerminationDateIsNull(String paCode, Integer primaryManufacturerId);
	List<DealerMasterEntity> findAll();
	DealerMasterEntity findByDealerId(long dealerId);


	@Query(value = "select *\n" +
			"from DEALER_MASTER dm\n" +
			"inner join (\n" +
			"    select unique pa_code\n" +
			"    from FORD_DEALER_INVENTORY\n" +
			") i\n" +
			"    on i.PA_CODE = dm.PA_CODE\n" +
			"where PRIMARY_MANUFACTURER_ID = 1\n" +
			"and TERMINATION_DATE is null\n" +
			"and DEALERSHIP_COUNTRY = 'USA'\n" +
			"order by dm.PA_CODE", nativeQuery = true)
	List<DealerMasterEntity> findAllInInventory();

}