package com.admi.data.repositories;

import com.admi.data.entities.KpiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KpiRepository extends JpaRepository<KpiEntity, Long> {

	List<KpiEntity> findAllByDealerIdAndDataDateIsBetweenOrderByDataDate(long dealerId, long start, long end);

	List<KpiEntity> findAllByDataDateIs(long dataDate);

	KpiEntity findByDealerIdAndDataDate(long dealerId, long dataDate);

	KpiEntity findFirstByDealerIdOrderByDataDateDesc(long dealerId);
}

