package com.admi.data.controllers;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.KpiEntity;
import com.admi.data.services.AisKpiService;
import com.admi.data.repositories.AipInventoryRepository;
import com.admi.data.services.CpcKpiService;
import com.admi.data.services.ProcessService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RequestMapping("/process")
@Controller
public class ProcessesController {

	@Autowired
	AisKpiService aisKpiService;

	@Autowired
	AipInventoryRepository aipInventoryRepo;

	@Autowired
	ProcessService processService;

	@ResponseBody
//	@GetMapping("/processTest")
	public String processTest(Model model) {
		List<AipInventoryEntity> inventory = aipInventoryRepo.findAllByDealerIdAndDataDate(1969L, LocalDate.of(2021,10,21));

		KpiEntity kpis = aisKpiService.calculateAisKpi(inventory);

		return kpis.toString();
	}

	/**
	 * Called to delete a Motorcraft order file from the P: drive
	 */
	@PostMapping(value = "/motorcraftCancellation/{orderNumber}")
	@ResponseBody
	public String motorcraftCancellation(@PathVariable("orderNumber") String orderNumber, Model model)
			throws IOException, InvalidFormatException, NoSuchFieldException, IllegalAccessException {
		System.out.println("Order Number: " + orderNumber);
		Boolean deleteSuccessful = processService.deleteMotorcraftOrderFile(orderNumber);
		return deleteSuccessful.toString();
	}

	@ResponseBody
	@GetMapping("/cpc")
	public String processCpcKpi() {
		cpcService.runCpcDealers();

		return "It did the thing.";
	}
}
