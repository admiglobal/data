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

import java.time.LocalDate;
import java.util.ArrayList;
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
        Long dealerId = 55780L;
        List<AipInventoryEntity> inventory = fetchInventory(dealerId);
        calculateTipKpi(dealerId, inventory, DmsProvider.CDK);
    }

    public List<AipInventoryEntity> fetchInventory(Long dealerId) {
//        LocalDate dataDate = inventoryRepo.getMaxDateByDealerId(dealerId);
        LocalDate dataDate = LocalDate.of(2022,5,23);
        return inventoryRepo.findAllByDealerIdAndDataDate(dealerId, dataDate);
    }

    public void calculateTipKpi(Long dealerId, List<AipInventoryEntity> inventory, DmsProvider dms) {
        List<TipOrderDetailEntity> orderList = new ArrayList<>();

        int lines = 0;
        int orderTotal = 0;
        int stockParts = 0;
        int onHandStockParts = 0;

        LocalDate creationTime = LocalDate.now();

        for (AipInventoryEntity part : inventory) {

            int currentMonth = part.getDataDate().getMonthValue();
            int lastSaleMonth = part.getLastSale().getMonthValue();
            int currentYear = part.getDataDate().getYear();
            int lastSaleYear = part.getLastSale().getYear();
            int monthsNoSale = (currentMonth - lastSaleMonth) + ((currentYear - lastSaleYear) * 12);

            if (part.getQoh() == 0 && monthsNoSale <= 6 && part.getYtdMonthsWithSales() >= 2) {
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
                        monthsNoSale);

                orderList.add(order);
            }
        }

        try {
            tipOrderRepo.saveAll(orderList);
        } catch (Exception e) {
            e.printStackTrace();
            for (TipOrderDetailEntity order : orderList) {
                try {
                    tipOrderRepo.save(order);
                }
                catch (Exception f) {
                    System.out.println("Doesn't pass, pal.");
                    f.printStackTrace();
                    System.out.println(order.toString());
                }
            }
        }

        TipKpiEntity kpi = new TipKpiEntity(
                dealerId,
                creationTime,
                lines,
                (long) orderTotal,
                stockParts,
                onHandStockParts);

        tipKpiRepo.save(kpi);
    }
}
