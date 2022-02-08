package com.admi.data.controllers;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.KpiEntity;
import com.admi.data.services.AisKpiService;
import com.admi.data.repositories.AipInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/process")
@Controller
public class ProcessesController {

	@Autowired
	AisKpiService aisKpiService;

	@Autowired
	AipInventoryRepository aipInventoryRepo;

	@ResponseBody
//	@GetMapping("/processTest")
	public String processTest(Model model) {
		List<AipInventoryEntity> inventory = aipInventoryRepo.findAllByDealerIdAndDataDate(1969L, LocalDate.of(2021,10,21));

		KpiEntity kpis = aisKpiService.calculateAisKpi(inventory);

		return kpis.toString();
	}

}
