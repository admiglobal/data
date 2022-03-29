package com.admi.data.services;

import com.admi.data.entities.*;
import com.admi.data.enums.statuses.*;
import com.admi.data.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public void runStatusValuesForToday(Long dealerId) {
        DealerMasterEntity dealer = dealerMasterRepo.findByDealerId(dealerId);

        DateTimeFormatter dataDateFormatter = DateTimeFormatter.ofPattern("yyyyMM");
        Long dataDate = Long.parseLong(dataDateFormatter.format(LocalDate.now()));

        ZigEntity zigEntityForDate = zigRepo.findFirstByPaCode(dealer.getPaCode());

        LocalDateTime todayDate;

        if (zigEntityForDate == null) {
            todayDate = LocalDateTime.of(2000,1,1,0,0);
        } else {
            todayDate = zigEntityForDate.getDataDate();
        }

        List<ZigEntity> nonnegativeQohParts = zigRepo.findAllNonnegativeQohByPaCodeAndDataDateOrderByDmsStatus(dealer.getPaCode(), todayDate);
        List<ZigEntity> rimActiveParts = zigRepo.findAllRimActivePartsByPaCode(dealer.getPaCode(), todayDate);
        List<ZigEntity> rimInactiveParts = zigRepo.findAllRimInactivePartsByPaCode(dealer.getPaCode(), todayDate);
        List<ZigEntity> supportedParts = zigRepo.findAllSupportedParts(
                dealer.getPaCode(),
                todayDate,
                getStockStatusString(dealer.getDmsId())
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
        maps.put(1, totalPartsByStatus(nonnegativeQohParts, dealer.getDmsId()));
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

    public String getStockStatusString(int dms) {
        DmsStatus status;

        switch (dms) {
            case 8:
            case 37:
            case 54:
            case 61:
                status = CdkStatus.STOCK;
                break;
            case 22:
                status = AutomateStatus.STOCKED;
                break;
            case 9:
                status = AutosoftStatus.Y;
                break;
            case 13:
                status = DealerTrackStatus.A;
                break;
            case 23:
                status = LightyearStatus.S;
                break;
            case 30:
                status = PbsStatus.STOCK;
                break;
            case 1:
            case 50:
                status = RREraStatus.STOCK;
                break;
            case 48:
                status = RRPowerStatus.STOCK;
                break;
            default:
                status = GenericStatus.STOCK;
                break;
        }

        return status.toString();
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
     * @param dms The DMS ID for this dealer
     * @return A HashMap of: DMS status name -> status total
     */
    public HashMap<String, BigDecimal> totalPartsByStatus(List<ZigEntity> inventory, int dms) {
        HashMap<String, BigDecimal> totals = new HashMap<>();

        BigDecimal total;

        for (ZigEntity part : inventory) {

            String statusName;

            try {
                DmsStatus status = getStatusByDms(part.getDmsStatus(), dms);
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

    private DmsStatus getStatusByDms(String statusString, int dms) {
        DmsStatus status;

        switch (dms) {
            case 8:
            case 37:
            case 54:
            case 61:
                try {
                    status = CdkStatus.valueOf(statusString);
                } catch (NullPointerException | IllegalArgumentException e) {
                    status = CdkStatus.NS;
                }
                break;
            case 22:
                try {
                    status = AutomateStatus.valueOf(statusString);
                } catch (NullPointerException | IllegalArgumentException e) {
                    status = AutomateStatus.NOT_STOCKED;
                }
                break;
            case 9:
                try {
                    status = AutosoftStatus.valueOf(statusString);
                } catch (NullPointerException | IllegalArgumentException e) {
                    status = AutosoftStatus.N;
                }
                break;
            case 13:
                try {
                    status = DealerTrackStatus.valueOf(statusString);
                } catch (NullPointerException | IllegalArgumentException e) {
                    status = DealerTrackStatus.N;
                }
                break;
            case 23:
                try {
                    status = LightyearStatus.valueOf(statusString);
                } catch (NullPointerException | IllegalArgumentException e) {
                    status = LightyearStatus.N;
                }
                break;
            case 30:
                try {
                    status = PbsStatus.valueOf(statusString);
                } catch (NullPointerException | IllegalArgumentException e) {
                    status = PbsStatus.TEST;
                }
                break;
            case 1:
            case 48:
            case 50:
                try {
                    status = RREraStatus.valueOf(statusString);
                } catch (NullPointerException | IllegalArgumentException e) {
                    status = RREraStatus.NS;
                }
                break;
            default:
                status = GenericStatus.NONE;
                break;
        }

        return status;
    }
}
