package com.admi.data.imports;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.KpiEntity;
import com.admi.data.entities.MixPartsInventoryEntity;
import com.admi.data.enums.MixSource;
import com.admi.data.processes.ProcessService;
import com.admi.data.repositories.AipInventoryRepository;
import com.admi.data.repositories.MixPartsInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MixImportService {

	@Autowired
	ProcessService processService;

	@Autowired
	AipInventoryRepository inventoryRepo;

	@Autowired
	MixPartsInventoryRepository mixRepo;

	public void importMixDealer(Long dealerId, LocalDate date, String paCode, MixSource dms) {
		List<MixPartsInventoryEntity> mixInventory = mixRepo.findAllByDealerIdAndInventoryDate(dealerId, date);
		List<AipInventoryEntity> aipInventory = mixInventory
				.stream()
				.map( part -> part.toAipInventoryEntity(dms))
				.collect(Collectors.toList());
		inventoryRepo.saveAll(aipInventory);
		KpiEntity kpis = processService.calculateAisKpi(aipInventory, paCode);
	}

}
