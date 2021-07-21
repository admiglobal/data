package com.admi.data.imports;

import com.admi.data.dto.ImportIssue;
import com.admi.data.dto.ImportJob;
import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.KpiEntity;
import com.admi.data.processes.DateService;
import com.admi.data.processes.ProcessService;
import com.admi.data.repositories.ZigRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Controller
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

		model.addAttribute("action", "/imports/inventory");
		return getPage("upload", "Inventory", model);
	}

	@PostMapping("/inventory")
	@ResponseBody
	public String submitInventoryFile(@RequestParam("file") MultipartFile file, Model model)
			throws IOException, InvalidFormatException, NoSuchFieldException, IllegalAccessException {
		String paCode = "20636";
		Long dealerId = 374L;

		ImportJob job = importService.createImportJob(file, dealerId, 0, paCode);
		List<AipInventoryEntity> inventory = importService.importInventoryFile(job);
		KpiEntity kpis = processService.calculateAisKpi(inventory, job.getPaCode());

		System.out.println(DateService.getTimeString() + ": Completed Dealer " + dealerId);

		return kpis.toString();
	}


	@GetMapping("/motorcraft")
	public String selectMotorocraftOrder(Model model) {
		model.addAttribute("action", "/imports/motorcraft");
		return getPage("upload", "Motorcraft", model);
	}

	@GetMapping("/allOrders")
	@ResponseBody
	public String generateAllOrders(Model model) throws IOException {
		processService.runAllOrdersAfter(LocalDateTime.of(2021,5,31,0,0));
		return "Alright, check those orders!";
	}

	@GetMapping("/order/{orderNumber}")
	@ResponseBody
	public String generateSingleOrder(@PathVariable("orderNumber") Long orderNumber,  Model model) throws IOException {

		processService.generateOneOrder(processService.getOrderSet(orderNumber));

		return "One order generated!";
	}

	@PostMapping("motorcraftTest")
	@ResponseBody
	public String submitMotorcraftOrder(@RequestParam("file") MultipartFile file, Model model)
			throws IOException, InvalidFormatException, MessagingException {
		String paCode = "00000";
		Long dealerId = 1969L;

		ImportJob job = importService.createMotorcraftImportJob(file, dealerId, paCode);

		importService.runMotorcraftOrders(job);

		return "File received.";
	}

	@PostMapping("motorcraft")
	@ResponseBody
	public String submitMotorcraftOrder(@RequestBody ImportJob call, Model model)
			throws IOException, InvalidFormatException, MessagingException {

		System.out.println(call.toString());

		byte [] decodedFilePath = Base64.getUrlDecoder().decode(call.getFilePath());
		call.setFilePath(new String (decodedFilePath));

		if (call.getPaCode() == null || call.getFilePath() == null) {
			return "All fields required. There can be no null values";
		} else {
			importService.runMotorcraftOrders(call);

			return "Import queued successfully. Please wait a few minutes to allow us to process this file.";
		}

	}

	@PostMapping("generateMotorcraftOrder")
	@ResponseBody
	public String generateMotorcraftOrder(@RequestBody Long orderNumber, Model model) {
		processService.generateOneOrder(processService.getOrderSet(orderNumber));
		return "Generating Motorcraft Order";
	}

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
