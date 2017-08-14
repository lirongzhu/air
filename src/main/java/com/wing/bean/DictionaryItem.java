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

import com.wing.common.enums.EnableStatus;

/**
 * @author 杨康
 *	字典子项
 */
@Entity
@Table(name = "crm_dictionary_item")
public class DictionaryItem {
	
	@Id
	@TableGenerator(name = "tg_dictionary_item", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_dictionary_item", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_dictionary_item")
	private Integer itemId;
	
	@Column(name = "full_name", length = 50)
	private String fullName;
	
	@Column(name = "short_name", length = 30)
	private String shortName;
	
	@Column(name = "english_name", length = 30)
	private String engName;
	
	@Column(name = "english_short_name", length = 30)
	private String engShortName;
	
	@Column(name = "item_key", length = 30)
	private String itemKey;
	
	@Column(name = "enable", length = 1)
	private EnableStatus enable;
	
	@Column(name = "item_sort", length = 2)
	private Integer itemSort;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "dictionary_id", referencedColumnName = "dictionaryId")
	private Dictionary dictionary;
	
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
	 * @return the itemKey
	 */
	public String getItemKey() {
		return itemKey;
	}

	/**
	 * @param itemKey the itemKey to set
	 */
	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
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
	 * @return the dictionary
	 */
	public Dictionary getDictionary() {
		return dictionary;
	}

	/**
	 * @param dictionary the dictionary to set
	 */
	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
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
	 * @return the itemId
	 */
	public Integer getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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
	 * @return the itemSort
	 */
	public Integer getItemSort() {
		return itemSort;
	}

	/**
	 * @param itemSort the itemSort to set
	 */
	public void setItemSort(Integer itemSort) {
		this.itemSort = itemSort;
	}
}
