package com.admi.data.imports;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.CdkDealersEntity;
import com.admi.data.entities.CdkPartsInventoryChild;
import com.admi.data.entities.KpiEntity;
import com.admi.data.processes.ProcessService;
import com.admi.data.repositories.AipInventoryRepository;
import com.admi.data.repositories.CdkDealersRepository;
import com.admi.data.repositories.CdkPartsInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CdkImportService {

	@Autowired
	CdkPartsInventoryRepository cdkRepo;

	@Autowired
	CdkDealersRepository cdkDealersRepo;

	@Autowired
	ProcessService processService;

	@Autowired
	AipInventoryRepository inventoryRepo;

	public void importInventory(Long dealerId, LocalDate localDate, String paCode) {

		CdkDealersEntity dealer = cdkDealersRepo.findAllByAdmiDealerId(dealerId);
//		String dealerId1 = "3PA86286";
//		List<CdkPartsInventoryChild> inventory = cdkRepo.findAllByDealerIdAndInventoryDate(dealerId1, localDate);
		List<CdkPartsInventoryChild> inventory = cdkRepo.findAllByDealerIdAndInventoryDate(dealer.getDealerId(), localDate);
		List<AipInventoryEntity> aipInventory = inventory.stream().map(part -> part.toAipInventoryEntity(dealerId)).collect(Collectors.toList());
		inventoryRepo.saveAll(aipInventory);
		KpiEntity kpis = processService.calculateAisKpi(aipInventory, paCode);
	}

}
