package com.admi.data.repositories;

import com.admi.data.dto.OpcKpiDto;
import com.admi.data.entities.FordDealerInventoryEntity;
import com.admi.data.entities.OpcTsp200DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FordDealerInventoryRepository extends JpaRepository<FordDealerInventoryEntity, Long> {
    FordDealerInventoryEntity findFirstByPaCode(String paCode);
}
