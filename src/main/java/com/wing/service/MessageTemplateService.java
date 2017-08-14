package com.wing.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.wing.bean.MessageTemplate;

public interface MessageTemplateService {

	public Page<MessageTemplate> findAll(Specification<MessageTemplate> spe,Pageable pageable);
	
	public MessageTemplate findOne(Integer id);
	
	public void save(MessageTemplate messageTemplate);
	
	public void delete(Integer id);
	
	public void update(MessageTemplate messageTemplate);
}
