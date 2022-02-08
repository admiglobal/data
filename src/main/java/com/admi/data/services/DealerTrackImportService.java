package com.admi.data.services;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.DealerMasterEntity;
import com.admi.data.entities.DealerTrackInventoryRow;
import com.admi.data.services.DateService;
import com.admi.data.services.ProcessService;
import com.admi.data.services.RimHistoryService;
import com.admi.data.services.ZigService;
import com.admi.data.repositories.AipInventoryRepository;
import com.admi.data.repositories.DealerMasterRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DealerTrackImportService {

	@Autowired
	DateService dateService;

	@Autowired
	ZigService zigService;

	@Autowired
	ProcessService processService;

	@Autowired
	RimHistoryService rimService;

	@Autowired
	AipInventoryRepository inventoryRepo;

	@Autowired
	DealerMasterRepository dealerMasterRepo;

	@Async("asyncExecutor")
	@Scheduled(cron="0 0 6 * * ?")
	public void runAllDtInventoryFiles() throws IOException {
		String filePath = File.separator + File.separator +
				"192.168.250.90" + File.separator +
				"ftp_server" + File.separator +
				"dtrack" + File.separator;
		Set<String> files = getFilesInDirectory(filePath);
		System.out.println(DateService.getTimeString() + ": Importing DealerTrack Dealers");

		for (String fileName : files) {
			String paCode = fileName.substring(0, fileName.indexOf('.'));

			System.out.println("Running DT Dealer: " + paCode);

			DealerMasterEntity dealer = dealerMasterRepo.findFirstByPaCode(paCode);
			String fullPath = filePath + fileName;
			File inventoryFile = new File(fullPath);

			if (dealer != null) {
				runDtInventoryFile(dealer.getDealerId(), new FileInputStream(inventoryFile), paCode);
			}

			String savePath = filePath + "Hold" + File.separator + paCode + "_" + DateService.getFileTimeString() + ".csv";
			Files.move(Path.of(fullPath), Path.of(savePath));
		}

		System.out.println(DateService.getTimeString() + ": Completed DealerTrack Dealers");
	}

	public void runDtInventoryFile(Long dealerId, InputStream file, String paCode) throws IOException {
		List<AipInventoryEntity> aipInventory = importInventoryFile(file, dealerId);

		try {
			inventoryRepo.saveAll(aipInventory);
		} catch (Exception e) {
			for (AipInventoryEntity part : aipInventory) {
				try {
					inventoryRepo.save(part);
				} catch (Exception f) {
					System.out.println("Part not saved - "
							+ "Dealer Id: " + dealerId
							+ " Part Number: " + part.getPartNo()
							+ " Desc: " + part.getDescription());
				}
			}
		}

		zigService.saveAsZig(aipInventory, paCode);
		rimService.addOrUpdateRimParts(dealerId, aipInventory);
		processService.calculateAisKpi(aipInventory);

		file.close();
	}

	public List<AipInventoryEntity> importInventoryFile(InputStream fileStream, Long dealerId) throws IOException {
		DateTimeFormatter dateTimeFormatter = dateService.getFormatterOfPattern("d/M/yyyy");
		Reader reader = new InputStreamReader(fileStream);

		CSVParser parser = CSVFormat.Builder
				.create()
				.setAutoFlush(true)
				.setIgnoreSurroundingSpaces(true)
				.setTrim(true)
				.setAllowMissingColumnNames(true)
				.setHeader()
				.build()
				.parse(reader);
		List<CSVRecord> records = parser.getRecords();
		List<DealerTrackInventoryRow> inventory = new ArrayList<>();

		for (CSVRecord record : records) {
			DealerTrackInventoryRow row = new DealerTrackInventoryRow();

			row.setPartNo(record.get("Part Number"));
			row.setDescription(record.get("Part Description"));
			row.setManufacturer(record.get("Manuf"));
			row.setQoh(record.get("Qty on Hand"));
			row.setCost(record.get("Cost"));
			row.setStatus(record.get("Status"));
			row.setPhaseIn(record.get("Date in Inv/Phase In"));
			row.setLastSaleDate(record.get("Last Sale Date"));
			row.setLastReceiptDate(record.get("Last Received Date"));
			row.setStockingGroup(record.get("Stocking Group"));
			row.setQoo(record.get("On Order"));
			row.setPhaseOut(record.get("Date Phase Out"));
			row.setBin(record.get("Bin Location"));
			row.setMonth1(record.get("Month - 1"));
			row.setMonth2(record.get("Month - 2"));
			row.setMonth3(record.get("Month - 3"));
			row.setMonth4(record.get("Month - 4"));
			row.setMonth5(record.get("Month - 5"));
			row.setMonth6(record.get("Month - 6"));
			row.setMonth7(record.get("Month - 7"));
			row.setMonth8(record.get("Month - 8"));
			row.setMonth9(record.get("Month - 9"));
			row.setMonth10(record.get("Month - 10"));
			row.setMonth11(record.get("Month - 11"));
			row.setMonth12(record.get("Month - 12"));
			row.setStockingCode(record.get("Stock Code"));

			inventory.add(row);
		}

		List<AipInventoryEntity> aipInventory = inventory
				.stream()
				.filter(DealerTrackInventoryRow::includeInInventory)
				.flatMap(p -> Stream.of(p.toAipInventoryEntity(dealerId, dateTimeFormatter, LocalDate.now())))
				.collect(Collectors.toList());
		aipInventory = sortInventory(aipInventory);

		return aipInventory;
	}

	List<AipInventoryEntity> sortInventory(List<AipInventoryEntity> inventory) {
		List<AipInventoryEntity> newInventory = new ArrayList<>();

		for(AipInventoryEntity part : inventory) {

			boolean notCore = !Objects.equals(part.getStatus(), "C");

			boolean quantityOnHand = part.getQoh() > 0;

			boolean lastReceipt = afterThirteenMonths(part.getLastReceipt());
			boolean lastSale = afterThirteenMonths(part.getLastSale());
			boolean entryDate = afterThirteenMonths(part.getEntryDate());

			if (notCore) {
				if (quantityOnHand) {
					newInventory.add(part);
				} else if (lastReceipt || lastSale || entryDate) {
					newInventory.add(part);
				}
			}
		}
		return newInventory;
	}

	private boolean afterThirteenMonths(LocalDate date){
		return date.isAfter(LocalDate.now().minusMonths(13));
	}

	public Set<String> getFilesInDirectory(String directoryPath) throws IOException {
		try (Stream<Path> stream = Files.list(Paths.get(directoryPath))) {
			return stream
					.filter(file -> !Files.isDirectory(file))
					.map(Path::getFileName)
					.map(Path::toString)
					.collect(Collectors.toSet());
		}
	}

}
