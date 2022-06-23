package com.admi.data.services;

import com.admi.data.entities.*;
import com.admi.data.enums.DmsProvider;
import com.admi.data.enums.KpiTitle;
import com.admi.data.enums.statuses.*;
import com.admi.data.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatusService {

    @Autowired
    ZigRepository zigRepo;

    @Autowired
    KpiRepository kpiRepo;

    @Autowired
    StatusTotalsRepository statusTotalsRepo;

    @Autowired
    DealerMasterRepository dealerMasterRepo;

    public void runStatusValuesForToday(Long dealerId, DmsProvider dmsProvider) {
        DealerMasterEntity dealer = dealerMasterRepo.findByDealerId(dealerId);

        ZigEntity zigEntityForDate = zigRepo.findFirstByPaCode(dealer.getPaCode());

        LocalDateTime todayDate;

        if (zigEntityForDate == null) {
            todayDate = LocalDateTime.of(2000,1,1,0,0);
        } else {
            todayDate = zigEntityForDate.getDataDate();
        }

        Long dataDate = new DateService().getLongDate(todayDate.toLocalDate().minusDays(1));

        List<ZigEntity> nonnegativeQohParts = zigRepo.findAllNonnegativeQohByPaCodeAndDataDateOrderByDmsStatus(dealer.getPaCode(), todayDate);
        List<ZigEntity> rimActiveParts = zigRepo.findAllRimActivePartsByPaCode(dealer.getPaCode(), todayDate);
        List<ZigEntity> rimInactiveParts = zigRepo.findAllRimInactivePartsByPaCode(dealer.getPaCode(), todayDate);
        List<ZigEntity> supportedParts = zigRepo.findAllSupportedParts(
                dealer.getPaCode(),
                todayDate,
                dmsProvider.getStatusType().getStockStatus().toString()
        );

        KpiEntity kpiEntity = kpiRepo.findByDealerIdAndDataDate(dealer.getDealerId(), dataDate);
        if (kpiEntity == null) {
            kpiEntity = new KpiEntity();
        }

        BigDecimal rimActiveTotal = totalAllParts(rimActiveParts);
        BigDecimal rimInactiveTotal = totalAllParts(rimInactiveParts);
        BigDecimal rimAllTotal = rimActiveTotal.add(rimInactiveTotal);
        BigDecimal allPartsTotal = BigDecimal.valueOf(kpiEntity.getTotalSValue() + kpiEntity.getTotalNsValue());
        BigDecimal supportedTotal = totalAllParts(supportedParts);

        Map<Integer, Map<String, BigDecimal>> maps = new HashMap<>();

        Map<String, BigDecimal> supportedTotals = new HashMap<>();
        supportedTotals.put(KpiTitle.SUPPORTED.toString(), supportedTotal);
        supportedTotals.put(KpiTitle.UNSUPPORTED.toString(), allPartsTotal.subtract(supportedTotal));

        Map<String, BigDecimal> rimTotals = new HashMap<>();
        rimTotals.put(KpiTitle.RIM_ACTIVE.toString(), rimActiveTotal);
        rimTotals.put(KpiTitle.RIM_INACTIVE.toString(), rimInactiveTotal);
        rimTotals.put(KpiTitle.NON_RIM.toString(), allPartsTotal.subtract(rimAllTotal));

        maps.put(0, supportedTotals);
        maps.put(1, totalPartsByStatus(nonnegativeQohParts, dmsProvider));
        maps.put(2, rimTotals);

        maps.forEach((k,v) -> saveStatusFromMap(v, dealer, k, dataDate));
    }

    private void saveStatusFromMap(Map<String, BigDecimal> map,
                                   DealerMasterEntity dealer,
                                   Integer graphNumber,
                                   Long dataDate) {
        map.forEach((k,v) -> statusTotalsRepo.save(
                new StatusTotalsEntity(
                        dealer.getDealerId(),
                        dataDate,
                        k,
                        v.floatValue(),
                        graphNumber,
                        dealer.getDmsId()
                )
        ));
        statusTotalsRepo.flush();
    }

    public BigDecimal totalAllParts(List<ZigEntity> inventory) {
        BigDecimal total = BigDecimal.ZERO;

        for(ZigEntity part : inventory) {
            total = total.add(part.getCost().multiply(BigDecimal.valueOf(part.getQoh())));
        }

        return total;
    }

    /**
     * Returns a map that maps DMS status name to total value of that status in inventory;
     * that is, the sum of all cost*QOH for all parts of that status.
     * Note: DOES include parts with a negative on-hand value. This will skew results if not omitted from the inventory argument.
     * @param inventory All parts in this dealer's inventory
     * @param dmsProvider The DmsProvider for this dealer
     * @return A HashMap of: DMS status name -> status total
     */
    public HashMap<String, BigDecimal> totalPartsByStatus(List<ZigEntity> inventory, DmsProvider dmsProvider) {
        HashMap<String, BigDecimal> totals = new HashMap<>();

        BigDecimal total;

        for (ZigEntity part : inventory) {

            String statusName;

            try {
                DmsStatus status = DmsStatus.findStatus(part.getDmsStatus(), dmsProvider);
                statusName = status.toString();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                statusName = "No Status";
            }

            if (totals.get(statusName) != null) {
                total = totals.get(statusName);
            } else {
                total = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
            }

            BigDecimal partTotal = part.getCost().multiply(BigDecimal.valueOf(part.getQoh()));

            totals.put(statusName, total.add(partTotal));
        }
        return totals;
    }
}
