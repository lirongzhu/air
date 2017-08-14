package com.wing.utils.orm;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;

public class BaseHibernateTemplateDao {
	
	@Resource(name = "hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
