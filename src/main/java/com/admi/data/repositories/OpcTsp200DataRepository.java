package com.admi.data.repositories;

import com.admi.data.entities.OpcTsp200DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OpcTsp200DataRepository extends JpaRepository<OpcTsp200DataEntity, Long> {
    List<OpcTsp200DataEntity> findAllByPaCode(String paCode);

    @Transactional
    void deleteAllByPaCode(String paCode);

    /**
     * Selects OPC 200 data by P&A code, but from FORD_DEALER_INVENTORY instead of from OPC_TSP_200_DATA itself.
     * This is useful for updating OPC_TSP_200_DATA with new data from FORD_DEALER_INVENTORY,
     * since the latter updates weekly.
     */
    @Query( value = "SELECT inv.PA_CODE, inv.PARTNO PART_NUMBER, inv.QOH\n" +
            "FROM FORD_DEALER_INVENTORY inv\n" +
            "INNER JOIN OPC_TSP_200 opc\n" +
            "ON (opc.SERVICE_PART_NUMBER = inv.PARTNO)\n" +
            "WHERE inv.QOH <> 0\n" +
            "AND inv.PA_CODE = :paCode"
            , nativeQuery = true)
    List<OpcTsp200DataEntity> findAllByPaCodeFromFordDealerInventory(
            @Param("paCode") String paCode);

    /**
     * Finds the number of OPC200 SKU's on hand for a particular PA code
     */
    @Query( value = "SELECT COUNT(*)\n" +
            "FROM FORD_DEALER_INVENTORY inv\n" +
            "INNER JOIN OPC_TSP_200 opc\n" +
            "ON (opc.SERVICE_PART_NUMBER = inv.PARTNO)\n" +
            "WHERE inv.QOH <> 0\n" +
            "AND inv.PA_CODE = :paCode"
            , nativeQuery = true)
    int findSkuQohByPaCode(
            @Param("paCode") String paCode);

    /**
     * Finds value of the OPC 200 parts on hand for a particular PA code.
     * FORD_PT is our source of pricing information (NOT opc_tsp_200)
     */
    @Query( value = "SELECT SUM(nvl((pt.PC_VALUE * inv.QOH), 0) ) total_value\n" +
            "FROM FORD_DEALER_INVENTORY inv\n" +
            "INNER JOIN OPC_TSP_200 opc\n" +
            "ON (opc.SERVICE_PART_NUMBER = inv.PARTNO)\n" +
            "inner join (\n" +
            "select * from ford_pt\n" +
            " where tape = 'FORD_US'\n" +
            ") pt\n" +
            "on (opc.SERVICE_PART_NUMBER = pt.PARTNO)\n" +
            "WHERE inv.QOH <> 0\n" +
            "AND inv.PA_CODE = :paCode"
            , nativeQuery = true)
    double findTotalOpcValueByPaCode(
            @Param("paCode") String paCode);
}
