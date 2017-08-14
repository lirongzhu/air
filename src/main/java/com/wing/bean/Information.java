package com.wing.bean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.wing.common.constant.Constant;
import com.wing.common.enums.EnableStatus;

/**
 * @author 杨康
 * 资讯管理
 */
@Entity
@Table(name = "crm_information")
public class Information {
	
	@Id
    @TableGenerator(name = "tg_information", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_information", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_information")
	private Integer informationId;
	
	/**
	 * 标题
	 */
	@Column(name = "information_title", length = 64)
	private String informationTitle;
	
	/**
	 * 封面
	 */
	@Column(name = "pic_path", length = 256)
	private String picPath;
	
	/**
	 * 封面小图
	 */
	@Column(name = "pic_thumbnail_path", length = 256)
	private String picThumbnailPath;
	
	/**
	 * 状态
	 */
	@Column(name = "information_state", length =1)
	private EnableStatus informationState;
	
	/**
	 * 类型
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "information_type", referencedColumnName = "itemId")
	private DictionaryItem informationType;
	
	/**
	 * 内容
	 */
	@Lob
	private String informationContent;
	
	
	/**
	 * 是否是 0主推 1次级推送 2 三级推送 3轮播图
	 */
	@Column(name = "hot_type", length =1)
	private Integer hotType;
	
	/**
     * 创建日期
     */
	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_date", nullable = false, length = 19)
    private Date createDate;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "create_user", referencedColumnName = "id")
	private User createUser;

	/**
	 * @return the informationTitle
	 */
	public String getInformationTitle() {
		return informationTitle;
	}

	/**
	 * @param informationTitle the informationTitle to set
	 */
	public void setInformationTitle(String informationTitle) {
		this.informationTitle = informationTitle;
	}

	/**
	 * @return the picPath
	 */
	public String getPicPath() {
		return picPath;
	}

	/**
	 * @param picPath the picPath to set
	 */
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	/**
	 * @return the informationContent
	 */
	public String getInformationContent() {
		return informationContent;
	}

	/**
	 * @param informationContent the informationContent to set
	 */
	public void setInformationContent(String informationContent) {
		this.informationContent = informationContent;
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
	 * @return the informationId
	 */
	public Integer getInformationId() {
		return informationId;
	}

	/**
	 * @param informationId the informationId to set
	 */
	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
	}

	/**
	 * @return the hotType
	 */
	public Integer getHotType() {
		return hotType;
	}

	/**
	 * @param hotType the hotType to set
	 */
	public void setHotType(Integer hotType) {
		this.hotType = hotType;
	}

	/**
	 * @return the informationState
	 */
	public EnableStatus getInformationState() {
		return informationState;
	}

	/**
	 * @param informationState the informationState to set
	 */
	public void setInformationState(EnableStatus informationState) {
		this.informationState = informationState;
	}

	/**
	 * @return the informationType
	 */
	public DictionaryItem getInformationType() {
		return informationType;
	}

	/**
	 * @param informationType the informationType to set
	 */
	public void setInformationType(DictionaryItem informationType) {
		this.informationType = informationType;
	}
	
	public String getTransformationImageUrl(){
		return Constant.transformationImageUrl(this.getPicPath());
	}
	
	public String getTransformationContent(){
		return Constant.transformationTextUrl(informationContent);
	}

	public String getPicThumbnailPath() {
		return picThumbnailPath;
	}

	public void setPicThumbnailPath(String picThumbnailPath) {
		this.picThumbnailPath = picThumbnailPath;
	}
	
	
}
