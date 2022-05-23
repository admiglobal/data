package com.admi.data.controllers;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.CpcDealerProfileEntity;
import com.admi.data.entities.DealerMasterEntity;
import com.admi.data.entities.KpiEntity;
import com.admi.data.enums.DmsProvider;
import com.admi.data.repositories.CpcDealerProfileRepository;
import com.admi.data.repositories.DealerMasterRepository;
import com.admi.data.repositories.OpcTsp200DataRepository;
import com.admi.data.services.*;
import com.admi.data.repositories.AipInventoryRepository;
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
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/process")
@Controller
public class ProcessesController {

	@Autowired
	AipInventoryRepository aipInventoryRepo;

	@Autowired
	CpcDealerProfileRepository cpcDealerProfileRepo;

	@Autowired
	OpcTsp200DataRepository opcTsp200DataRepo;

	@Autowired
	DealerMasterRepository dealerRepo;

	@Autowired
	AisKpiService aisKpiService;

	@Autowired
	ProcessService processService;

	@Autowired
	CpcKpiService cpcService;

	@Autowired
	FordDealerKpiService fordDealerKpiService;

	@Autowired
	OpcKpiService opcKpiService;

	@Autowired
	TipOrderDetailService tipService;

//	@ResponseBody
////	@GetMapping("/processTest")
//	public String processTest(Model model) {
//		List<AipInventoryEntity> inventory = aipInventoryRepo.findAllByDealerIdAndDataDate(1969L, LocalDate.of(2021,10,21));
//
//		KpiEntity kpis = aisKpiService.calculateAisKpi(inventory);
//
//		return kpis.toString();
//	}

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
		long startTime = System.currentTimeMillis();

		opcKpiService.runOpcProcess();

		long endTime = System.currentTimeMillis();
		Long l = endTime-startTime;
		double completionTime = (l.doubleValue())/1000;

		return "Ran OPC KPI process: transferred OPC 200 data and took KPI performance snapshots for each OPC dealer." +
				"Time to complete: " + completionTime + " seconds.";
	}

	@ResponseBody
	@GetMapping("/opc/{paCode}")
	public String processSingleOpcDealer(@PathVariable("paCode") String paCode) {
		long startTime = System.currentTimeMillis();
		opcKpiService.updateOpc200Data(paCode);
		opcKpiService.takePerformanceSnapshot(paCode); //take snapshot AFTER updating
		opcTsp200DataRepo.flush();
		long endTime = System.currentTimeMillis();
		Long l = endTime-startTime;
		double completionTime = (l.doubleValue())/1000;

		return "Ran single OPC dealer (P&A Code " + paCode + "): transferred OPC 200 data and took KPI performance snapshot. This took " + completionTime + "s.";
	}

	@ResponseBody
	@GetMapping("/opc/test")
	public String opcTester() {
		opcKpiService.tester();
		return "Ran OPC tester method.";
	}

	@ResponseBody
	@GetMapping("/dpoc")
	public String processInventoryForDpocSite() {

		fordDealerKpiService.runAllFordDealers();

		return "DPOC dealers ran.";
	}

	@ResponseBody
	@GetMapping("/ford/{dateString}")
	public String processUDBForAllFordPrograms(@PathVariable("dateString") String dateString) {
		long startTime = System.currentTimeMillis();

		LocalDate date = LocalDate.parse(dateString);

		cpcService.runCpcDealers(date);
		fordDealerKpiService.runAllFordDealers();
		opcKpiService.runOpcProcess();

		long endTime = System.currentTimeMillis();
		Long l = endTime-startTime;
		double completionTime = (l.doubleValue())/1000;

		return "Ran UDB Data for Ford programs. " +
				"Time to complete: " + completionTime + " seconds.";
	}

	@ResponseBody
	@GetMapping("/aip/{paCode}")
	public String runKpisForAIPDealer(@PathVariable("paCode") String paCode) {
		DealerMasterEntity dealer = dealerRepo.findFirstByPaCodeAndPrimaryManufacturerIdAndTerminationDateIsNull(paCode, 1);

		if (dealer != null) {
			List<AipInventoryEntity> inventory = aipInventoryRepo.findAllByDealerIdAndDataDate(dealer.getDealerId(), LocalDate.now());

			DmsProvider dms = DmsProvider.findDms(dealer.getDmsId());
			KpiEntity kpis = aisKpiService.calculateAisKpi(inventory, dms);

			return kpis.toString();
		}
		return "Not a dealer.";
	}

	@ResponseBody
	@GetMapping("/test/aisStatusList")
	public String testAisStatusListCalc() {
		List<AipInventoryEntity> partList = new ArrayList<>();

//		CDK Test
		AipInventoryEntity part1 = new AipInventoryEntity();

		part1.setPartNo("ABC1");
		part1.setStatus("STOCK");
		part1.setQoh(5);
		part1.setCents(1000);

		partList.add(part1);


		AipInventoryEntity part2 = new AipInventoryEntity();

		part2.setPartNo("ABC2");
		part2.setStatus("NS");
		part2.setQoh(10);
		part2.setCents(1000);

		partList.add(part2);

		AipInventoryEntity part3 = new AipInventoryEntity();

		part3.setPartNo("ABC3");
		part3.setStatus("AP");
		part3.setQoh(15);
		part3.setCents(1000);

		partList.add(part3);

		return "ok.";
	}

	@ResponseBody
	@GetMapping("/tip")
	public String runTipDealer() {

		tipService.runSingleTipDealer();

		return "TIP process has completed successfully.";
	}

}
