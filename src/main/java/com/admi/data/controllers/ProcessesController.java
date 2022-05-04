package com.admi.data.controllers;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.CpcDealerProfileEntity;
import com.admi.data.entities.KpiEntity;
import com.admi.data.entities.OpcTsp200DataEntity;
import com.admi.data.repositories.CpcDealerProfileRepository;
import com.admi.data.services.AisKpiService;
import com.admi.data.repositories.AipInventoryRepository;
import com.admi.data.services.CpcKpiService;
import com.admi.data.services.OpcKpiService;
import com.admi.data.services.ProcessService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.tomcat.jni.Local;
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
	AipInventoryRepository aipInventoryRepo;

	@Autowired
	CpcDealerProfileRepository cpcDealerProfileRepo;

	@Autowired
	AisKpiService aisKpiService;

	@Autowired
	ProcessService processService;

	@Autowired
	CpcKpiService cpcService;

	@Autowired
	OpcKpiService opcKpiService;

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
		LocalDate date = aipInventoryRepo.getMaxDate();

		cpcService.runCpcDealers(date);

		return "It did the thing.";
	}

	@ResponseBody
	@GetMapping("/cpc/{dateString}")
	public String processCpcKpiByDate(@PathVariable("dateString") String dateString) {
		LocalDate date = LocalDate.parse(dateString);

		cpcService.runCpcDealers(date);

		return "It did the thing.";
	}

	@ResponseBody
	@GetMapping("/cpc/{dealerId}/{dateString}")
	public String processSingleCpcDealer(@PathVariable("dealerId") Long dealerId,
	                                     @PathVariable("dateString") String dateString) {
		LocalDate date = LocalDate.parse(dateString);
		CpcDealerProfileEntity profile = cpcDealerProfileRepo.findByDealerId(dealerId);

		if (profile != null) {
			cpcService.runSingleCpcDealer(dealerId, date, profile.getPartsList(), profile.getTierList());
			return "CPC Dealer " + dealerId + " ran.";
		} else {
			return "Dealer " + dealerId + " not found.";
		}
	}

	@ResponseBody
	@GetMapping("/opc")
	public String processOpcKpi() {
		opcKpiService.runOpcProcess();

		return "Ran OPC KPI process: transferred OPC 200 data and took KPI performance snapshots for each OPC dealer.";
	}

	@ResponseBody
	@GetMapping("/opc/{paCode}")
	public String processSingleOpcDealer(@PathVariable("paCode") String paCode) {
		opcKpiService.updateOpc200Data(paCode);
		opcKpiService.takePerformanceSnapshot(paCode); //take snapshot AFTER updating

		return "Ran single OPC dealer (P&A Code " + paCode + "): transferred OPC 200 data and took KPI performance snapshot.";
	}

	@ResponseBody
	@GetMapping("/opc/test")
	public String opcTester() {
		opcKpiService.tester();
		return "Ran OPC tester method.";
	}
}
