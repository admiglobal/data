package com.admi.data.repositories;

import com.admi.data.entities.TipKpiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TipKpiRepository extends JpaRepository<TipKpiEntity, Long> {

    TipKpiEntity findByDealerIdAndDataDate(Long dealerId, LocalDateTime dateTime);

}
