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

import org.springframework.format.annotation.DateTimeFormat;

import com.wing.common.enums.DictionaryType;
import com.wing.common.enums.EnableStatus;

/**
 * @author 杨康
 *	字典表
 */
/**
 * @author lirongzhu
 *
 */
@Entity
@Table(name = "crm_dictionary")
public class Dictionary {
	
	@Id
	@TableGenerator(name = "tg_dictionary", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_dictionary", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_dictionary")
	private Integer dictionaryId;
	
	@Column(name = "full_name", length = 50)
	private String fullName;
	
	@Column(name = "short_name", length = 30)
	private String shortName;
	
	@Column(name = "english_name", length = 30)
	private String engName;
	
	@Column(name = "english_short_name", length = 30)
	private String engShortName;
	
	@Column(name = "dictionary_key", length = 30)
	private String dictionaryKey;
	
	@Column(name = "enable", length = 1)
	private EnableStatus enable;
	
	@Column(name = "dictionary_type", length = 1)
	private DictionaryType dictionaryType;
	
	/**
     * 创建日期
     */
	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_date", nullable = false, length = 19)
    private Date createDate;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "create_user", referencedColumnName = "id")
	private User createUser;

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the engName
	 */
	public String getEngName() {
		return engName;
	}

	/**
	 * @param engName the engName to set
	 */
	public void setEngName(String engName) {
		this.engName = engName;
	}

	/**
	 * @return the dictionaryKey
	 */
	public String getDictionaryKey() {
		return dictionaryKey;
	}

	/**
	 * @param dictionaryKey the dictionaryKey to set
	 */
	public void setDictionaryKey(String dictionaryKey) {
		this.dictionaryKey = dictionaryKey;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the engShortName
	 */
	public String getEngShortName() {
		return engShortName;
	}

	/**
	 * @param engShortName the engShortName to set
	 */
	public void setEngShortName(String engShortName) {
		this.engShortName = engShortName;
	}

	/**
	 * @return the createUser
	 */
	public User getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the dictionaryId
	 */
	public Integer getDictionaryId() {
		return dictionaryId;
	}

	/**
	 * @param dictionaryId the dictionaryId to set
	 */
	public void setDictionaryId(Integer dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	/**
	 * @return the enable
	 */
	public EnableStatus getEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(EnableStatus enable) {
		this.enable = enable;
	}

	/**
	 * @return the dictionaryType
	 */
	public DictionaryType getDictionaryType() {
		return dictionaryType;
	}

	/**
	 * @param dictionaryType the dictionaryType to set
	 */
	public void setDictionaryType(DictionaryType dictionaryType) {
		this.dictionaryType = dictionaryType;
	}
}
