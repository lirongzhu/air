package com.wing.bean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wing.common.constant.Constant;
import com.wing.common.enums.OperationType;

/**
 * @author 杨康
 *	登入日志
 */
@Entity
@Table(name = "crm_operation_log")
public class OperationLog {
	
	public OperationLog(){};
	
	public OperationLog( OperationType operationType, String operationContent ){
		
		this.operationContent = operationContent;
		this.operationType = operationType;
		this.operationUser = Constant.getCurrentUser();
		this.operationDate = new Date();
	};
	
	@Id
    @TableGenerator(name = "tg_operation_log", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_operation_log", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_operation_log")
    private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "operation_date", nullable = false, length = 19)
	private Date operationDate;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "operation_user", referencedColumnName = "id")
	private User operationUser;
	
	@Column(name = "operation_content",  length = 256)
	private String operationContent;
	
	@Column(name = "operation_type",  length = 1)
	private OperationType operationType;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the operationDate
	 */
	public Date getOperationDate() {
		return operationDate;
	}

	/**
	 * @param operationDate the operationDate to set
	 */
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	/**
	 * @return the operationUser
	 */
	public User getOperationUser() {
		return operationUser;
	}

	/**
	 * @param operationUser the operationUser to set
	 */
	public void setOperationUser(User operationUser) {
		this.operationUser = operationUser;
	}

	/**
	 * @return the operationContent
	 */
	public String getOperationContent() {
		return operationContent;
	}

	/**
	 * @param operationContent the operationContent to set
	 */
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}

	/**
	 * @return the operationType
	 */
	public OperationType getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType the operationType to set
	 */
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	
}
