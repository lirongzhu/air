package com.wing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wing.dao.MessageReceiverDao;
import com.wing.dao.MessageTemplateDao;
import com.wing.service.MessageTemplateService;
import com.wing.bean.MessageReceiver;
import com.wing.bean.MessageTemplate;

@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {

	@Autowired
	MessageTemplateDao templateDao;
	
	@Autowired
	MessageReceiverDao receiverDao;
	
	@Override
	public Page<MessageTemplate> findAll(Specification<MessageTemplate> spe, Pageable pageable) {
		return templateDao.findAll(spe,pageable);
	}

	@Override
	public MessageTemplate findOne(Integer id) {
		return templateDao.findOne(id);
	}

	@Override
	public void save(MessageTemplate messageTemplate) {
		templateDao.save(messageTemplate);
	}

	@Override
	public void delete(Integer id) {
		
		MessageTemplate messageTemplate = templateDao.findOne(id);
		
		List<MessageReceiver> receviers = receiverDao.findByMessageTemplate(messageTemplate);
		receiverDao.delete(receviers);
		
		templateDao.delete(messageTemplate);
	}

	@Override
	public void update(MessageTemplate messageTemplate) {
		templateDao.save(messageTemplate);
	}

}
