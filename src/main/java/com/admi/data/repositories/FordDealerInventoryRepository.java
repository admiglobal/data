package com.admi.data.repositories;

import com.admi.data.entities.FordDealerInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FordDealerInventoryRepository extends JpaRepository<FordDealerInventoryEntity, Long> {
    FordDealerInventoryEntity findFirstByPaCode(String paCode);
}
