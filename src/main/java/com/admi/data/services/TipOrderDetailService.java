package com.admi.data.services;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.TipKpiEntity;
import com.admi.data.entities.TipOrderDetailEntity;
import com.admi.data.enums.DmsProvider;
import com.admi.data.repositories.AipInventoryRepository;
import com.admi.data.repositories.TipKpiRepository;
import com.admi.data.repositories.TipOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.BatchUpdateException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TipOrderDetailService {

    @Autowired
    AipInventoryRepository inventoryRepo;

    @Autowired
    TipKpiRepository tipKpiRepo;

    @Autowired
    TipOrderDetailRepository tipOrderRepo;

    public void runSingleTipDealer() {
        List<AipInventoryEntity> inventory = fetchInventory(55780L);
        calculateTipKpi(inventory, DmsProvider.CDK);
    }

    public List<AipInventoryEntity> fetchInventory(Long dealerId) {
        LocalDate dataDate = inventoryRepo.getMaxDateByDealerId(dealerId);
        return inventoryRepo.findAllByDealerIdAndDataDate(dealerId, dataDate);
    }

    public void calculateTipKpi(List<AipInventoryEntity> inventory, DmsProvider dms) {

        int lines = 0;
        int orderTotal = 0;
        int stockParts = 0;
        int onHandStockParts = 0;

        AipInventoryEntity entity = inventory.get(0);
        LocalDateTime creationTime = LocalDateTime.now();

        for (AipInventoryEntity part : inventory) {

            int currentMonth = part.getDataDate().getMonthValue();
            int lastSaleMonth = part.getLastSale().getMonthValue();
            int currentYear = part.getDataDate().getYear();
            int lastSaleYear = part.getLastSale().getYear();
            int monthsNoSale = (currentMonth - lastSaleMonth) + ((currentYear - lastSaleYear) * 12);

            if (part.getQoh() == 0 || monthsNoSale <= 6) {
                lines++;
                orderTotal += part.getCents();

                if (Objects.equals(dms.getStatusType().getStockStatus().toString(), part.getStatus())) {
                    stockParts++;

                    if (part.getQoh() > 0)
                        onHandStockParts++;
                }

                TipOrderDetailEntity order = new TipOrderDetailEntity(
                        part.getDealerId(),
                        creationTime,
                        part.getPartNo(),
                        part.getDescription(),
                        part.getCents(),
                        part.getSource(),
                        part.getTwelveMonthSales(),
                        part.getYtdMonthsWithSales(),
                        (byte) monthsNoSale);

                System.out.println(order.toString());

                try {
                    tipOrderRepo.save(order);
                }
                catch (Exception e) {
                    System.out.println("Doesn't pass, pal.");
                }
            }
        }

        TipKpiEntity kpi = new TipKpiEntity(
                entity.getDealerId(),
                creationTime,
                lines,
                (long) orderTotal,
                stockParts,
                onHandStockParts);


        tipKpiRepo.save(kpi);
    }
}
