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

	@Query(value = "select dm.* \n" +
			"from QUICK_LANE_DEALERS ql \n" +
			"inner join ( \n" +
			"	select * \n" +
			"	from DEALER_MASTER \n" +
			"	where TERMINATION_DATE is null \n" +
			"	and PRIMARY_MANUFACTURER_ID = 1 \n" +
			"	and (dealership_country = 'USA' or DEALERSHIP_COUNTRY is null) \n" +
			") dm \n" +
			"on ql.PA_CODE = dm.pa_code",
			nativeQuery = true)
	List<DealerMasterEntity> findAllQuickLaneDealers();

	/**
	 * Ordered to put the primary dealer first, if applicable, since it is most likely to find data under the primary PA code
	 * @return A list of all dealers that share this dealer's sales code. This list does not include the argument dealer.
	 */
	@Query(value = "select * \n" +
			"from DEALER_MASTER \n" +
			"where SALES_CODE = ( \n" +
			"    select SALES_CODE from DEALER_MASTER \n" +
			"    where PA_CODE = :paCode \n" +
			"    ) \n" +
			"and PA_CODE <> :paCode \n" +
			"order by PRIMARY_OR_SECONDARY",
			nativeQuery = true)
	List<DealerMasterEntity> findSameSalesCodeDealers(String paCode);
}