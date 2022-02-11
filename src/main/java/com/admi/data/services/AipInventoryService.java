package com.admi.data.services;

import com.admi.data.entities.AipInventoryEntity;
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

	public void saveAll(List<AipInventoryEntity> inventory, Long dealerId, String paCode) {
		try {
			inventoryRepo.saveAll(inventory);
		} catch (Exception e) {
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
		processInventory(inventory, dealerId, paCode);
	}

	public void processInventory(List<AipInventoryEntity> inventory, Long dealerId, String paCode) {
		zigService.saveAsZig(inventory, paCode);
		rimService.addOrUpdateRimParts(dealerId, inventory);
		processService.calculateAisKpi(inventory);
	}

}
