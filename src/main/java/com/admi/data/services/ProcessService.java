package com.admi.data.services;

import com.admi.data.dto.MotorcraftOrderSet;
import com.admi.data.entities.*;
import com.admi.data.enums.DmsProvider;
import com.admi.data.repositories.McOrdersContentRepository;
import com.admi.data.repositories.McOrdersRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessService {

	@Autowired
	AisKpiService aisKpiService;

	@Autowired
	McOrdersRepository ordersRepo;

	@Autowired
	McOrdersContentRepository ordersContentRepo;

	@Value("${pDrive.address}")
	String pDriveAddress;


	public KpiEntity calculateAisKpi(List<AipInventoryEntity> inventory, DmsProvider dms) {
//		return aisKpiService.calculateAisKpi(inventory);
		return aisKpiService.calculateAisKpi(inventory, dms);
	}

	public void generateDowOrders(List<MotorcraftOrderSet> orders) throws IOException {
		for (MotorcraftOrderSet order : orders) {
			generateOneOrder(order);
		}
		System.out.println("Files Generated.");
	}

	/**
	 * Creates a DOW Order spreadsheet for this order and saves it to the P drive.
	 */
	public void generateOneOrder(MotorcraftOrderSet order) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Sheet1");

		CreationHelper helper = workbook.getCreationHelper();
		Row firstRow = getHeaderRow(sheet.createRow(0));

		int i = 1;

		for (DowOrderRow part : order.getOrderContentAsDowOrder()) {

//			System.out.println(part.toString());

			Row row = sheet.createRow(i);
			Cell paCodeCell = row.createCell(0);
			Cell orderTypeCell = row.createCell(1);
			Cell partNumberCell = row.createCell(2);
			Cell termsCodeCell = row.createCell(3);
			Cell quantityCell = row.createCell(4);
			Cell poNumberCell = row.createCell(5);

			paCodeCell.setCellValue(part.getPaCode());
			orderTypeCell.setCellValue(part.getOrderType());
			partNumberCell.setCellValue(part.getPartNumber());
			termsCodeCell.setCellValue(part.getTermsCode());
			quantityCell.setCellValue(part.getQuantity());
			poNumberCell.setCellValue(part.getPoNumber());

			i++;
		}

		Row lastRow = sheet.createRow(sheet.getLastRowNum() + 1);

		int rowCount = sheet.getLastRowNum() + 1;

		lastRow.createCell(0).setCellValue("TOTAL LINES=" + rowCount);


		try {
			String filePath = File.separator + File.separator +
					pDriveAddress + File.separator +
					"Public" + File.separator +
					"Development" + File.separator +
					"Motorcraft_Orders" + File.separator +
					"DOW Orders" + File.separator
					+ order.getOrder().getOrderDate() + File.separator;

			Files.createDirectories(Paths.get(filePath));

//			String fileName = order.getOrder().getPaCode() + "_" + DateService.getFileTimeString(order.getOrder().getPlaced()) + ".xlsx";
			//updated format to use ADMI Order Number: always unique filename
			String fileName = order.getOrder().getPaCode() + "_" + order.getOrder().getPoNumber() + "_" + order.getOrder().getOrderNumber() + ".xlsx";

			OutputStream fileOut = new FileOutputStream(filePath + fileName, false);
			workbook.write(fileOut);
			workbook.close();
			fileOut.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes the Motorcraft order file from the P: drive that corresponds to this order number.
	 * Files can have one of three formats. This function searches for all three:
	 *         1) P&A_PlacedDatetime.xlsx  **Datetime of format yyyy-MM-dd_HH-nn-ss
	 *         2) P&A_AdmiOrderNumber.xlsx
	 *         3) P&A_PoNumber_AdmiOrderNumber.xlsx
	 * Note that formats 1 and 2 are deprecated file formats.
	 * @return true if successful, false if otherwise
	 */
	public Boolean deleteMotorcraftOrderFile(String orderNumber){
		McOrdersEntity order = ordersRepo.findByOrderNumber(orderNumber);

		String filePathRoot = File.separator + File.separator +
				pDriveAddress + File.separator +
				"Public" + File.separator +
				"Development" + File.separator +
				"Motorcraft_Orders" + File.separator +
				"DOW Orders" + File.separator +
				order.getOrderDate() + File.separator +
				order.getPaCode() + "_";
		String[] orderFileFormats = {
				filePathRoot + DateService.getFileTimeString(order.getPlaced()) + ".xlsx",
				filePathRoot + order.getOrderNumber() + ".xlsx",
				filePathRoot + order.getPoNumber() + "_" + order.getOrderNumber() + ".xlsx"
		};

		boolean fileNotFound = true;

		//try each filename format
		for(String orderFileFormat : orderFileFormats){
			File file = new File(orderFileFormat);

			if(file.exists()) { fileNotFound = false; }

			try {
				if(file.delete()){
					System.out.println("Successfully deleted file from P: drive for Motorcraft order #" + orderNumber + ": " + orderFileFormat);
					return true;
				}

			} catch (SecurityException se) {
				se.printStackTrace();
				if(file.exists()){
					System.out.println("Unable to delete file from P: drive for Motorcraft order #" + orderNumber);
					return false;
				}
			}
		}

		if(fileNotFound){
			System.out.println("Motorcraft file for order #" + orderNumber + " was not found; assumed already deleted.");
			return true;
		}

		System.out.println("Unable to delete file from P: drive for Motorcraft order #" + orderNumber);
		return false;
	}

	public MotorcraftOrderSet getOrderSet(Long orderNumber) {
		McOrdersEntity order = ordersRepo.findByOrderNumber(orderNumber.toString());
		List<McOrdersContentEntity> orderContent = ordersContentRepo.findAllByPaCodeAndOrderNumber(order.getPaCode(), order.getOrderNumber());

		return new MotorcraftOrderSet(order, orderContent, null);
	}

	public List<MotorcraftOrderSet> getAllOrdersAfterDate(LocalDateTime date) {
		List<MotorcraftOrderSet> orderSets = new ArrayList<>();

		List<McOrdersEntity> orders = ordersRepo.findAllByPlacedAfterAndPlacedIsNotNull(date);

		for (McOrdersEntity order : orders) {
			List<McOrdersContentEntity> orderContent = ordersContentRepo.findAllByPaCodeAndOrderNumber(order.getPaCode(), order.getOrderNumber());
			orderSets.add(new MotorcraftOrderSet(order, orderContent, null));
		}
		return orderSets;
	}

	public void runAllOrdersAfter(LocalDateTime date) throws IOException {
		generateDowOrders(getAllOrdersAfterDate(date));
	}

	private Row getHeaderRow(Row row) {
		row.createCell(0).setCellValue("P&A CODE");
		row.createCell(1).setCellValue("ORDER TYPE");
		row.createCell(2).setCellValue("PART NUMBER");
		row.createCell(3).setCellValue("TERMS CODE");
		row.createCell(4).setCellValue("QUANTITY");
		row.createCell(5).setCellValue("PO NUMBER");

		return row;
	}

	/**
	 * Given a certain number of milliseconds, returns a String-formatted time (h:mm:ss)
	 * @param milliseconds A time in milliseconds
	 * @return This amount of time formatted as a timestamp string (h:mm:ss)
	 */
	public String timestamp(long milliseconds){
		int seconds = (int) (milliseconds / 1000) % 60 ;
		int minutes = (int) ((milliseconds / (1000*60)) % 60);
		int hours   = (int) ((milliseconds / (1000*60*60)) % 24);

		String timestamp = hours + ":";
		timestamp += (minutes < 10) ? ("0" + minutes) : minutes;
		timestamp += ":";
		timestamp += (seconds < 10) ? ("0" + seconds) : seconds;

		return timestamp;
	}

}
