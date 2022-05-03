package com.admi.data.repositories;

import com.admi.data.entities.CpcObjectivesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;


public interface CpcObjectivesRepository extends JpaRepository<CpcObjectivesEntity, Long> {

    CpcObjectivesEntity findByObjectiveMonth(LocalDate objectiveMonth);
}
