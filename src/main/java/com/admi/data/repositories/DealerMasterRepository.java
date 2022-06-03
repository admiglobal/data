package com.admi.data.repositories;

import com.admi.data.entities.DealerMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
			"and i.PA_CODE = :pa\n" +
			"and dm.PA_CODE = :pa\n" +
			"order by dm.PA_CODE", nativeQuery = true)
	DealerMasterEntity findInInventory(@Param("pa") String paCode);

	@Query(value = "select dm.* \n" +
			"from DEALER_MASTER dm \n" +
			"join GOLDD_IMPORTER g \n" +
			"	on dm.PA_CODE = g.PA_CODE\n" +
			"where g.COUNTRY_CODE = 'USA'\n" +
			"  and dm.PRIMARY_MANUFACTURER_ID = 1 \n" +
			"  and dm.TERMINATION_DATE is null \n" +
			"  and (dm.DEALERSHIP_COUNTRY = 'USA' or dm.DEALERSHIP_COUNTRY is null) \n" +
			"  and (g.QUICK_LANE = 'Y' \n" +
			"  or dm.PA_CODE in (:nonQlPrimariesPaCodes) \n" +
			"  ) \n" +
			"order by dm.PA_CODE",
			nativeQuery = true)
	List<DealerMasterEntity> findAllQuickLaneDealers(String[] nonQlPrimariesPaCodes);

	@Query(value = "select * \n" +
			"from DEALER_MASTER \n" +
			"where SALES_CODE = ( \n" +
			"    select SALES_CODE from DEALER_MASTER \n" +
			"    where PA_CODE = :paCode \n" +
			"    and termination_date is null \n" +
			"    and PRIMARY_MANUFACTURER_ID = 1 \n" +
			"    ) \n" +
			"and PA_CODE <> :paCode",
			nativeQuery = true)
	List<DealerMasterEntity> findSameSalesCodeDealers(String paCode);
}