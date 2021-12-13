package com.admi.data.repositories;

import com.admi.data.entities.FcsdProgramCreditsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FcsdProgramCreditsRepository extends JpaRepository<FcsdProgramCreditsEntity, Long> {

	List<FcsdProgramCreditsEntity> findAllByDealerIdOrderByDataDate(long dealerId);
	List<FcsdProgramCreditsEntity> findAllByDealerIdAndDataDate(long dealerId, LocalDate date);

}
