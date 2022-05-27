package com.admi.data.repositories;

import com.admi.data.entities.TipEnrollmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipEnrollmentsRepository extends JpaRepository<TipEnrollmentsEntity, Long> {

	TipEnrollmentsEntity findByDealerId(Long dealerId);

}
