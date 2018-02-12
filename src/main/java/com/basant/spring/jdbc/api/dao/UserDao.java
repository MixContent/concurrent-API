package com.basant.spring.jdbc.api.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	@Autowired
	private JdbcTemplate template;

	public List<Map<String, Object>> getUsers() {
		String query = "SELECT * FROM User";
		template.setFetchSize(100);
		List<Map<String, Object>> results = template.queryForList(query);
		return results;
	}

	public int getCount() {
		return template.queryForObject("SELECT count(*) FROM User", Integer.class);
	}
}
