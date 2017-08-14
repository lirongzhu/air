package com.wing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wing.dao.MessageReceiverDao;
import com.wing.service.MessageReceiverService;
import com.wing.bean.MessageReceiver;
import com.wing.bean.MessageTemplate;
import com.wing.bean.User;


@Service
public class MessageReceiverServiceImpl implements MessageReceiverService {

	@Autowired
	private MessageReceiverDao receiverDao;
	
	@Override
	public void delete(Integer id) {
		receiverDao.delete(id);
	}

	@Override
	public MessageReceiver findByUser(User user) {
		return receiverDao.findByUser(user);
	}
	
	@Override
	public MessageReceiver findByUserAndMessageTemplate(User user, MessageTemplate messageTemplate) {
		return receiverDao.findByUserAndMessageTemplate(user, messageTemplate);
	}

	@Override
	public void save(MessageReceiver messageReceiver) {
		receiverDao.save(messageReceiver);
	}

	@Override
	public void save(List<MessageReceiver> messageReceiver) {
		receiverDao.save(messageReceiver);
	}

	@Override
	public Page<MessageReceiver> findByMessageTemplate(Pageable pageable, MessageTemplate messageTemplate) {
		return receiverDao.findByMessageTemplate(pageable, messageTemplate);
	}

}
