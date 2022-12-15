package com.benwyw.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.CSVWriter;

import lombok.extern.slf4j.Slf4j;
import nonapi.io.github.classgraph.utils.StringUtils;

@Service
@Transactional
@Slf4j
public class ExcelService {
	
	public byte[] createExcel(List<List<String>> dummyDataCombined) throws IOException {
		List<String> headerList = dummyDataCombined.get(0);
		List<String> bodyList = dummyDataCombined.get(1);
		List<String> footerList = dummyDataCombined.get(2);
		
		// Workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		// Spreadsheet
		XSSFSheet sheet = workbook.createSheet("Tab 1");
		
		// Row
		XSSFRow row;
		
		// Row (Header)
		row = sheet.createRow(0);
		
		for (int index = 0; index < headerList.size(); index++) {
			Cell cell = row.createCell(index);
			cell.setCellValue(headerList.get(index));
		}
		
		// Row ^ v
		for (int i = 0; i < footerList.size(); i++) {
			row = sheet.createRow(i+1);
			
			// Row <-->
			for (int j = 0; j < dummyDataCombined.size()-1; j++) {
				Cell cell = row.createCell(j);
				
				if (cell.getColumnIndex() == 0) {
					cell.setCellValue(bodyList.get(i));
				}
				
				else if (cell.getColumnIndex() == 1) {
					cell.setCellValue(footerList.get(i));
				}
			}
		}
		
		File dummyExcel = new File("src/excel/dummy.xls");
		if (dummyExcel.getParentFile() == null || !dummyExcel.getParentFile().exists())
			dummyExcel.getParentFile().mkdirs();
		FileOutputStream out = new FileOutputStream(dummyExcel);
		workbook.write(out);
		workbook.close();
		out.close();
		
		FileInputStream fl = new FileInputStream(dummyExcel);
        byte[] arr = new byte[(int)dummyExcel.length()];
        fl.read(arr);
        fl.close();
		
		return arr;
		
	}
	
	public static List<List<String>> createDummyDataLtStr() {
		// init dummy data
		final String dummyConstHeader = "HEADER";
		final String dummyConstBody = "BODY";
		final String dummyConstFooter = "FOOTER";
		
		List<String> dummyDataHeader = new ArrayList<>();
		List<String> dummyDataBody = new ArrayList<>();
		List<String> dummyDataFooter = new ArrayList<>();
		
		List<List<String>> dummyDataCombined = new ArrayList<>();
		
		dummyDataHeader.add("Header1");
		dummyDataHeader.add("Header2");
		dummyDataHeader.add("Header3");
		
		dummyDataBody.add("Body1");
		dummyDataBody.add("Body2");
		dummyDataBody.add("Body3");
		dummyDataBody.add("Body4");
		dummyDataBody.add("Body5");
		
		dummyDataFooter.add("Footer1");
		dummyDataFooter.add("Footer2");
		dummyDataFooter.add("Footer3");
		dummyDataFooter.add("Footer4");
		dummyDataFooter.add("Footer5");
		
		dummyDataCombined.add(dummyDataHeader);
		dummyDataCombined.add(dummyDataBody);
		dummyDataCombined.add(dummyDataFooter);
		
		return dummyDataCombined;
	}
	
//	public static List<String[]> createDummyData() {
//		// init dummy data
//		final String dummyConstHeader = "HEADER";
//		final String dummyConstBody = "BODY";
//		final String dummyConstFooter = "FOOTER";
//		
//		List<String> dummyDataHeader = new ArrayList<>();
//		List<String> dummyDataBody = new ArrayList<>();
//		List<String> dummyDataFooter = new ArrayList<>();
//		
//		List<String[]> dummyDataCombined = new ArrayList<>();
//		
//		dummyDataHeader.add("Header1");
//		dummyDataHeader.add("Header2");
//		dummyDataHeader.add("Header3");
//		
//		dummyDataBody.add("Body1");
//		dummyDataBody.add("Body2");
//		dummyDataBody.add("Body3");
//		dummyDataBody.add("Body4");
//		
//		dummyDataFooter.add("Footer1");
//		dummyDataFooter.add("Footer2");
//		dummyDataFooter.add("Footer3");
//		dummyDataFooter.add("Footer4");
//		dummyDataFooter.add("Footer5");
//		
//		dummyDataCombined.add(dummyDataHeader.toArray(String[]::new));
//		dummyDataCombined.add(dummyDataBody.toArray(String[]::new));
//		dummyDataCombined.add(dummyDataFooter.toArray(String[]::new));
//		
//		return dummyDataCombined;
//	}
//	
//	public File createExcel(String fileName) throws IOException {
//		final Logger log = LoggerFactory.getLogger(ExcelService.class.getName());
//		
//		List<String[]> dummyDataCombined = createDummyData();
//		
//		// generate CSV
//		File dummyExcel = new File(String.format("src/excel/%s.xls",fileName));
//		if (dummyExcel.getParentFile() == null || !dummyExcel.getParentFile().exists())
//			dummyExcel.getParentFile().mkdirs();
//		dummyExcel.createNewFile();
//		FileWriter fileWriter = new FileWriter(dummyExcel, false);
//		CSVWriter writer = new CSVWriter(fileWriter);
//		
//		try{
//	        writer.writeAll(dummyDataCombined);
//		}
//
//		catch(Exception e){
//			log.error(String.valueOf(e));
//			log.error("Exception occured durign CSV generation.");
//		}
//
//		finally{
//		   if (writer!=null){
//		      writer.flush();
//		      writer.close();
//		   }
//		}
//		log.info("Completed");
//		
//		return dummyExcel;
//	}
//	
//	public byte[] toZip() throws IOException {
//		List<File> files = new ArrayList<>();
//		for (int i = 0; i < 3; i++) {
//			files.add(createExcel(String.format("dummyExcel-%s",i)));
//		}
//		
//		File dummyExcelZip = new File("src/excel/dummy.zip");
//		if (dummyExcelZip.getParentFile() == null || !dummyExcelZip.getParentFile().exists())
//			dummyExcelZip.getParentFile().mkdirs();
//		dummyExcelZip.createNewFile();
//		
//		FileOutputStream fos = new FileOutputStream(dummyExcelZip);
//		ZipOutputStream zipOut = new ZipOutputStream(fos);
//		
//		for (File file : files) {
//			FileInputStream fis = new FileInputStream(file);
//			ZipEntry zipEntry = new ZipEntry(file.getName());
//			zipOut.putNextEntry(zipEntry);
//			byte[] bytes = new byte[1024];
//			int length;
//			while ((length = fis.read(bytes)) >= 0) {
//				zipOut.write(bytes, 0, length);
//			}
//			fis.close();
//		}
//		zipOut.close();
//		fos.close();
//		
//		FileInputStream fl = new FileInputStream(dummyExcelZip);
//        byte[] arr = new byte[(int)dummyExcelZip.length()];
//        fl.read(arr);
//        fl.close();
//		
//		return arr;
//	}
	
	public byte[] create() throws IOException {
		final Logger log = LoggerFactory.getLogger(ExcelService.class.getName());
		
		List<List<String>> dummyDataCombined = createDummyDataLtStr();
		
		// generate Excel	
		return createExcel(dummyDataCombined);
	}
}
