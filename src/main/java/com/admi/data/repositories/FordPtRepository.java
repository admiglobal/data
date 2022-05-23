package com.admi.data.repositories;

import com.admi.data.entities.FordPtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FordPtRepository extends JpaRepository<FordPtEntity, Long> {
    FordPtEntity findFirstByPartnoAndTape(String partNo, String tape);
    FordPtEntity findFirstByMotorcraftAndTape(String motorcraftPartNo, String tape);
}
