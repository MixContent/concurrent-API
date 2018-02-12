package com.basant.spring.jdbc.api.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basant.spring.jdbc.api.dao.UserDao;
import com.basant.spring.jdbc.api.service.QueryExecuator;

@RestController
public class ExcelDownloadController {
	@Autowired
	private UserDao dao;
	/*
	 * @Autowired private ExportExcelService service;
	 */
	@Autowired
	private JdbcTemplate template;

	Logger logger = LoggerFactory.getLogger(ExcelDownloadController.class);

	@GetMapping("/getUsers")
	public ResponseEntity<Object> getUsers(HttpServletResponse response) throws Exception {
		// service.generateExcel(response);
		QueryExecuator queryExecuator = null;
		ExecutorService service = Executors.newFixedThreadPool(10);
		List<List<Map<String, Object>>> recordList = new CopyOnWriteArrayList<>();
		/*
		 * long startTime = System.currentTimeMillis();
		 * logger.info("Start time : {}", startTime);
		 */
		for (int i = 0; i <= dao.getCount(); i = i + 10000) {
			queryExecuator = new QueryExecuator(i, i + 10000, template);
			Future<List<Map<String, Object>>> future = service.submit(queryExecuator);
			recordList.add(future.get());
		}
		/*
		 * long endTime = System.currentTimeMillis();
		 * logger.info("End time : {}", endTime); logger.info("Total time : {}",
		 * ((endTime - startTime) / 100)); service.shutdown();
		 */
		return new ResponseEntity<Object>(recordList, HttpStatus.OK);
	}

	@GetMapping("/getRecordCount")
	public int getRecordCount() {
		return dao.getCount();
	}

	@GetMapping("/getUser")
	public ResponseEntity<Object> getUser() {
		return new ResponseEntity<Object>(dao.getUsers(), HttpStatus.OK);
	}
}
