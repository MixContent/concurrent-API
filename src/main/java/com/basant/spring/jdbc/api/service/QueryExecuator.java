package com.basant.spring.jdbc.api.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryExecuator implements Callable<List<Map<String, Object>>> {
	int startIndex;
	int endIndex;
	JdbcTemplate template;

	@Override
	public List<Map<String, Object>> call() throws Exception {
		List<Map<String, Object>> results = null;
		System.out.println(template);
		String query = "SELECT * FROM User LIMIT " + startIndex + "," + endIndex;
		System.out.println(query);
		results = template.queryForList(query);

		return results;
	}

	public QueryExecuator(int startIndex, int endIndex, JdbcTemplate template) {
		super();
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.template = template;
	}

}
