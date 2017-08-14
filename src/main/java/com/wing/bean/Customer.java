package com.wing.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.wing.common.enums.EnableStatus;

/**
 * @author 杨康
 *	客户表
 */
@Entity
@Table(name = "crm_customer")
public class Customer {
	
	/**
	 * id
	 */
	@Id
	@TableGenerator(name = "tg_customer", table = "tg_id_table", pkColumnName = "table_name", 
		pkColumnValue = "crm_customer", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_customer")
	private Integer id;
	
	/**
	 * 登陆名
	 */
	@Column(name = "login_name", length = 50)
	private String loginName;
	
	/**
	 * 密码
	 */
	@Column(name = "password", length = 128)
	private String password;
	
	/**
	 * 真实名称
	 */
	@Column(name = "true_name", length = 50)
	private String trueName;
	
	/**
	 * 联系方式
	 */
	@Column(name = "contact", length =32)
	private String contact;
	
	/**
     * 创建日期
     */
	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_date", nullable = false, length = 19)
    private Date createDate;
	
	@Column(name = "id_card", length =64)
	private String idCard;
	
	@Column(name = "sex")
	private Integer sex;
	
	/**
	 * 状态
	 */
	@Column(name = "state", length =1)
	private EnableStatus state;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the state
	 */
	public EnableStatus getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(EnableStatus state) {
		this.state = state;
	}

	/**
	 * @return the idCard
	 */
	public String getIdCard() {
		return idCard;
	}

	/**
	 * @param idCard the idCard to set
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	/**
	 * @return the sex
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
}
