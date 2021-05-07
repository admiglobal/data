package com.admi.data.processes;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.KpiEntity;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class AisKpiService {

	public void calculateAisKpi(List<AipInventoryEntity> inventory) {
		Map<String, Pair<Long, Long>> admiStatusCountAndTotal = totalByStatus(inventory, AipInventoryEntity::getAdmiStatus);
		Map<String, Pair<Long, Long>> dmsStatusCountAndTotal = totalByStatus(inventory, AipInventoryEntity::getStatus);

	}


	public Map<String, Pair<Long, Long>> totalByStatus(List<AipInventoryEntity> inventory,
	                                                   Function<AipInventoryEntity, String> statusGetter) {
		Map<String, Pair<Long, Long>> statusMap = new HashMap<>();

		Long count = 0L;
		Long total = 0L;

		for (AipInventoryEntity part : inventory) {

			if (part.getQoh() > 0) {
				String newStatus = statusGetter.apply(part);

				Pair<Long, Long> countAndTotal = statusMap.get(newStatus);

				count = 1L;
				total = part.getCents() * part.getQoh();

				if (countAndTotal != null) {
					count = count + countAndTotal.getFirst();
					total = total + countAndTotal.getSecond();
				}

				statusMap.put(newStatus, Pair.of(count, total));
			}
		}

		return statusMap;
	}

	public Float getDmsPerformance(Long skuCountWithQuantityOnHand, Long totalSkuCount) {

	}

	public KpiEntity setAisKpi(List<AipInventoryEntity> inventory) {
		KpiEntity kpi = new KpiEntity();

		if (inventory.size() > 0) {
			kpi.setDealerId(inventory.get(0).getDealerId());
	//		kpi.setDataDate(inventory.get(0).getDataDate());
			kpi.setDataDate(202105L);

			Long totalDmsSku = 0L;
			Long dmsSkuWithQoh = 0L;

			for (AipInventoryEntity part : inventory) {
				if (part.getQoh() > 0) {

					kpi.setTotalSValue(kpi.getTotalSValue() + getPartTotalByStatus(part, "S"));
					kpi.setTotalNsValue(kpi.getTotalNsValue() + getPartTotalByStatus(part, "N"));

					kpi.setSSkuCount(kpi.getSSkuCount() + getSkuCountByStatus(part, "S"));
					kpi.setNsSkuCount(kpi.getNsSkuCount() + getSkuCountByStatus(part, "N"));

					Long stockOver9m = getPartTotalByStatusBeforeDate(part, "S", 270L, AipInventoryEntity::getLastSale);
					Long nonStockOver9m = getPartTotalByStatusBeforeDate(part, "N", 270L, AipInventoryEntity::getLastSale);
					Long nonStockLT60 = getPartTotalByStatusBetweenDate(part, "N", 0L, 61L, AipInventoryEntity::getLastSale);
					Long nonStock30To60 = getPartTotalByStatusBetweenDate(part, "N", 29L, 61L, AipInventoryEntity::getLastSale);
					Long nonStock61To90 = getPartTotalByStatusBetweenDate(part, "N", 60L, 91L, AipInventoryEntity::getLastSale);
					Long nonStock61To9m = getPartTotalByStatusBetweenDate(part, "N", 60L, 271L, AipInventoryEntity::getLastSale);
					Long preIdle = getPartTotalByStatusBetweenDate(part, "N", 29L, 61L, AipInventoryEntity::getLastSale);
					Long newIdle = getPartTotalByStatusBetweenDate(part, "N", 60L, 91L, AipInventoryEntity::getLastSale);

					kpi.setSOver9M(kpi.getSOver9M() + stockOver9m);
					kpi.setNsOver9M(kpi.getNsOver9M() + nonStockOver9m);
					kpi.setNsLessThan60(kpi.getNsLessThan60() + nonStockLT60);
					kpi.setNs30To60(kpi.getNs30To60() + nonStock30To60);
					kpi.setNs61To90(kpi.getNs61To90() + nonStock61To90);
					kpi.setNs61To9M(kpi.getNs61To9M() + nonStock61To9m);
					kpi.setNsPreIdle(kpi.getNsPreIdle() + preIdle);
					kpi.setNsNewIdle(kpi.getNsNewIdle() + newIdle);

//					DMS Perf
//					Piece Count
//					Total Rim Value
//					RIM Sku Count


				}
			}
		}


		return kpi;
	}

	public Long getPartTotalByStatus(AipInventoryEntity part, String status) {
		if (part.getAdmiStatus().equals(status)) {
			return part.getQoh() * part.getCents();
		} else {
			return 0L;
		}
	}

	public Long getSkuCountByStatus(AipInventoryEntity part, String status) {
		if (part.getAdmiStatus().equals(status)) {
			return 1L;
		} else {
			return 0L;
		}
	}

	public Long getPartTotalByStatusBetweenDate(AipInventoryEntity part,
	                                            String status,
	                                            Long startDayCount,
	                                            Long endDayCount,
	                                            Function<AipInventoryEntity, LocalDate> dateGetter) {
		LocalDate startDate = LocalDate.now().minusDays(startDayCount);
		LocalDate endDate = LocalDate.now().minusDays(endDayCount);

		LocalDate partDate = dateGetter.apply(part);

		if (partDate.isBefore(startDate)
				&& partDate.isAfter(endDate)
				&& part.getAdmiStatus().equalsIgnoreCase(status)) {
			return part.getCents() * part.getQoh();
		} else {
			return 0L;
		}
	}

	public Long getPartTotalByStatusBeforeDate(AipInventoryEntity part,
	                                           String status,
	                                           Long dayCount,
	                                           Function<AipInventoryEntity, LocalDate> dateGetter) {
		LocalDate date = LocalDate.now().minusDays(dayCount);

		LocalDate partDate = dateGetter.apply(part);

		if (partDate.isBefore(date) && part.getAdmiStatus().equalsIgnoreCase(status)) {
			return part.getCents() * part.getQoh();
		} else {
			return 0L;
		}
	}




}
