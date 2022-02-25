package com.admi.data.services;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.repositories.AipInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
		//truncate items for testing
//		List<AipInventoryEntity> shortInventory = new ArrayList<>();
//		int i = 1;
//		for(AipInventoryEntity part: inventory){
//			shortInventory.add(part);
//			System.out.println("Added part to short list: " + part);
//			if(i++ >= 100){
//				break;
//			}
//		}
//		inventory = shortInventory;

		System.out.println("Saving all...");
		long aipSaveStart = System.currentTimeMillis();
		try {
			inventoryRepo.saveAll(inventory);
		} catch (Exception e) {
			System.out.println("Unable to save all inventory parts at once.");
			e.printStackTrace();
			for (AipInventoryEntity part : inventory) {
//				System.out.println("Saving part... - "
//						+ "Dealer Id: " + part.getDealerId()
//						+ " Part Number: " + part.getPartNo()
//						+ " Desc: " + part.getDescription());
				try {
					inventoryRepo.save(part);
				} catch (Exception f) {
					System.out.println("Part not saved - "
							+ "Dealer Id: " + part.getDealerId()
							+ " Part Number: " + part.getPartNo()
							+ " Desc: " + part.getDescription());
//					f.printStackTrace();
				}
			}
		}
		System.out.println("Finished saving parts. Processing inventory...");
		processInventory(inventory, dealerId, paCode);
		long aipSaveEnd = System.currentTimeMillis();
		System.out.println("Total time taken to save: " + (aipSaveEnd-aipSaveStart)/1000);
	}

	public void processInventory(List<AipInventoryEntity> inventory, Long dealerId, String paCode) {
		zigService.saveAsZig(inventory, paCode);
		rimService.addOrUpdateRimParts(dealerId, inventory);
		processService.calculateAisKpi(inventory);
	}

}
