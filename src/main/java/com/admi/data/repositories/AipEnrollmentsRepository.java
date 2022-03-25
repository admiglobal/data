package com.admi.data.repositories;

import com.admi.data.entities.AipEnrollmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AipEnrollmentsRepository extends JpaRepository<AipEnrollmentsEntity, Long> {

    List<AipEnrollmentsEntity> findAllByOrderByPaCode();
    AipEnrollmentsEntity findFirstByPaCode(String paCode);

}
