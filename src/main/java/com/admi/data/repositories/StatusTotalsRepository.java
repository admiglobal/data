package com.admi.data.repositories;

import com.admi.data.entities.StatusTotalsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StatusTotalsRepository extends JpaRepository<StatusTotalsEntity, Long> {

    List<StatusTotalsEntity> findAllByDealerIdAndGraphNumberAndDataDate(Long dealerId, Integer graphNumber, Long dataDate);
    List<StatusTotalsEntity> findAllByDealerIdAndGraphNumberAndDataDateAndUpdatedDate(Long dealerId, Integer graphNumber, Long dataDate, LocalDate updatedDate);
    List<StatusTotalsEntity> findAllByDealerIdAndGraphNumberAndDataDateBetweenOrderByStatusAscDataDateAsc(Long dealerId, Integer graphNumber, Long startDate, Long endDate);

}
