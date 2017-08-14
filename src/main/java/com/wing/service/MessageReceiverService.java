package com.wing.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wing.bean.MessageReceiver;
import com.wing.bean.MessageTemplate;
import com.wing.bean.User;

public interface MessageReceiverService {

	public void delete(Integer id);
	
	public MessageReceiver findByUser(User user);
	
	public MessageReceiver findByUserAndMessageTemplate(User user, MessageTemplate messageTemplate);

	public Page<MessageReceiver> findByMessageTemplate(Pageable pageable,MessageTemplate messageTemplate);
	
	public void save(MessageReceiver messageReceiver);

	public void save(List<MessageReceiver> messageReceiver);
}
