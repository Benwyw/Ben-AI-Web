package com.benwyw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.benwyw.payload.ApiResponse;
import com.benwyw.service.CountService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/count")
@Slf4j
public class CountController {
	@Autowired
	CountService countService;

	@GetMapping("/getTest")
	@ApiOperation(value = "Get test")
	public ResponseEntity<ApiResponse<String>> getTest() {
		return ResponseEntity.ok(new ApiResponse<>(countService.getTest()));
	}
}
