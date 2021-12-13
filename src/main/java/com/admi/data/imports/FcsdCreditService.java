package com.admi.data.imports;

import com.admi.data.entities.DealerMasterEntity;
import com.admi.data.entities.FcsdProgramCreditsEntity;
import com.admi.data.enums.FcsdCreditType;
import com.admi.data.repositories.DealerMasterRepository;
import com.admi.data.repositories.FcsdProgramCreditsRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

@Service
public class FcsdCreditService {

	@Autowired
	FcsdProgramCreditsRepository creditsRepo;

	@Autowired
	DealerMasterRepository dealerRepo;

	public Map<String, List<FcsdProgramCreditsEntity>> importRimDashboardFile(MultipartFile file) throws IOException, InvalidFormatException {
		OPCPackage pkg = OPCPackage.open(file.getInputStream());
		Workbook workbook = new XSSFWorkbook(pkg);
		Map<String, List<FcsdProgramCreditsEntity>> credits = new HashMap<>();
		Iterator<Sheet> sheetIterator = workbook.sheetIterator();

		while(sheetIterator.hasNext()) {
			processFcsdCreditFile(sheetIterator.next(), credits);
		}

		return credits;
	}

	public List<String> combineCreditsAndSave(Map<String, List<FcsdProgramCreditsEntity>> credits) {
		List<FcsdProgramCreditsEntity> allCredits = new ArrayList<>();
		List<String> missingPaCodes = new ArrayList<>();

		for (Map.Entry<String, List<FcsdProgramCreditsEntity>> creditList : credits.entrySet()) {
			Long dealerId = getCreditDealer(creditList.getKey());

			if (dealerId != null) {
				creditList.getValue().forEach(c -> c.setDealerId(dealerId));
				allCredits.addAll(creditList.getValue());
			} else {
				missingPaCodes.add(creditList.getKey());
			}
		}

		creditsRepo.saveAll(allCredits);
		return missingPaCodes;
	}

	private void processFcsdCreditFile(Sheet sheet, Map<String, List<FcsdProgramCreditsEntity>> credits) {
		String dataPointName;
		FcsdCreditType creditType;
		Iterator<Cell> dateRowCellIterator = sheet.getRow(1).cellIterator();
		List<LocalDate> dateHeaders = new ArrayList<>();
		Iterator<Row> rowIterator = sheet.rowIterator();

		try {
			dataPointName = sheet.getRow(0).getCell(1).getStringCellValue().trim();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Issue setting data point name.");
//			System.out.println(e.getStackTrace());
			return;
		}

		try {
			creditType = FcsdCreditType.of(dataPointName);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.out.println("Data point doesn't exist: " + dataPointName);
			return;
		}

//		Creating list of date headers.
		while(dateRowCellIterator.hasNext()) {
			Cell cell = dateRowCellIterator.next();
			if (cell.getCellType() == CellType.STRING) {
				String cellString = cell.getStringCellValue();
				if (!Objects.equals(cellString, "NAME") && cellString != null) {
					dateHeaders.add(getLocalDateFromCellString(cellString));
				}
			}
		}

//		Skip first two rows.
		rowIterator.next();
		rowIterator.next();

		while(rowIterator.hasNext()) {
			processFcsdCreditRow(rowIterator.next(), creditType, dateHeaders, credits);
		}

	}

	private void processFcsdCreditRow(Row row,
	                                 FcsdCreditType creditType,
	                                 List<LocalDate> dateHeaders,
	                                 Map<String, List<FcsdProgramCreditsEntity>> sheetCredits) {
		Iterator<Cell> cellIterator = row.cellIterator();
		Cell paCodeCell = cellIterator.next();
		String paCode;
		List<FcsdProgramCreditsEntity> creditList;

		if (paCodeCell.getCellType() == CellType.STRING) {
			String cellValue = paCodeCell.getStringCellValue();
			int dashIndex = cellValue.lastIndexOf("-");
			paCode = cellValue.substring(dashIndex - 5, dashIndex);
		} else {
			System.out.println("No P&A Code.");
			return;
		}

		creditList = sheetCredits.getOrDefault(paCode, new ArrayList<>());

		for (LocalDate date : dateHeaders) {
			FcsdProgramCreditsEntity credit = creditList.stream()
					.filter(c -> c.getDataDate().equals(date))
					.findFirst()
					.orElse(new FcsdProgramCreditsEntity());
			Cell cell = cellIterator.next();

			credit.setDataDate(date);

			if (cell.getCellType() == CellType.NUMERIC) {
				BigDecimal bd = BigDecimal.valueOf(cell.getNumericCellValue());
				creditType.getSetter().accept(credit, bd.setScale(2, RoundingMode.HALF_UP));
			}

			if (!creditList.contains(credit))
				creditList.add(credit);
		}
		sheetCredits.put(paCode, creditList);
	}

	private LocalDate getLocalDateFromCellString(String cellString) {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				.parseCaseInsensitive()
				.parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
				.appendPattern("MMM yyyy")
				.toFormatter();
		return LocalDate.parse(cellString, formatter);
	}

	private Long getCreditDealer(String paCode) {
		DealerMasterEntity dealer = dealerRepo.findFirstByPaCodeAndPrimaryManufacturerIdAndTerminationDateIsNull(paCode, 1);
		if (dealer != null)
			return dealer.getDealerId();
		else
			return null;
	}

}
