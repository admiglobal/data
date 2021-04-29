package com.admi.data.imports;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/imports")
public class ImportsController {

	@Autowired
	ImportService importService;

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
	public String submitInventoryFile(@RequestParam("file") MultipartFile file, Model model) throws IOException, InvalidFormatException, NoSuchFieldException, IllegalAccessException {
		System.out.println(file.getContentType());

		String message;
		Long dealerId = 1969L;

		switch (Objects.requireNonNull(file.getContentType())) {
			case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
				message = importService.importXlsxInventoryFile(file, dealerId, 0L);
				break;
			case "application/vnd.ms-excel":
				message = "application/vnd.ms-excel";
				break;
			case "application/octet-stream":
				message = "application/octet-stream";
				break;
			default:
				message = "File format incorrect.";
				break;
		}

		return message;
	}

	private String getPage(String page, String title, Model model) {
		model.addAttribute("title", " // " + title);
		model.addAttribute("page", folder + page);
		return "index";
	}
}
