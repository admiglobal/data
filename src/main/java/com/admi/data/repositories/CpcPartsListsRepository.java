package com.admi.data.repositories;

import com.admi.data.entities.CpcPartsListsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CpcPartsListsRepository extends JpaRepository<CpcPartsListsEntity, Long> {

    List<CpcPartsListsEntity> findAll();

    List<CpcPartsListsEntity> findByPartsListAndRankIsLessThanEqual(String partsList, Short rank);
}
