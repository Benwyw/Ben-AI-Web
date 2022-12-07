package com.benwyw.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CsvService {
	public void create() throws IOException {
		final Logger log = LoggerFactory.getLogger(CsvService.class.getName());
		
		// init dummy data
		final String dummyConstHeader = "HEADER";
		final String dummyConstBody = "BODY";
		final String dummyConstFooter = "FOOTER";
		
		List<String> dummyDataHeader = new ArrayList<>();
		List<String> dummyDataBody = new ArrayList<>();
		List<String> dummyDataFooter = new ArrayList<>();
		
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
		
		// generate CSV
		File dummyCsv = new File("src/csv/dummy.csv");
		if (dummyCsv.getParentFile() == null || !dummyCsv.getParentFile().exists())
			dummyCsv.getParentFile().mkdirs();
		dummyCsv.createNewFile();
		FileWriter writer = new FileWriter(dummyCsv, false);
		
		try{
			writer.append(dummyConstHeader);
			for (String line : dummyDataHeader) {
				writer.append(line);
			}
			
			writer.append("\n");
			writer.append(dummyConstBody);
			
			for (String line : dummyDataBody) {
				writer.append(line);
			}
			
			writer.append("\n");
			writer.append(dummyConstFooter);
			
			for (String line : dummyDataFooter) {
				writer.append(line);
			}
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
	}
}
