package com.admi.data.repositories;

import com.admi.data.entities.PriceTapeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PriceTapeRepository extends JpaRepository<PriceTapeEntity, Long> {

    @Query( value = "SELECT FPT.*\n" +
            "FROM FORD_PT FPT\n" +
            "LEFT OUTER JOIN CPC_PARTS_LISTS CPL\n" +
            "ON FPT.PARTNO = CPL.PRIMARY_SKU\n" +
            "WHERE FPT.TAPE = 'FORD_US'\n" +
            "AND CPL.PARTS_LIST = :partsList\n" +
            "AND CPL.RANK <= :tierList", nativeQuery = true)
    List<PriceTapeEntity> findAllCpcListPartsInPriceTape(@Param("partsList") String partsList,
                                                         @Param("tierList") Short tierList);

    @Query( value = "SELECT FPT.*\n" +
            "FROM FORD_PT FPT\n" +
            "LEFT OUTER JOIN FORD_DEALER_INVENTORY FDI\n" +
            "ON FPT.PARTNO = FDI.PARTNO\n" +
            "LEFT OUTER JOIN DEALER_MASTER DM\n" +
            "ON FDI.PA_CODE = DM.PA_CODE\n" +
            "WHERE FPT.TAPE = 'FORD_US'\n" +
            "AND DM.DEALER_ID = :dealerId\n" +
//            "AND FDI.DATA_DATE = :dataDate\n" +
            "AND DM.PRIMARY_MANUFACTURER_ID = 1\n" +
            "AND DM.TERMINATION_DATE IS NULL\n" +
            "AND DM.DEALERSHIP_COUNTRY = 'USA'\n" +
            "AND FDI.QOH > 0", nativeQuery = true)
    List<PriceTapeEntity> findAllOnHandDealerPartsInPriceTape(@Param("dealerId") Long dealerId);
//                                                              @Param("dataDate") LocalDate dataDate);
}
