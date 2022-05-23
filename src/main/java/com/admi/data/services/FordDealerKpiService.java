package com.admi.data.services;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.DealerMasterEntity;
import com.admi.data.entities.FordDealerKpiEntity;
import com.admi.data.repositories.AipInventoryRepository;
import com.admi.data.repositories.DealerMasterRepository;
import com.admi.data.repositories.FordDealerKpiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Service
public class FordDealerKpiService {

	@Autowired
	DealerMasterRepository dealerRepo;

	@Autowired
	AipInventoryRepository inventoryRepo;

	@Autowired
	FordDealerKpiRepository kpiRepo;

	public void runAllFordDealers() {
		List<DealerMasterEntity> dealers = dealerRepo.findAllInInventory();

		for(DealerMasterEntity dealer : dealers) {
			System.out.println("Running dealer " + dealer.getPaCode() + " - " + dealer.getDealershipName());
			runSingleDealer(dealer);
		}
	}

	public void runSingleDealer(DealerMasterEntity dealer) {
		List<AipInventoryEntity> inventory = inventoryRepo.findFordInventoryByPaCode(dealer.getPaCode());
		FordDealerKpiEntity kpi;

		if (inventory.size() > 0) {

			LocalDate invDate = null;

			int totalRimValue = 0;
			int totalRimSkus = 0;

			int totalStockValue = 0;
			int totalStockSkus = 0;
			int totalStockValueOver9M = 0;

			int totalNonStockValue = 0;
			int totalNonStockSkus = 0;
			int totalNonStockValueUnder60 = 0;
			int totalNonStockValueOver60 = 0;

			for (AipInventoryEntity part : inventory) {
				if (invDate == null) {
					invDate = part.getDataDate();
				}

				if (part.getQoh() > 0) {
					if (part.getMfgControlled() != null && part.getMfgControlled()) {
						totalRimValue += part.getCents() * part.getQoh();
						totalRimSkus += 1;
					} else {
						totalStockValue += getPartTotalByField(part, "Y", AipInventoryEntity :: getAdmiStatus);
						totalStockSkus += getSkuCountByField(part, "Y", AipInventoryEntity :: getAdmiStatus);
						totalStockValueOver9M += getPartTotalByStatusBeforeDate(part, "Y", 9, AipInventoryEntity :: getLastSaleOrReceipt, AipInventoryEntity :: getAdmiStatus);

						totalNonStockValue += getPartTotalByField(part, "N", AipInventoryEntity :: getAdmiStatus);
						totalNonStockSkus += getSkuCountByField(part, "N", AipInventoryEntity :: getAdmiStatus);
						totalNonStockValueUnder60 += getPartTotalByStatusAfterDate(part, "N", 2, AipInventoryEntity :: getLastSaleOrReceipt, AipInventoryEntity :: getAdmiStatus);
						totalNonStockValueOver60 += getPartTotalByStatusBeforeDate(part, "N", 2, AipInventoryEntity :: getLastSaleOrReceipt, AipInventoryEntity :: getAdmiStatus);
					}
				}
			}

			kpi = new FordDealerKpiEntity(dealer.getDealerId(), invDate, totalStockValue, totalNonStockValue, totalRimValue, totalStockSkus, totalNonStockSkus, totalRimSkus, totalStockValueOver9M, totalNonStockValueOver60, totalNonStockValueUnder60);
			kpiRepo.save(kpi);
		}
	}

	private <T> Integer getPartTotalByField(AipInventoryEntity part, T valueToMatch, Function<AipInventoryEntity, T> fieldGetter) {
		if (Objects.equals(fieldGetter.apply(part), valueToMatch)) {
			return part.getQoh() * part.getCents();
		} else {
			return 0;
		}
	}

	private <T> Integer getSkuCountByField(AipInventoryEntity part, T valueToMatch, Function<AipInventoryEntity, T> fieldGetter) {
		if (Objects.equals(fieldGetter.apply(part), valueToMatch)) {
			return 1;
		} else {
			return 0;
		}
	}

	private Integer getPartTotalByStatusBeforeDate(AipInventoryEntity part,
	                                               String status,
	                                               Integer monthCount,
	                                               Function<AipInventoryEntity, LocalDate> dateGetter,
	                                               Function<AipInventoryEntity, String> stringGetter) {
		LocalDate date = LocalDate.now().minusMonths(monthCount);
		LocalDate partDate = dateGetter.apply(part);

		if (partDate != null
				&& partDate.isBefore(date)
				&& stringGetter.apply(part).equalsIgnoreCase(status)) {
			return part.getCents() * part.getQoh();
		} else {
			return 0;
		}
	}

	private Integer getPartTotalByStatusAfterDate(AipInventoryEntity part,
	                                               String status,
	                                               Integer monthCount,
	                                               Function<AipInventoryEntity, LocalDate> dateGetter,
	                                               Function<AipInventoryEntity, String> stringGetter) {
		LocalDate date = LocalDate.now().minusMonths(monthCount);
		LocalDate partDate = dateGetter.apply(part);

		if (partDate != null
				&& partDate.isAfter(date)
				&& stringGetter.apply(part).equalsIgnoreCase(status)) {
			return part.getCents() * part.getQoh();
		} else {
			return 0;
		}
	}
}
