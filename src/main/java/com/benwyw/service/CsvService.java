package com.benwyw.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
public class CsvService {
	
	public static List<String[]> createDummyData() {
		// init dummy data
		final String dummyConstHeader = "HEADER";
		final String dummyConstBody = "BODY";
		final String dummyConstFooter = "FOOTER";
		
		List<String> dummyDataHeader = new ArrayList<>();
		List<String> dummyDataBody = new ArrayList<>();
		List<String> dummyDataFooter = new ArrayList<>();
		
		List<String[]> dummyDataCombined = new ArrayList<>();
		
		dummyDataHeader.add("Header1");
		dummyDataHeader.add("Header2");
		dummyDataHeader.add("Header3");
		
		dummyDataBody.add("Body1");
		dummyDataBody.add("Body2");
		dummyDataBody.add("Body3");
		dummyDataBody.add("Body4");
		
		dummyDataFooter.add("Footer1");
		dummyDataFooter.add("Footer2");
		dummyDataFooter.add("Footer3");
		dummyDataFooter.add("Footer4");
		dummyDataFooter.add("Footer5");
		
		dummyDataCombined.add(dummyDataHeader.toArray(String[]::new));
		dummyDataCombined.add(dummyDataBody.toArray(String[]::new));
		dummyDataCombined.add(dummyDataFooter.toArray(String[]::new));
		
		return dummyDataCombined;
	}
	
	public File createCsv(String fileName) throws IOException {
		final Logger log = LoggerFactory.getLogger(CsvService.class.getName());
		
		List<String[]> dummyDataCombined = createDummyData();
		
		// generate CSV
		File dummyCsv = new File(String.format("src/csv/%s.csv",fileName));
		if (dummyCsv.getParentFile() == null || !dummyCsv.getParentFile().exists())
			dummyCsv.getParentFile().mkdirs();
		dummyCsv.createNewFile();
		FileWriter fileWriter = new FileWriter(dummyCsv, false);
		CSVWriter writer = new CSVWriter(fileWriter);
		
		try{
	        writer.writeAll(dummyDataCombined);
		}

		catch(Exception e){
			log.error(String.valueOf(e));
			log.error("Exception occured durign CSV generation.");
		}

		finally{
		   if (writer!=null){
		      writer.flush();
		      writer.close();
		   }
		}
		log.info("Completed");
		
		return dummyCsv;
	}
	
	public byte[] toZip() throws IOException {
		List<File> files = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			files.add(createCsv(String.format("dummyCsv-%s",i)));
		}
		
		File dummyCsvZip = new File("src/csv/dummy.zip");
		if (dummyCsvZip.getParentFile() == null || !dummyCsvZip.getParentFile().exists())
			dummyCsvZip.getParentFile().mkdirs();
		dummyCsvZip.createNewFile();
		
		FileOutputStream fos = new FileOutputStream(dummyCsvZip);
		ZipOutputStream zipOut = new ZipOutputStream(fos);
		
		for (File file : files) {
			FileInputStream fis = new FileInputStream(file);
			ZipEntry zipEntry = new ZipEntry(file.getName());
			zipOut.putNextEntry(zipEntry);
			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zipOut.write(bytes, 0, length);
			}
			fis.close();
		}
		zipOut.close();
		fos.close();
		
		FileInputStream fl = new FileInputStream(dummyCsvZip);
        byte[] arr = new byte[(int)dummyCsvZip.length()];
        fl.read(arr);
        fl.close();
		
		return arr;
	}
	
	public byte[] create() throws IOException {
		final Logger log = LoggerFactory.getLogger(CsvService.class.getName());
		
		List<String[]> dummyDataCombined = createDummyData();
		
		// generate CSV
		File dummyCsv = new File("src/csv/dummy.csv");
		if (dummyCsv.getParentFile() == null || !dummyCsv.getParentFile().exists())
			dummyCsv.getParentFile().mkdirs();
		dummyCsv.createNewFile();
		FileWriter fileWriter = new FileWriter(dummyCsv, false);
		CSVWriter writer = new CSVWriter(fileWriter);
		
		try{
	        writer.writeAll(dummyDataCombined);
		}

		catch(Exception e){
			log.error(String.valueOf(e));
			log.error("Exception occured durign CSV generation.");
		}

		finally{
		   if (writer!=null){
		      writer.flush();
		      writer.close();
		   }
		}
		log.info("Completed");
		
		FileInputStream fl = new FileInputStream(dummyCsv);
        byte[] arr = new byte[(int)dummyCsv.length()];
        fl.read(arr);
        fl.close();
		
		return arr;
	}
}
