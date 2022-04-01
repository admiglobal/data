package com.admi.data.repositories;

import com.admi.data.entities.CpcDealerProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CpcDealerProfileRepository extends JpaRepository<CpcDealerProfileEntity, Long> {

    List<CpcDealerProfileEntity> findAll();

    CpcDealerProfileEntity findByDealerId(Long dealerId);
}
