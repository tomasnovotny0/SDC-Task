package sdc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Undefined file path");
		}
		String path = args[0];
		File file = new File(path);
		if (!file.exists()) {
			throw new IllegalArgumentException("Invalid file path");
		}
		try {
			readDataFile(file);
		} catch (IOException exc) {
			System.err.println("Error: " + exc.getMessage());
		}
		
	}
	
	/**
	 * Attempts to read excel sheet from specific file
	 * @param file File to read
	 * @throws IOException When file cannot be read
	 */
	private static void readDataFile(File file) throws IOException {
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int rowIndex = 0;
		XSSFRow row = sheet.getRow(rowIndex);
		int columnDataIndex = findColumnWithData(row);
		if (columnDataIndex < 0)
			throw new IllegalStateException("Couldn't locate data");
		while ((row = sheet.getRow(++rowIndex)) != null) {
			checkCellForPrime(row.getCell(columnDataIndex));
		}
	}
	
	/**
	 * Find column which contains data
	 * @param row Starting row
	 * @return Column index with data or -1 when no data are found
	 */
	private static int findColumnWithData(XSSFRow row) {
		for (Cell cell : row) {
			if (cell == null) continue;
			String data = cell.getStringCellValue();
			if (data.equalsIgnoreCase("data")) {
				return cell.getColumnIndex();
			}
		}
		return -1;
	}
	
	/**
	 * Checks if cell value is a prime number
	 * @param cell Cell which is being checked
	 */
	private static void checkCellForPrime(Cell cell) {
		String value = cell.getStringCellValue();
		int number;
		try {
			number = Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			return;
		}
		if (number < 0)
			return;
		if (PrimeNumberHelper.isPrimeNumber(number)) {
			System.out.println(number);
		}
	}
}
