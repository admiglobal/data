package com.admi.data.repositories;

import com.admi.data.entities.ZigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface ZigRepository extends JpaRepository<ZigEntity, Long> {

	List<ZigEntity> findAllByPaCodeAndDataDateOrderByDmsStatus(String paCode, LocalDateTime dataDate);
	ZigEntity findFirstByPaCode(String paCode);

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

	@Query(value = "select zig.* \n" +
			"from AIS_ZIG_NEW zig \n" +
			"where zig.PA_CODE = ? \n" +
			"  and DATA_DATE = ? \n" +
			"  and MFG_CONTROLLED = 1",
			nativeQuery = true)
	List<ZigEntity> findAllRimActivePartsByPaCode(String paCode, LocalDateTime dataDate);

	@Query(value = "select zig.* \n" +
			"from AIS_ZIG_NEW zig, AIS_ENROLLMENTS_V ais, AIP_RIM_HISTORY rim \n" +
			"where zig.PA_CODE = ais.pa_code \n" +
			"  and ais.dealer_id = rim.DEALER_ID \n" +
			"  and zig.PARTNO = rim.PARTNO \n" +
			"  and zig.PA_CODE = ? \n" +
			"  and DATA_DATE = ? \n" +
			"  and PHASE_OUT is not null \n" +
			"  and MFG_CONTROLLED = 0 \n" +
			"  and to_date(nvl(Lr_DATE, nvl(LS_DATE, sysdate)), 'YYYY-MM-DD') >= PHASE_IN \n" +
			"  and to_date(nvl(Lr_DATE, nvl(LS_DATE, sysdate)), 'YYYY-MM-DD') <= PHASE_OUT \n" +
			"  and to_date(nvl(Lr_DATE, nvl(LS_DATE, sysdate)), 'YYYY-MM-DD') > add_months(sysdate, -13)",
			nativeQuery = true)
	List<ZigEntity> findAllRimInactivePartsByPaCode(String paCode, LocalDateTime dataDate);

	@Query(value =  "-- Stock Parts\n" +
			"select zig.*\n" +
			"from AIS_ZIG_NEW zig\n" +
			"where zig.PA_CODE = ?1\n" +
			"  and DATA_DATE = ?2\n" +
			"  and DMS_STS = ?3\n" +
			"\n" +
			"UNION\n" +
			"\n" +
			"-- Parts < 2 months\n" +
			"select zig.*\n" +
			"from AIS_ZIG_NEW zig\n" +
			"where zig.PA_CODE = ?1\n" +
			"  and DATA_DATE = ?2\n" +
			"  and to_date(nvl(LR_DATE, '1970-01-01'), 'YYYY-MM-DD') > add_months(sysdate, -2)\n" +
			"\n" +
			"UNION\n" +
			"\n" +
			"-- Current RIM Parts\n" +
			"select zig.* \n" +
			"from AIS_ZIG_NEW zig \n" +
			"where zig.PA_CODE = ?1 \n" +
			"  and DATA_DATE = ?2 \n" +
			"  and MFG_CONTROLLED = 1\n" +
			"\n" +
			"UNION\n" +
			"\n" +
			"-- Rim Inactive < 13 months old\n" +
			"select zig.* \n" +
			"from AIS_ZIG_NEW zig, AIS_ENROLLMENTS_V ais, AIP_RIM_HISTORY rim \n" +
			"where zig.PA_CODE = ais.pa_code \n" +
			"  and ais.dealer_id = rim.DEALER_ID \n" +
			"  and zig.PARTNO = rim.PARTNO \n" +
			"  and zig.PA_CODE = ?1 \n" +
			"  and DATA_DATE = ?2 \n" +
			"  and MFG_CONTROLLED = 0 \n" +
			"  and PHASE_OUT is not null \n" +
			"  and to_date(nvl(Lr_DATE, nvl(LS_DATE, sysdate)), 'YYYY-MM-DD') >= PHASE_IN \n" +
			"  and to_date(nvl(Lr_DATE, nvl(LS_DATE, sysdate)), 'YYYY-MM-DD') <= nvl(PHASE_OUT, sysdate) \n" +
			"  and to_date(nvl(Lr_DATE, nvl(LS_DATE, sysdate)), 'YYYY-MM-DD') > add_months(sysdate, -13)",
			nativeQuery = true)
	List<ZigEntity> findAllSupportedParts(String paCode, LocalDateTime dataDate, String stockStatus);

	@Query(value = "select zig.*\n" +
			"from AIS_ZIG_NEW zig\n" +
			"where zig.PA_CODE = ?1\n" +
			"  and DATA_DATE = ?2\n" +
			"  and QOH >= 0",
			nativeQuery = true)
	List<ZigEntity> findAllNonnegativeQohByPaCodeAndDataDateOrderByDmsStatus(String paCode, LocalDateTime dataDate);
}
