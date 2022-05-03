package com.admi.data.services;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.KpiEntity;
import com.admi.data.enums.statuses.DmsStatus;
import com.admi.data.repositories.KpiRepository;
import com.admi.data.repositories.ZigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

@Service
public class AisKpiService {

	@Autowired
	DateService dateService;

	@Autowired
	KpiRepository kpiRepo;

	@Autowired
	ZigRepository zigRepo;

	public KpiEntity calculateAisKpi(List<AipInventoryEntity> inventory) {
		KpiEntity kpi = new KpiEntity();

		if (inventory.size() > 0) {
			kpi.setDealerId(inventory.get(0).getDealerId());
//			Always treat KPI calculations as though they are for yesterday.
			kpi.setDataDate(dateService.getLongDate(inventory.get(0).getDataDate().minusDays(1)));

			Long totalDmsSku = 0L;
			Long pieceCount = 0L;

			for (AipInventoryEntity part : inventory) {
				totalDmsSku += getSkuCountByField(part, "S", AipInventoryEntity::getAdmiStatus);

				if ((part.getQoh() != null && part.getQoh() > 0)
						&& (part.getCents() != null && part.getCents() > 0)) {

					Long totalStockValue = getPartTotalByField(part, "S", AipInventoryEntity::getAdmiStatus);
					Long totalNonStockValue = getPartTotalByField(part, "N", AipInventoryEntity::getAdmiStatus);

					Long totalStockSkuCount = getSkuCountByField(part, "S", AipInventoryEntity::getAdmiStatus);
					Long totalNonStockSkuCount = getSkuCountByField(part, "N", AipInventoryEntity::getAdmiStatus);

					Long stockOver9m = getPartTotalByStatusBeforeDate(part, "S", 270L, AipInventoryEntity::getLastSaleOrReceipt);
					Long nonStockOver9m = getPartTotalByStatusBeforeDate(part, "N", 270L, AipInventoryEntity::getLastSaleOrReceipt);
					Long nonStockLT60 = getPartTotalByStatusBetweenDate(part, "N", 0L, 61L, AipInventoryEntity::getLastSaleOrReceipt);
					Long nonStock30To60 = getPartTotalByStatusBetweenDate(part, "N", 29L, 61L, AipInventoryEntity::getLastSaleOrReceipt);
					Long nonStock61To90 = getPartTotalByStatusBetweenDate(part, "N", 60L, 91L, AipInventoryEntity::getLastSaleOrReceipt);
					Long nonStock61To9m = getPartTotalByStatusBetweenDate(part, "N", 60L, 271L, AipInventoryEntity::getLastSaleOrReceipt);

					Long preIdle = getPartTotalByStatusBetweenDate(part, "N", 29L, 61L, AipInventoryEntity::getLastReceipt);
					Long newIdle = getPartTotalByStatusBetweenDate(part, "N", 60L, 91L, AipInventoryEntity::getLastReceipt);

					Long rimValue = getPartTotalByField(part, true, AipInventoryEntity :: getMfgControlled);
					Long rimSkuCount = getSkuCountByField(part, true, AipInventoryEntity :: getMfgControlled);

					kpi.setTotalSValue(kpi.getTotalSValue() + totalStockValue);
					kpi.setTotalNsValue(kpi.getTotalNsValue() + totalNonStockValue);

					kpi.setSSkuCount(kpi.getSSkuCount() + totalStockSkuCount);
					kpi.setNsSkuCount(kpi.getNsSkuCount() + totalNonStockSkuCount);

					kpi.setSOver9M(kpi.getSOver9M() + stockOver9m);
					kpi.setNsOver9M(kpi.getNsOver9M() + nonStockOver9m);
					kpi.setNsLessThan60(kpi.getNsLessThan60() + nonStockLT60);
					kpi.setNs30To60(kpi.getNs30To60() + nonStock30To60);
					kpi.setNs61To90(kpi.getNs61To90() + nonStock61To90);
					kpi.setNs61To9M(kpi.getNs61To9M() + nonStock61To9m);
					kpi.setNsPreIdle(kpi.getNsPreIdle() + preIdle);
					kpi.setNsNewIdle(kpi.getNsNewIdle() + newIdle);

					kpi.setTotalRimValue(kpi.getTotalRimValue() + rimValue);
					kpi.setRimSkuCount(kpi.getRimSkuCount() + rimSkuCount);

					pieceCount += part.getQoh();
				}
			}
			kpi.setDmsPerf(getDmsPerformance(kpi.getSSkuCount(), totalDmsSku));
			kpi.setPieceCount(pieceCount);
		}


//		TODO: Move into IF statement?
		kpi.convertAllCentsToDollars();

		kpiRepo.saveAndFlush(kpi);

		return kpi;
	}

	private <T> Long getPartTotalByField(AipInventoryEntity part, T valueToMatch, Function<AipInventoryEntity, T> fieldGetter) {
		if (Objects.equals(fieldGetter.apply(part), valueToMatch)) {
			return (long) part.getQoh() * part.getCents();
		} else {
			return 0L;
		}
	}

//	private <T> Long getPartTotalByField(AipInventoryEntity part, List<DmsStatus> statusList, Function<AipInventoryEntity, T> fieldGetter) {
//		T status = fieldGetter.apply(part);
//
////		if ()
//
//		if (Objects.equals(fieldGetter.apply(part), valueToMatch)) {
//			return (long) part.getQoh() * part.getCents();
//		} else {
//			return 0L;
//		}
//	}

	private <T> Long getSkuCountByField(AipInventoryEntity part, T valueToMatch, Function<AipInventoryEntity, T> fieldGetter) {
		if (Objects.equals(fieldGetter.apply(part), valueToMatch)) {
			return 1L;
		} else {
			return 0L;
		}
	}

	private Long getPartTotalByStatusBetweenDate(AipInventoryEntity part,
	                                             String status,
	                                             Long startDayCount,
	                                             Long endDayCount,
	                                             Function<AipInventoryEntity, LocalDate> dateGetter) {
		LocalDate startDate = LocalDate.now().minusDays(startDayCount);
		LocalDate endDate = LocalDate.now().minusDays(endDayCount);
		LocalDate partDate = dateGetter.apply(part);

		if (partDate != null
				&& partDate.isBefore(startDate)
				&& partDate.isAfter(endDate)
				&& part.getAdmiStatus().equalsIgnoreCase(status)) {
			return (long) part.getCents() * part.getQoh();
		} else {
			return 0L;
		}
	}

	private Long getPartTotalByStatusBeforeDate(AipInventoryEntity part,
	                                           String status,
	                                           Long dayCount,
	                                           Function<AipInventoryEntity, LocalDate> dateGetter) {
		LocalDate date = LocalDate.now().minusDays(dayCount);
		LocalDate partDate = dateGetter.apply(part);

		if (partDate != null
				&& partDate.isBefore(date)
				&& part.getAdmiStatus().equalsIgnoreCase(status)) {
			return (long) part.getCents() * part.getQoh();
		} else {
			return 0L;
		}
	}

	private Float getDmsPerformance(long stockSkuWithQoh, long totalStockSku) {
		if (totalStockSku > 0) {
			BigDecimal num = new BigDecimal(stockSkuWithQoh);
			BigDecimal den = new BigDecimal(totalStockSku);

			BigDecimal decimal = num.divide(den, 4, RoundingMode.HALF_UP);
			return decimal.floatValue() * 100;
		} else {
			return 0F;
		}
	}

	private Map<String, Pair<Long, Long>> totalByStatus(List<AipInventoryEntity> inventory,
	                                                    Function<AipInventoryEntity, String> statusGetter) {
		Map<String, Pair<Long, Long>> statusMap = new HashMap<>();

		Long count = 0L;
		Long total = 0L;

		for (AipInventoryEntity part : inventory) {

			if (part.getQoh() > 0) {
				String newStatus = statusGetter.apply(part);

				Pair<Long, Long> countAndTotal = statusMap.get(newStatus);

				count = 1L;
				total = (long) part.getCents() * part.getQoh();

				if (countAndTotal != null) {
					count = count + countAndTotal.getFirst();
					total = total + countAndTotal.getSecond();
				}
				statusMap.put(newStatus, Pair.of(count, total));
			}
		}

		return statusMap;
	}
}
