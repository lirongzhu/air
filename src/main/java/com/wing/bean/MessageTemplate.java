package com.wing.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * 短信模板
 *
 */
@Entity
@Table(name = "crm_message_template")
public class MessageTemplate {

	@Id
  @TableGenerator(name = "tg_message_template", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_message_template", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_message_template")
	private Integer templateId;
	
	/**
	 * 模板名称
	 */
	@Column(name = "template_name",  length = 256)
	private String templateName;
	
	/**
	 * 短信内容
	 */
	@Column(name = "template_content",  length = 256)
	private String templateContent;
	
	public Integer getTemplateId() {
	
		return templateId;
	}
	
	public void setTemplateId(Integer templateId) {
	
		this.templateId = templateId;
	}
	
	public String getTemplateName() {
	
		return templateName;
	}
	
	public void setTemplateName(String templateName) {
	
		this.templateName = templateName;
	}
	
	public String getTemplateContent() {
	
		return templateContent;
	}
	
	public void setTemplateContent(String templateContent) {
	
		this.templateContent = templateContent;
	}

}
