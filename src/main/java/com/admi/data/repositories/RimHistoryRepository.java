package com.admi.data.repositories;

import com.admi.data.entities.RimHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public interface RimHistoryRepository extends JpaRepository<RimHistoryEntity, Long> {

	List<RimHistoryEntity> findAllByDealerIdAndPhaseOutIsNull(Long dealerId);
	List<RimHistoryEntity> findAllByDealerIdAndPhaseOutIsNotNull(Long dealerId);
	List<RimHistoryEntity> findAllByDealerIdAndPartnoInAndPhaseOutIsNull(Long dealerId, List<String> parts);

	default Map<String, RimHistoryEntity> findMapByDealerIdAndPhaseOutIsNull(Long dealerId) {
		return findAllByDealerIdAndPhaseOutIsNull(dealerId)
				.stream()
				.collect(Collectors.toMap(RimHistoryEntity::getPartno, v -> v));
	}

	default Map<String, RimHistoryEntity> findMapByDealerIdAndPhaseOutIsNotNull(Long dealerId) {
		return findAllByDealerIdAndPhaseOutIsNotNull(dealerId)
				.stream()
				.collect(Collectors.toMap(RimHistoryEntity::getPartno, v -> v));
	}

	@Transactional
	@Modifying
	@Query(value =  "insert into AIP_RIM_HISTORY(dealer_id, partno, phase_in)\n" +
					"select :d_id, :part, sysdate\n" +
					"from dual\n" +
					"where not exists(\n" +
					"    select 1\n" +
					"    from AIP_RIM_HISTORY\n" +
					"    where DEALER_ID = :d_id\n" +
					"      and partno = :part\n" +
					"      and PHASE_IN is not null\n" +
					"      and PHASE_OUT is null\n" +
					")",
			nativeQuery = true)
	void insertNewRimPart(@Param("d_id") Long dealerId,
	                      @Param("part") String partNumber);

	@Transactional
	@Modifying
	@Query(value =  "update AIP_RIM_HISTORY\n" +
					"set PHASE_OUT = sysdate\n" +
					"where DEALER_ID = :d_id\n" +
					"  and PARTNO = :part\n" +
					"  and phase_in is not null\n" +
					"  and PHASE_OUT is null",
			nativeQuery = true)
	void updateOldRimPart(@Param("d_id") Long dealerId,
	                      @Param("part") String partNumber);


}
