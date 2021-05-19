package com.admi.data.processes;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.KpiEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessService {

	@Autowired
	AisKpiService aisKpiService;

	public KpiEntity calculateAisKpi(List<AipInventoryEntity> inventory, String paCode) {
		return aisKpiService.calculateAisKpi(inventory, paCode);
	}

}
