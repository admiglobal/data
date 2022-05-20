package com.admi.data.services;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.TipOrderDetailEntity;
import com.admi.data.repositories.AipInventoryRepository;
import com.admi.data.repositories.TipOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TipOrderDetailService {

    @Autowired
    AipInventoryRepository inventoryRepo;

    @Autowired
    TipOrderDetailRepository tipOrderRepo;

    public void fillOrder(Long dealerId) {
        List<AipInventoryEntity> inventory = inventoryRepo.findAllAtMaxDataDateByDealerId(dealerId);

        LocalDateTime creationTime = LocalDateTime.now();

        for (AipInventoryEntity part : inventory) {

            int currentMonth = part.getDataDate().getMonthValue();
            int lastSaleMonth = part.getLastSale().getMonthValue();
            int currentYear = part.getDataDate().getYear();
            int lastSaleYear = part.getLastSale().getYear();
            int monthsNoSale = (currentMonth - lastSaleMonth) + ((currentYear - lastSaleYear) * 12);

            if (part.getQoh() == 0) {
                TipOrderDetailEntity newOrder = new TipOrderDetailEntity(
                        dealerId,
                        creationTime,
                        part.getPartNo(),
                        part.getDescription(),
                        part.getCents(),
                        part.getSource(),
                        part.getTwelveMonthSales(),
                        (byte) 0,
                        (byte) monthsNoSale);

                tipOrderRepo.save(newOrder);
            }
            else if (monthsNoSale <= 6) {
                TipOrderDetailEntity newOrder = new TipOrderDetailEntity(
                        dealerId,
                        creationTime,
                        part.getPartNo(),
                        part.getDescription(),
                        part.getCents(),
                        part.getSource(),
                        part.getTwelveMonthSales(),
                        (byte) 0,
                        (byte) monthsNoSale);

                tipOrderRepo.save(newOrder);
            }
        }
    }
}
