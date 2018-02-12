package com.basant.spring.jdbc.api.service;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basant.spring.jdbc.api.dao.UserDao;

@Service
public class ExportExcelService {
	@Autowired
	private UserDao dao;

	public void generateExcel(HttpServletResponse response) throws Exception {
		Map<Integer, Map<String, Object>> empinfo = new TreeMap<>();

		// Set<String> excelHeaders = new LinkedHashSet<>();
		int count = 0;
		for (Map<String, Object> recordList : dao.getUsers()) {
			count++;
			empinfo.put(count, recordList);
		}
		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet(" Employee Info ");

		// Create row object
		XSSFRow row;

		// Iterate over data and write to sheet
		Set<Integer> keyid = empinfo.keySet();
		int rowid = 0;

		for (int key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Map<String, Object> records = empinfo.get(key);
			int cellid = 0;

			for (String keySet : records.keySet()) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue(String.valueOf(records.get(keySet)));
			}
		}

		// Write the workbook in file system

		System.out.println("Writesheet.xlsx written successfully");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + "batch" + ".xlsx");
		response.setHeader("Access-Control-Expose-Headers", "X-ExcelFilename");
		response.setHeader("X-ExcelFilename", "batch" + ".xlsx");
		// write workbook to outputstream
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
		out.close();

	}
}
