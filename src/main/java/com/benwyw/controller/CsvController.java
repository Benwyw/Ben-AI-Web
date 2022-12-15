package com.benwyw.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.benwyw.service.CsvService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/csv")
@Slf4j
public class CsvController {
	@Autowired
	CsvService csvService;

	@GetMapping("/create/csv")
	@ApiOperation(value = "Create CSV")
	public byte[] getTest(HttpServletResponse response) throws IOException {
		response.setContentType("application/csv");      
		response.setHeader("Content-Disposition", "attachment; filename=\"dummy.csv\""); 
		return csvService.create();
	}
	
	@GetMapping("/create/zip")
	@ApiOperation(value = "Create ZIP contained multiple CSV")
	public byte[] zipTest(HttpServletResponse response) throws IOException {
		response.setContentType("application/zip");      
		response.setHeader("Content-Disposition", "attachment; filename=\"dummy.zip\""); 
		return csvService.toZip();
	}
}
