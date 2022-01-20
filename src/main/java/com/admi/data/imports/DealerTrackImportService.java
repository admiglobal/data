package com.admi.data.imports;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.DealerTrackInventoryRow;
import com.admi.data.processes.DateService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DealerTrackImportService {

	@Autowired
	DateService dateService;


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
			boolean quantityOnHand = part.getQoh() > 0;

			boolean lastReceipt = afterThirteenMonths(part.getLastReceipt());
			boolean lastSale = afterThirteenMonths(part.getLastSale());
			boolean entryDate = afterThirteenMonths(part.getEntryDate());

			if (quantityOnHand) {
				newInventory.add(part);
			} else if (lastReceipt || lastSale || entryDate) {
				newInventory.add(part);
			}
		}
		return newInventory;
	}

	private boolean afterThirteenMonths(LocalDate date){
		return date.isAfter(LocalDate.now().minusMonths(13));
	}

}
