package com.admi.data.services;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.KpiEntity;
import com.admi.data.enums.DmsProvider;
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

	public KpiEntity calculateAisKpi(List<AipInventoryEntity> inventory, DmsProvider dms) {
		KpiEntity kpi = new KpiEntity();

		DmsStatus statuses = dms.getStatusType();

		if (inventory.size() > 0) {
			kpi.setDealerId(inventory.get(0).getDealerId());
//			Always treat KPI calculations as though they are for yesterday.
			kpi.setDataDate(dateService.getLongDate(inventory.get(0).getDataDate().minusDays(1)));

			Long totalDmsSku = 0L;
			Long totalDmsSkuInStock = 0L;
			Long pieceCount = 0L;

			for (AipInventoryEntity part : inventory) {
				totalDmsSku += getSkuCountByField(part, AipInventoryEntity::getStatus, List.of(statuses.getStockStatus()), dms);

				if ((part.getQoh() != null && part.getQoh() > 0)
						&& (part.getCents() != null && part.getCents() > 0)) {

					Long totalStockValue = getPartTotalByStatus(part, statuses.getStockStatuses(), AipInventoryEntity::getStatus, dms);
					Long totalNonStockValue = getPartTotalByStatus(part, statuses.getNonStockStatuses(), AipInventoryEntity::getStatus, dms);

					Long totalStockSkuCount = getSkuCountByField(part, AipInventoryEntity::getStatus, statuses.getStockStatuses(), dms);
					Long totalNonStockSkuCount = getSkuCountByField(part, AipInventoryEntity::getStatus, statuses.getNonStockStatuses(), dms);

					Long stockOver9m = getPartTotalByStatusBeforeDate(part, 270L, AipInventoryEntity::getLastSaleOrReceipt, statuses.getStockStatuses(), dms);
					Long nonStockOver9m = getPartTotalByStatusBeforeDate(part, 270L, AipInventoryEntity::getLastSaleOrReceipt, statuses.getNonStockStatuses(), dms);
					Long nonStockLT60 = getPartTotalByStatusBetweenDate(part, 0L, 61L, AipInventoryEntity::getLastSaleOrReceipt, statuses.getNonStockStatuses(), dms);
					Long nonStock30To60 = getPartTotalByStatusBetweenDate(part, 29L, 61L, AipInventoryEntity::getLastSaleOrReceipt, statuses.getNonStockStatuses(), dms);
					Long nonStock61To90 = getPartTotalByStatusBetweenDate(part, 60L, 91L, AipInventoryEntity::getLastSaleOrReceipt, statuses.getNonStockStatuses(), dms);
					Long nonStock61To9m = getPartTotalByStatusBetweenDate(part, 60L, 271L, AipInventoryEntity::getLastSaleOrReceipt, statuses.getNonStockStatuses(), dms);

					Long preIdle = getPartTotalByStatusBetweenDate(part, 29L, 61L, AipInventoryEntity::getLastReceipt, statuses.getInactiveStatuses(), dms);
					Long newIdle = getPartTotalByStatusBetweenDate(part, 60L, 91L, AipInventoryEntity::getLastReceipt, statuses.getInactiveStatuses(), dms);

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

					totalDmsSkuInStock += getSkuCountByField(part, AipInventoryEntity::getStatus, List.of(statuses.getStockStatus()), dms);
					pieceCount += part.getQoh();
				}
			}
			kpi.setDmsPerf(getDmsPerformance(totalDmsSkuInStock, totalDmsSku));
			kpi.setPieceCount(pieceCount);
			kpi.convertAllCentsToDollars();
		}

		kpiRepo.saveAndFlush(kpi);
		System.out.println(kpi);

		return kpi;
	}

	private <T> Long getPartTotalByField(AipInventoryEntity part, T valueToMatch, Function<AipInventoryEntity, T> fieldGetter) {
		if (Objects.equals(fieldGetter.apply(part), valueToMatch)) {
			return (long) part.getQoh() * part.getCents();
		} else {
			return 0L;
		}
	}

	private Long getPartTotalByStatus(AipInventoryEntity part, List<DmsStatus> statusList, Function<AipInventoryEntity, String> fieldGetter, DmsProvider dmsProvider) {
		String status = fieldGetter.apply(part);

		if (statusList.stream().anyMatch(s -> Objects.equals(s, DmsStatus.findStatus(part.getStatus(), dmsProvider)))) {
			return (long) part.getQoh() * part.getCents();
		} else {
			return 0L;
		}
	}

	private <T> Long getSkuCountByField(AipInventoryEntity part, T valueToMatch, Function<AipInventoryEntity, T> fieldGetter) {
		if (Objects.equals(fieldGetter.apply(part), valueToMatch)) {
			return 1L;
		} else {
			return 0L;
		}
	}

	private Long getSkuCountByField(AipInventoryEntity part, Function<AipInventoryEntity, String> fieldGetter, List<DmsStatus> statusList, DmsProvider dmsProvider) {
		String status = fieldGetter.apply(part);

		if (statusList.stream().anyMatch(s -> Objects.equals(s, DmsStatus.findStatus(part.getStatus(), dmsProvider)))) {
			return 1L;
		} else {
			return 0L;
		}
	}

	private Long getPartTotalByStatusBetweenDate(AipInventoryEntity part,
	                                             Long startDayCount,
	                                             Long endDayCount,
	                                             Function<AipInventoryEntity, LocalDate> dateGetter,
	                                             List<DmsStatus> statusList,
	                                             DmsProvider dmsProvider) {
		LocalDate startDate = LocalDate.now().minusDays(startDayCount);
		LocalDate endDate = LocalDate.now().minusDays(endDayCount);
		LocalDate partDate = dateGetter.apply(part);

		if (partDate != null
				&& partDate.isBefore(startDate)
				&& partDate.isAfter(endDate)
				&& statusList.stream().anyMatch(s -> Objects.equals(s, DmsStatus.findStatus(part.getStatus(), dmsProvider)))) {
			return (long) part.getCents() * part.getQoh();
		} else {
			return 0L;
		}
	}

	private Long getPartTotalByStatusBeforeDate(AipInventoryEntity part,
	                                            Long dayCount,
	                                            Function<AipInventoryEntity, LocalDate> dateGetter,
	                                            List<DmsStatus> statusList,
	                                            DmsProvider dmsProvider) {
		LocalDate date = LocalDate.now().minusDays(dayCount);
		LocalDate partDate = dateGetter.apply(part);

		if (partDate != null
				&& partDate.isBefore(date)
				&& statusList.stream().anyMatch(s -> Objects.equals(s, DmsStatus.findStatus(part.getStatus(), dmsProvider)))) {
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

}
