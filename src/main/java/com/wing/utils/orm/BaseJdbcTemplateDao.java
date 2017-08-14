package com.wing.utils.orm;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

public class BaseJdbcTemplateDao {
	
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
