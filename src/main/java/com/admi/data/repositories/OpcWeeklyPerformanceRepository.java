package com.admi.data.repositories;

import com.admi.data.dto.OpcKpiDto;
import com.admi.data.entities.OpcWeeklyPerformanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OpcWeeklyPerformanceRepository extends JpaRepository<OpcWeeklyPerformanceEntity, Long> {
    OpcWeeklyPerformanceEntity findFirstByPaCodeOrderBySnapshotDateDesc(String paCode);

    /**
     * Finds the total sku and value for the different brands in FORD_DEALER_INVENTORY for a certain pa code.
     */
    @Query( value = "SELECT pt.brand,\n" +
            "nvl(sum(pt.PC_VALUE * inv.QOH), 0) value,\n" +
            "count(inv.PARTNO) sku\n" +
            "from FORD_DEALER_INVENTORY inv\n" +
            "join FORD_PT pt on (inv.PARTNO = pt.PARTNO or inv.PARTNO = pt.MOTORCRAFT)\n" +
            "where inv.PA_CODE = :paCode\n" +
            "and inv.QOH > 0\n" + //don't count negative QOH
            "and pt.TAPE = 'FORD_US'\n" +
            "group by pt.BRAND"
            , nativeQuery = true)
    List<OpcKpiDto> findBrandAndValueAndSkuByPaCode(
            @Param("paCode") String paCode);
}
