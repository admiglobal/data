package com.admi.data.imports;

import com.admi.data.dto.ImportJob;
import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.KpiEntity;
import com.admi.data.processes.DateService;
import com.admi.data.processes.ProcessService;
import com.admi.data.repositories.ZigRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/imports")
public class ImportsController {

	@Autowired
	ImportService importService;

	@Autowired
	ProcessService processService;

	@Autowired
	DateService dateService;

	@Autowired
	ZigRepository zigRepo;

	String title = "Imports";
	String folder = "/imports/";

	@GetMapping("/home")
	public String getImportHome(Model model) {

		return getPage("home", "Home", model);
	}

	@GetMapping("/inventory")
	public String selectInventoryFile(Model model) {

		return getPage("inventory", "Inventory", model);
	}

	@PostMapping("/inventory")
	@ResponseBody
	public String submitInventoryFile(@RequestParam("file") MultipartFile file, Model model)
			throws IOException, InvalidFormatException, NoSuchFieldException, IllegalAccessException {
		String paCode = "08616";
		Long dealerId = 1518L;

		ImportJob job = importService.createInventoryImportJob(file, dealerId, 0, paCode);
		List<AipInventoryEntity> inventory = importService.importInventoryFile(job);
		KpiEntity kpis = processService.calculateAisKpi(inventory, job.getPaCode());

		System.out.println(DateService.getTimeString() + ": Completed Dealer " + dealerId);

		return kpis.toString();
	}

//	public String submitAipFile(@RequestBody String call, Model model)

	@PostMapping(value = "/aip")
	@ResponseBody
	public String submitAipFile(@RequestBody ImportJob call, Model model)
			throws IOException, InvalidFormatException, NoSuchFieldException, IllegalAccessException {
		System.out.println(call.toString());

		byte [] decodedFilePath = Base64.getUrlDecoder().decode(call.getFilePath());
		call.setFilePath(new String (decodedFilePath));

		if (call.getPaCode() == null || call.getFilePath() == null || call.getDealerId() == null) {
			return "All fields required. There can be no null values";
		} else {
			importService.runAipInventory(call);

			return "Import queued successfully. Please wait a few minutes to allow us to process this file.";
		}

	}


	private String getPage(String page, String title, Model model) {
		model.addAttribute("title", " // " + title);
		model.addAttribute("page", folder + page);
		return "index";
	}
}
