package com.admi.data.repositories;

import com.admi.data.entities.OpcTsp200DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OpcTsp200DataRepository extends JpaRepository<OpcTsp200DataEntity, Long> {

    void deleteByPaCode(String paCode);

    /**
     * Selects OPC 200 data by P&A code, but from FORD_DEALER_INVENTORY instead of from OPC_TSP_200_DATA itself.
     * This is useful for updating OPC_TSP_200_DATA with new data from FORD_DEALER_INVENTORY,
     * since the latter updates weekly.
     */
    @Query( value = "SELECT inv.PA_CODE, opc.OC_PART_NUMBER PART_NUMBER, inv.QOH\n" +
            "FROM FORD_DEALER_INVENTORY inv\n" +
            "INNER JOIN OPC_TSP_200 opc\n" +
            "ON (opc.SERVICE_PART_NUMBER = inv.PARTNO\n" +
            "OR opc.OC_PART_NUMBER = inv.PARTNO)\n" +
            "WHERE inv.QOH <> 0\n" +
            "AND inv.PA_CODE = :paCode"
            , nativeQuery = true)
    List<OpcTsp200DataEntity> findAllByPaCodeFromFordDealerInventory(
            @Param("paCode") String paCode);

    /**
     * Finds the number of OPC200 SKU's on hand for a particular PA code
     */
    @Query( value = "SELECT SUM(CASE WHEN d.qoh is null THEN 0 ELSE 1 END) total_qoh\n" +
            "FROM OPC_TSP_200 opc\n" +
            "left join (\n" +
            "select * from OPC_TSP_200_DATA\n" +
            "where PA_CODE = :paCode\n" +
            "and QOH > 0\n" +
            ") d\n" +
            "on (d.PART_NUMBER = opc.SERVICE_PART_NUMBER\n" +
            "or d.PART_NUMBER = opc.OC_PART_NUMBER)"
            , nativeQuery = true)
    int findSkuQohByPaCode(
            @Param("paCode") String paCode);

    /**
     * Finds value of the OPC 200 parts on hand for a particular PA code
     */
    @Query( value = "SELECT SUM((opc.DEALER_NET * nvl(d.QOH, 0))) total_value\n" +
            "FROM OPC_TSP_200 opc\n" +
            "left join (\n" +
            "select * from OPC_TSP_200_DATA\n" +
            "where PA_CODE = :paCode\n" +
            "and QOH > 0\n" +
            ") d\n" +
            "on (d.PART_NUMBER = opc.SERVICE_PART_NUMBER\n" +
            "or d.PART_NUMBER = opc.OC_PART_NUMBER)"
            , nativeQuery = true)
    double findTotalOpcValueByPaCode(
            @Param("paCode") String paCode);
}
