package com.admi.data.services;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.CdkDealersEntity;
import com.admi.data.entities.CdkPartsInventoryChild;
import com.admi.data.services.ProcessService;
import com.admi.data.services.RimHistoryService;
import com.admi.data.services.ZigService;
import com.admi.data.repositories.AipInventoryRepository;
import com.admi.data.repositories.CdkDealersRepository;
import com.admi.data.repositories.CdkPartsInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CdkImportService {

	@Autowired
	ProcessService processService;

	@Autowired
	ZigService zigService;

	@Autowired
	RimHistoryService rimService;

	@Autowired
	CdkPartsInventoryRepository cdkRepo;

	@Autowired
	CdkDealersRepository cdkDealersRepo;

	@Autowired
	AipInventoryRepository inventoryRepo;

	@Async("asyncCdkExecutor")
	public void importInventory(Long dealerId, LocalDate localDate, String paCode) {

		CdkDealersEntity dealer = cdkDealersRepo.findAllByAdmiDealerId(dealerId);
		List<CdkPartsInventoryChild> inventory = cdkRepo.findAllByDealerIdAndInventoryDate(dealer.getDealerId(), localDate);
		List<AipInventoryEntity> aipInventory = inventory.stream().map(part -> part.toAipInventoryEntity(dealerId)).collect(Collectors.toList());

		try {
			inventoryRepo.saveAll(aipInventory);
		} catch (Exception e) {
			for (AipInventoryEntity part : aipInventory) {
				try {
					inventoryRepo.save(part);
				} catch (Exception f) {
					System.out.println("Part not saved - "
							+ "Dealer Id: " + dealerId
							+ " Part Number: " + part.getPartNo()
							+ " Desc: " + part.getDescription());
				}
			}
		}

		zigService.saveAsZig(aipInventory, paCode);
		rimService.addOrUpdateRimParts(dealerId, aipInventory);
		processService.calculateAisKpi(aipInventory);

		System.out.println("Imported and processed CDK " + paCode + " Dealer Id: " + dealerId);
	}

}
