package com.admi.data.services;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.enums.DmsProvider;
import com.admi.data.repositories.AipInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AipInventoryService {

	@Autowired
	ProcessService processService;

	@Autowired
	ZigService zigService;

	@Autowired
	RimHistoryService rimService;

	@Autowired
	AipInventoryRepository inventoryRepo;

	@Autowired
	StatusService statusService;

	public void saveAll(List<AipInventoryEntity> inventory, Long dealerId, String paCode, DmsProvider dms) {
		System.out.println("Saving inventory...");
		long aipSaveStart = System.currentTimeMillis();

		try {
			inventoryRepo.saveAll(inventory);
		} catch (Exception e) {
			e.printStackTrace();
			for (AipInventoryEntity part : inventory) {
				try {
					inventoryRepo.save(part);
				} catch (Exception f) {
					System.out.println("Part not saved - "
							+ "Dealer Id: " + part.getDealerId()
							+ " Part Number: " + part.getPartNo()
							+ " Desc: " + part.getDescription());
				}
			}
		}
		System.out.println("Finished saving parts. Processing inventory...");
		processInventory(inventory, dealerId, paCode, dms);

		long aipSaveEnd = System.currentTimeMillis();
		System.out.println("Total time taken to save: " + (aipSaveEnd-aipSaveStart)/1000 + "s.");
	}

	public void processInventory(List<AipInventoryEntity> inventory, Long dealerId, String paCode, DmsProvider dms) {
		zigService.saveAsZig(inventory, paCode);
		rimService.addOrUpdateRimParts(dealerId, inventory);
		processService.calculateAisKpi(inventory, dms);
		statusService.runStatusValuesForToday(dealerId, dms);
	}

}
