package com.admi.data.controllers;

import com.admi.data.dto.ImportJob;
import com.admi.data.dto.MotorcraftOrderSet;
import com.admi.data.entities.*;
import com.admi.data.enums.DmsProvider;
import com.admi.data.exceptions.ApiNotSupportedException;
import com.admi.data.repositories.TipEnrollmentsRepository;
import com.admi.data.services.ImportService;
import com.admi.data.services.MixImportService;
import com.admi.data.services.*;
import com.admi.data.repositories.DealerMasterRepository;
import com.admi.data.repositories.MixDealersRepository;
import com.admi.data.repositories.ZigRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The ImportsController is used as one of the two ways to interface with this server. This is the front door
 * for any automated import process controlled by this server.
 *
 * This {@link org.springframework.stereotype.Controller} pulls in many different services to handle most the
 * underlying processes. We try to keep logic outy of this controller and put it in the hands of the relevant
 * service.
 *
 * Some of these methods are accessed directly from a web page, and others are accessed by the AIP Server, or
 * the DDD AIP server as HTTP calls.
 *
 * Programs/Imports handled through here:
 * <ul>
 *     <li>"Plan B" Inventory Import - Dealer uploads inventory file through the AIP</li>
 *     <li>Motorcraft Battery Order Import, Order Generation</li>
 *     <li>Calculation of all AIP data using various sources: DDD API, FTP Files, Dealer Upload</li>
 *     <li>Upload of FCSD Credits for DPOC program</li>
 * </ul>
 *
 *
 * @author kmowers, jbetzig, igrisham
 */

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
	FcsdCreditService fcsdCreditService;

	@Autowired
	MixImportService mixService;

	@Autowired
	CdkImportService cdkService;

	@Autowired
	DealerTrackImportService dtService;

	@Autowired
	RRImportService rrImportService;

	@Autowired
	AipInventoryService aipInventoryService;

	@Autowired
	EmailService emailService;

	@Autowired
	ZigRepository zigRepo;

	@Autowired
	MixDealersRepository mixDealerRepo;

	@Autowired
	DealerMasterRepository dealerMasterRepo;

	String title = "Imports";
	String folder = "imports/";

	@GetMapping("/home")
	public String getImportHome(Model model) {

		return getPage("home", "Home", model);
	}

	@GetMapping("/inventory/{dealerId}")
	public String selectInventoryFile(@PathVariable("dealerId") Long dealerId, Model model) {

		model.addAttribute("action", "/imports/inventory/" + dealerId);
		return getPage("upload", "Inventory", model);
	}

	@PostMapping("/inventory/{dealerId}")
	@ResponseBody
	public String submitInventoryFile(@PathVariable("dealerId") Long dealerId,
	                                  @RequestParam("file") MultipartFile file,
	                                  Model model)
			throws IOException, InvalidFormatException, NoSuchFieldException, IllegalAccessException {
		DealerMasterEntity dealer = dealerMasterRepo.findByDealerId(dealerId);
		String paCode = dealer.getPaCode();

		System.out.println(file.getContentType());

		importService.runAipInventory(file.getInputStream(), dealerId, dealer.getPaCode(), dealer.getDmsId(), file.getContentType(), null);

		System.out.println(DateService.getTimeString() + ": Completed Dealer " + dealerId);

		return paCode + " - Dealer ID: " + dealerId + "Complete";
	}

//	@GetMapping("/UDBInventory")
//	public String selectUdbInventoryFile(Model model) {
//		model.addAttribute("action", "/imports/UDBInventory");
//		return getPage("upload", "Inventory", model);
//	}
//
//	@PostMapping("/UDBInventory")
//	@ResponseBody
//	public String submitUdbInventoryFile(@RequestParam("file") MultipartFile file, Model model) throws IOException, InvalidFormatException {
//		List<AipInventoryEntity> inventory = importService.importUdbInventoryFile(file);
//
//		return "UDB File imported.\n \n Lines: " + inventory.size() + "\n \n " + inventory.subList(0,100);
//	}

	@GetMapping("/DTInventory/{dealerId}")
	public String selectDtInventoryFile(@PathVariable("dealerId") Long dealerId, Model model) {
		model.addAttribute("action", "/imports/DTInventory/" + dealerId);
		return getPage("upload", "Inventory", model);
	}

	@PostMapping("/DTInventory/{dealerId}")
	@ResponseBody
	public String submitDtInventoryFile(@PathVariable("dealerId") Long dealerId,
	                                    @RequestParam("file") MultipartFile file,
	                                    Model model)
			throws IOException, InvalidFormatException {
		String paCode = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf('.'));

		dtService.runDtInventoryFile(dealerId, file.getInputStream(), paCode);
		return "DT File imported for " + paCode;
	}

	@GetMapping("/DTInventoryRun")
	@ResponseBody
	public String runAllDealerTrack() throws IOException {
		dtService.runAllDtInventoryFiles();
		return "DT Ran";
	}

	@GetMapping("/{api}/{dealerId}/{dateString}")
	@ResponseBody
	public String importApiDealer(@PathVariable("api") String apiName,
	                              @PathVariable("dealerId") Long dealerId,
	                              @PathVariable("dateString") String dateString)
			throws ApiNotSupportedException {
		LocalDate date = LocalDate.parse(dateString);
		MixDealersEntity dealer = mixDealerRepo.findByDealerId(dealerId);
		DealerMasterEntity dealerMasterEntity = dealerMasterRepo.findByDealerId(dealerId);

		switch (apiName.toLowerCase()) {
			case "cdk":
				cdkService.importInventory(dealerId, date, dealerMasterEntity.getPaCode());
				break;

			case "mix":
				mixService.importMixDealer(dealerId, date, dealerMasterEntity.getPaCode(), dealer.getMixSource());
				break;

			default:
				throw new ApiNotSupportedException("This API is not currently supported by this import process.");
		}

		return apiName.toUpperCase() + " Dealer " + dealerMasterEntity.getPaCode() + " Processing";
	}

	@GetMapping("/rimDashUpload")
	public String selectDashDataFile(Model model) {
		model.addAttribute("action", "/imports/rimDashUpload");
		return getPage("upload", "Rim Dash", model);
	}

	@PostMapping("/rimDashUpload")
	@ResponseBody
	public String uploadDashDataFile(@RequestParam("file") MultipartFile file, Model model) throws IOException, InvalidFormatException {
		Map<String, List<FcsdProgramCreditsEntity>> credits = fcsdCreditService.importRimDashboardFile(file);
		List<String> missingPaCodes = fcsdCreditService.combineCreditsAndSave(credits);

		return missingPaCodes.toString();
	}

	@GetMapping("/motorcraft")
	public String selectMotorocraftOrder(Model model) {
		model.addAttribute("action", "/imports/motorcraftTest");
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

	/**
	 * The Motorcraft site calls this method when an order is uploaded via excel document
	 */
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

	/**
	 * The Motorcraft site calls this method when an order is generated via the online cart (no excel upload)
	 */
	@PostMapping("generateMotorcraftOrder")
	@ResponseBody
	public String generateMotorcraftOrder(@RequestBody Long orderNumber, Model model) {
		MotorcraftOrderSet orderSet = processService.getOrderSet(orderNumber);

		processService.generateOneOrder(orderSet);

		try {
			emailService.sendMotorcraftOrderEmail(orderNumber);
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

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
			File file = new File(call.getFilePath());
			importService.runAipInventory(
					new FileInputStream(file),
					call.getDealerId(),
					call.getPaCode(),
					call.getDmsId(),
					call.getFileType(),
					call.getEmail());

			return "Import queued successfully. Please wait a few minutes to allow us to process this file. " +
					"You will receive an email notification once your file has finished processing.";
		}

	}

	private String getPage(String page, String title, Model model) {
		model.addAttribute("title", " // " + title);
		model.addAttribute("page", folder + page);
		return "index";
	}
}
