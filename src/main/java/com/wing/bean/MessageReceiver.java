package com.wing.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * TODO: 填入Class说明.
 * 
 * <pre>
 * 历史纪录：
 * 2017年7月11日 杨康
 * 	新建文件
 * </pre>
 * 短信接收者
 */
@Entity
@Table(name = "crm_message_receiver")
public class MessageReceiver {

	@Id
  @TableGenerator(name = "tg_message_receiver", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_message_receiver", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_message_receiver")
	private Integer receiverId;

	/**
	 * 短信模板ID
	 */
	@OneToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "template_id", referencedColumnName = "templateId", updatable=false)
	private MessageTemplate messageTemplate;

	/**
	 * 管理者id
	 */
	@OneToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "user_id", referencedColumnName = "id", updatable=false)
	private User user;
	
	public Integer getReceiverId() {
	
		return receiverId;
	}
	
	public void setReceiverId(Integer receiverId) {
	
		this.receiverId = receiverId;
	}
	
	public MessageTemplate getMessageTemplate() {
	
		return messageTemplate;
	}

	public void setMessageTemplate(MessageTemplate messageTemplate) {
	
		this.messageTemplate = messageTemplate;
	}
	
	public User getUser() {
	
		return user;
	}
	
	public void setUser(User user) {
	
		this.user = user;
	}

}
