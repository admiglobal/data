package com.admi.data.services;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.MixPartsInventoryEntity;
import com.admi.data.enums.MixSource;
import com.admi.data.repositories.AipInventoryRepository;
import com.admi.data.repositories.MixPartsInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MixImportService {

	@Autowired
	AipInventoryService aipInventoryService;

	@Autowired
	AipInventoryRepository inventoryRepo;

	@Autowired
	MixPartsInventoryRepository mixRepo;

	@Async("asyncMixExecutor")
	public void importMixDealer(Long dealerId, LocalDate date, String paCode, MixSource dms) {
		List<MixPartsInventoryEntity> mixInventory = mixRepo.findAllByDealerIdAndInventoryDate(dealerId, date);
		List<AipInventoryEntity> aipInventory = mixInventory
				.stream()
				.map(part -> part.toAipInventoryEntity(dms))
				.collect(Collectors.toList());

		aipInventoryService.saveAll(aipInventory, dealerId, paCode);

		System.out.println("Imported and processed "+ dms.getSourceName() + " " + paCode + " Dealer Id: " + dealerId);
	}

}
