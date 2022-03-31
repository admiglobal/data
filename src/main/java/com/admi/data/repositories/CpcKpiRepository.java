package com.admi.data.repositories;

import com.admi.data.entities.CpcKpiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CpcKpiRepository extends JpaRepository<CpcKpiEntity, Long> {

    List<CpcKpiEntity> findAllByDealerIdAndDataDate(Long dealerId, LocalDate dataDate);
}
