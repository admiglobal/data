package com.admi.data.repositories;

import com.admi.data.entities.CpcPartsOnHandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CpcPartsOnHandRepository extends JpaRepository<CpcPartsOnHandEntity, Long> {

}
