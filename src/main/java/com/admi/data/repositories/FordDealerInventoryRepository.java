package com.admi.data.repositories;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.FordDealerInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FordDealerInventoryRepository extends JpaRepository<FordDealerInventoryEntity, Long> {
    FordDealerInventoryEntity findFirstByPaCode(String paCode);
    FordDealerInventoryEntity findFirstByPaCodeAndPartno(String paCode, String partno);
    List<FordDealerInventoryEntity> findAllByPaCode(String paCode);

    @Query(value =  "select \n" +
                    "    i.PA_CODE, \n" +
                    "    i.PARTNO, \n" +
                    "    pt.PC_VALUE * 100 CENTS, \n" +
                    "    i.QOH, \n" +
                    "    i.STATUS, \n" +
                    "    i.LAST_SALE, \n" +
                    "    i.LAST_RECEIPT, \n" +
                    "    h.STATUS MFG_CONTROLLED, \n" +
                    "    i.DATA_DATE, \n" +
                    "    i.ADMI_STATUS, \n" +
                    "    i.QOO \n" +
                    "from \n" +
                    "    FORD_DEALER_INVENTORY i \n" +
                    "LEFT OUTER JOIN FORD_DEALER_RIM_HISTORY h \n" +
                    "    on h.PA_CODE = i.PA_CODE \n" +
                    "    and h.PART_NUMBER = i.PARTNO \n" +
                    "INNER JOIN FORD_PT pt \n" +
                    "    on pt.PARTNO = i.PARTNO \n" +
                    "where i.PA_CODE = :pa",
            nativeQuery = true)
    List<FordDealerInventoryEntity> findFordInventoryByPaCode(@Param("pa") String pa_code);

    @Query( value = "select \n" +
            "    i.PA_CODE, \n" +
            "    i.PARTNO, \n" +
            "    pt.PC_VALUE * 100 CENTS, \n" +
            "    i.QOH, \n" +
            "    i.STATUS, \n" +
            "    i.LAST_SALE, \n" +
            "    i.LAST_RECEIPT, \n" +
            "    '' MFG_CONTROLLED, \n" +
            "    i.DATA_DATE, \n" +
            "    i.ADMI_STATUS, \n" +
            "    i.QOO \n" +
            "from \n" +
            "    FORD_DEALER_INVENTORY i \n" +
            "INNER JOIN FORD_PT PT\n" +
            "ON I.PARTNO = PT.PARTNO\n" +
            "LEFT OUTER JOIN DEALER_MASTER DM\n" +
            "ON I.PA_CODE = DM.PA_CODE\n" +
            "WHERE I.QOH > 0\n" +
            "AND DM.DEALER_ID = :dealerId\n" +
            "AND PT.TAPE = 'FORD_US'\n" +
            "AND PT.COLLISION = 'Y'\n" +
            "AND DM.PRIMARY_MANUFACTURER_ID = 1\n" +
            "AND DM.TERMINATION_DATE IS NULL\n" +
            "AND DM.DEALERSHIP_COUNTRY = 'USA'", nativeQuery = true)
    List<FordDealerInventoryEntity> findAllCollisionPartsInInventory(
            @Param("dealerId") Long dealerId);

    @Query( value = "select \n" +
            "    i.PA_CODE, \n" +
            "    i.PARTNO, \n" +
            "    pt.PC_VALUE * 100 CENTS, \n" +
            "    i.QOH, \n" +
            "    i.STATUS, \n" +
            "    i.LAST_SALE, \n" +
            "    i.LAST_RECEIPT, \n" +
            "    '' MFG_CONTROLLED, \n" +
            "    i.DATA_DATE, \n" +
            "    i.ADMI_STATUS, \n" +
            "    i.QOO \n" +
            "from \n" +
            "    FORD_DEALER_INVENTORY i \n" +
            "INNER JOIN FORD_PT PT\n" +
            "ON I.PARTNO = PT.PARTNO\n" +
            "LEFT OUTER JOIN DEALER_MASTER DM\n" +
            "ON I.PA_CODE = DM.PA_CODE\n" +
            "WHERE I.QOH > 0\n" +
            "AND DM.DEALER_ID = :dealerId\n" +
            "AND PT.TAPE = 'FORD_US'\n" +
            "AND PT.COLLISION != 'Y'\n" +
            "AND DM.PRIMARY_MANUFACTURER_ID = 1\n" +
            "AND DM.TERMINATION_DATE IS NULL\n" +
            "AND DM.DEALERSHIP_COUNTRY = 'USA'", nativeQuery = true)
    List<FordDealerInventoryEntity> findAllNonCollisionPartsInInventory(
            @Param("dealerId") Long dealerId);

}
