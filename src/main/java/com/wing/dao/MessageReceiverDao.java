package com.wing.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wing.utils.orm.BaseRepository;
import com.wing.bean.MessageReceiver;
import com.wing.bean.MessageTemplate;
import com.wing.bean.User;

public interface MessageReceiverDao extends BaseRepository<MessageReceiver, Integer>{

	public MessageReceiver findByUser(User user); 
	
	public List<MessageReceiver> findByMessageTemplate(MessageTemplate messageTemplate);
	
	public MessageReceiver findByUserAndMessageTemplate(User user, MessageTemplate messageTemplate); 

	public Page<MessageReceiver> findByMessageTemplate(Pageable pageable,MessageTemplate messageTemplate); 
}
