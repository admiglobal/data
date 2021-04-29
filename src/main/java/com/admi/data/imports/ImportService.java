package com.admi.data.imports;

import com.admi.data.dto.RRDto;
import com.admi.data.enums.InventoryField;
import com.admi.data.enums.RRField;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ImportService {

	public String importXlsxInventoryFile(MultipartFile multipartFile, Long dealerId, Long dmsId) throws IOException, InvalidFormatException, NoSuchFieldException, IllegalAccessException {
//		FileInputStream inputStream = new FileInputStream(file);
//		File file = getFileFromMultipartFile(multipartFile);

//		OPCPackage pkg = OPCPackage.open(file);
		OPCPackage pkg = OPCPackage.open(multipartFile.getInputStream());

		Workbook workbook = new XSSFWorkbook(pkg);
		Sheet sheet = workbook.getSheetAt(0);

		if (dmsId == 0) {
			importRAndRInventory(sheet, dealerId);
		} else if (dmsId == 1) {

		} else if (dmsId == 2) {

		}

		pkg.close();
		return "";
	}

	private File getFileFromMultipartFile(MultipartFile multipartFile) throws IOException {
		File file = new File("src/main/resources/temp/" + multipartFile.getOriginalFilename());
		multipartFile.transferTo(file);

		return file;
	}

	private void importRAndRInventory(Sheet sheet, Long dealerId) throws NoSuchFieldException, IllegalAccessException {
		Row topRow = sheet.getRow(0);

		List<String> headers = getHeaderList(topRow);

		List<RRDto> inventory = new ArrayList<>();

		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			RRDto rowDTO = new RRDto();

			if (row.getRowNum() != 0) {
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int i = cell.getColumnIndex();

	//				TODO: Move into getHeaderList, create list of Enums instead of finding then for every cell;
					InventoryField inventoryField = RRField.findByColumnName(headers.get(i));

					Field field = RRDto.class.getField(inventoryField.getFieldName());

					if (cell.getCellType() == CellType.NUMERIC) {
						System.out.println(cell.getNumericCellValue());
						if (inventoryField.equals(RRField.EX_VALUE)) {
							field.set(rowDTO, (long) (cell.getNumericCellValue() * 100));
						} else {
							field.set(rowDTO, (long) cell.getNumericCellValue());
						}
					} else if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().equals("")) {
						System.out.println(cell.getStringCellValue());
						field.set(rowDTO, cell.getStringCellValue());
					}

				}
				inventory.add(rowDTO);
			}
		}
		System.out.println(inventory.toString());
	}

	private List<String> getHeaderList(Row row) {
		Iterator<Cell> cellIterator = row.iterator();
		List<String> headers = new ArrayList<>();

		while(cellIterator.hasNext()) {
			String cellValue = cellIterator.next().getStringCellValue();
			headers.add(cellValue);
		}
		return headers;
	}

	public void saveInventoryToDatabase(){

	}

	public void saveFileToDirectory(File file, String fileName, String directory) {

	}


}
