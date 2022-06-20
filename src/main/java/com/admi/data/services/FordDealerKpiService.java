package com.admi.data.services;

import com.admi.data.entities.DealerMasterEntity;
import com.admi.data.entities.FordInventoryEntity;
import com.admi.data.entities.FordKpiEntity;
import com.admi.data.repositories.DealerMasterRepository;
import com.admi.data.repositories.FordInventoryRepository;
import com.admi.data.repositories.FordKpiRepository;
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

//	@Autowired
//	AipInventoryRepository inventoryRepo;

	@Autowired
	FordKpiRepository kpiRepo;

	@Autowired
	FordInventoryRepository inventoryRepo;

	public FordInventoryEntity testNewRels(String paCode, String partNumber) {
		return inventoryRepo.findFirstByPaCodeAndPartno(paCode, partNumber);
	}

	public void runAllFordDealers(LocalDate date) {
		List<DealerMasterEntity> dealers = dealerRepo.findAllInInventory();

		for(DealerMasterEntity dealer : dealers) {
			System.out.println("Running dealer " + dealer.getPaCode() + " - " + dealer.getDealershipName());
			runSingleDealer(dealer, date);
		}
	}

	public void runDealerByPaCode(String paCode, LocalDate date) {
		DealerMasterEntity dealer = dealerRepo.findInInventory(paCode);

		System.out.println("Running dealer " + dealer.getPaCode() + " - " + dealer.getDealershipName());
		runSingleDealer(dealer, date);
	}

	public void runSingleDealer(DealerMasterEntity dealer, LocalDate date) {
//		List<AipInventoryEntity> inventory = inventoryRepo.findFordInventoryByPaCode(dealer.getPaCode());
		List<FordInventoryEntity> inventory = inventoryRepo.findFordInventoryByPaCode(dealer.getPaCode());
		FordKpiEntity kpi;

		if (inventory.size() > 0) {

			LocalDate invDate = date;

			int totalRimValue = 0;
			int totalRimSkus = 0;

			int totalStockValue = 0;
			int totalStockSkus = 0;
			int totalStockValueOver9M = 0;

			int totalNonStockValue = 0;
			int totalNonStockSkus = 0;
			int totalNonStockValueUnder60 = 0;
			int totalNonStockValueOver60 = 0;
			int totalNonStockValueOver270 = 0;

			int badPartCount = 0;

			for (FordInventoryEntity part : inventory) {
				if (part.getQoh() > 0) {
					if (part.getMfgControlled() != null && part.getMfgControlled()) {
						totalRimValue += part.getCents() * part.getQoh();
						totalRimSkus += 1;
					} else {
						totalStockValue += getPartTotalByField(part, "Y", FordInventoryEntity:: getAdmiStatus);
						totalStockSkus += getSkuCountByField(part, "Y", FordInventoryEntity:: getAdmiStatus);
						totalStockValueOver9M += getPartTotalByStatusBeforeDate(part, "Y", 9, FordInventoryEntity:: getLastSaleOrReceipt, FordInventoryEntity:: getAdmiStatus);

						totalNonStockValue += getPartTotalByField(part, "N", FordInventoryEntity:: getAdmiStatus);
						totalNonStockSkus += getSkuCountByField(part, "N", FordInventoryEntity:: getAdmiStatus);
						totalNonStockValueUnder60 += getPartTotalByStatusAfterDate(part, "N", 2, FordInventoryEntity:: getLastSaleOrReceipt, FordInventoryEntity:: getAdmiStatus);
						totalNonStockValueOver60 += getPartTotalByStatusBeforeDate(part, "N", 2, FordInventoryEntity:: getLastSaleOrReceipt, FordInventoryEntity:: getAdmiStatus);
						totalNonStockValueOver270 += getPartTotalByStatusBeforeDate(part, "N", 9, FordInventoryEntity:: getLastSaleOrReceipt, FordInventoryEntity:: getAdmiStatus);
					}
				} else {
					badPartCount++;
				}
			}

			System.out.println("Bad Part Count: " + badPartCount);
			kpi = new FordKpiEntity(dealer.getDealerId(), invDate, totalStockValue, totalNonStockValue, totalRimValue, totalStockSkus, totalNonStockSkus, totalRimSkus, totalStockValueOver9M, totalNonStockValueOver60, totalNonStockValueUnder60, totalNonStockValueOver270);
			kpiRepo.save(kpi);
		}
	}

	private <T> Integer getPartTotalByField(FordInventoryEntity part, T valueToMatch, Function<FordInventoryEntity, T> fieldGetter) {
		if (Objects.equals(fieldGetter.apply(part), valueToMatch)) {
			return part.getQoh() * part.getCents();
		} else {
			return 0;
		}
	}

	private <T> Integer getSkuCountByField(FordInventoryEntity part, T valueToMatch, Function<FordInventoryEntity, T> fieldGetter) {
		if (Objects.equals(fieldGetter.apply(part), valueToMatch)) {
			return 1;
		} else {
			return 0;
		}
	}

	private Integer getPartTotalByStatusBeforeDate(FordInventoryEntity part,
	                                               String status,
	                                               Integer monthCount,
	                                               Function<FordInventoryEntity, LocalDate> dateGetter,
	                                               Function<FordInventoryEntity, String> stringGetter) {
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

	private Integer getPartTotalByStatusAfterDate(FordInventoryEntity part,
	                                              String status,
	                                              Integer monthCount,
	                                              Function<FordInventoryEntity, LocalDate> dateGetter,
	                                              Function<FordInventoryEntity, String> stringGetter) {
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
