package com.admi.data.repositories;

import com.admi.data.entities.ZigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


public interface ZigRepository extends JpaRepository<ZigEntity, Long> {

	@Async
	@Transactional
	void deleteAllByPaCode(String paCode);

	@Async
	@Modifying
	@Transactional
	@Query(value = "insert into AIS_ZIG_NEW(pa_code, partno, qoh, des, ls_date, lr_date, data_date, status, rflag, cost, bin, src, dms_sts, mfg_controlled)\n" +
			"SELECT\n" +
			"       ?1,\n" +
			"       PARTNO,\n" +
			"       QOH,\n" +
			"       DESCRIPTION,\n" +
			"       to_char(LAST_SALE, 'YYYY-MM-DD'),\n" +
			"       to_char(LAST_RECEIPT, 'YYYY-MM-DD'),\n" +
			"       trunc(sysdate),\n" +
			"       ADMI_STATUS,\n" +
			"       (CASE WHEN cents >= 1500 THEN 'Y' ELSE 'N' END) as rFlag,\n" +
			"       round(cents/100, 2),\n" +
			"       bin,\n" +
			"       source,\n" +
			"       STATUS,\n" +
			"       MFG_CONTROLLED\n" +
			"from aip_inventory\n" +
			"where DEALER_ID = ?2\n" +
			"  and nvl(QOH, 0) > 0\n" +
			"  and CENTS > 0" +
			"  and DATA_DATE = ?3", nativeQuery = true)
	void copyZigParts(String paCode, Long dealerId, LocalDate date);
}
