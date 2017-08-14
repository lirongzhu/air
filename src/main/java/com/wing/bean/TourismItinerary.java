package com.wing.bean;

import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.wing.common.constant.Constant;
import com.wing.common.enums.EnableStatus;

/**
 * @author 杨康
 * 行程路线
 */
@Entity
@Table(name = "crm_tourism_itinerary")
public class TourismItinerary {
	
	@Id
    @TableGenerator(name = "tg_tourism_Itinerary", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_tourism_Itinerary", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_tourism_Itinerary")
    private Integer itineraryId;
	
	/**
	 * 路线名称
	 */
	@Column(name = "route_name",  length = 256)
	private String routeName;
	
	/**
	 *产品编号 
	 */
	@Column(name = "product_number",  length = 32)
	private String  productNumber;
	
	/**
	 * 路线简介
	 */
	@Column(name = "route_introduction",  length = 256)
	private String routeIntroduction;
	
	/**
	 * 出发地点
	 */
	@Column(name = "point_of_departure",  length = 256)
	private String pointOfDeparture;
	
	/**
	 * 目的地
	 */
	@Column(name = "destination",  length = 256)
	private String destination;
	
	/**
	 * 行程天数
	 */
	@Column(name = "route_days",  length = 3)
	private Integer routeDays;
	
	/**
	 * 是否可用
	 */
	@Column(name = "enable",  length = 1)
	private EnableStatus enable;
	
	/**
	 * 原价
	 */
	@Column(name = "original_price", precision = 14, scale = 3)
	private Double originalPrice;
	
	/**
	 * 折扣
	 */
	@Column(name = "discount",  length = 3)
	private String discount;
	
	/**
	 * 现价
	 */
	@Column(name = "now_price", precision = 14, scale = 3)
	private Double nowPrice;
	
	/**
	 * 图片
	 */
	@Column(name = "picture_relative_path",  length = 256)
	private String pictureRelativePath;
	
	/**
	 * 小图
	 */
	@Column(name = "picture_thumbnail_path", length = 256)
	private String pictureThumbnailPath;
	
	/**
	 * 行程类型
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "tourism_type", referencedColumnName = "itemId", insertable=false, updatable = false)
	private DictionaryItem tourismType;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "belong_function", referencedColumnName = "itemId", insertable=false, updatable = false)
	private DictionaryItem belongFunction;
	
	//产品类别
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "rang_type", referencedColumnName = "itemId", insertable=false, updatable = false)
	private DictionaryItem rangType;
	
	//卖点
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "selling_point", referencedColumnName = "itemId", insertable=false, updatable = false)
	private DictionaryItem sellingPoint;
	
	//所需证件类型
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "card_type", referencedColumnName = "itemId", insertable=false, updatable = false)
	private DictionaryItem cardType;
	
	//关键字
	@Column(name = "key_word",  length = 255)
	private String keyWord;
	
	@Column(name="has_delete", length = 2)
	private Integer hasDelete;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  
	@JSONField(format="yyyy-MM-dd HH:mm:ss", serialize=false)
    @Column(name = "create_date", nullable = false, length = 19)
	private Date createDate;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "create_user", referencedColumnName = "id")
	private User createUser;
	
	/**
	 * 审核状态
	 */
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "examine_result", referencedColumnName = "itemId", insertable=false, updatable = false)
	private DictionaryItem examineResult;
	
	@Transient
	private List<TourismCostStatement> costStatementList;
	
	@Transient
	private List<TourismScheduling> schedulingList;
	
	@Transient
	private List<TourismSpecialNotice> specialNoticeList;
	
	@Transient
	private TourismFeature tourismFeature;

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getRouteIntroduction() {
		return routeIntroduction;
	}

	public void setRouteIntroduction(String routeIntroduction) {
		this.routeIntroduction = routeIntroduction;
	}

	public String getPointOfDeparture() {
		return pointOfDeparture;
	}

	public void setPointOfDeparture(String pointOfDeparture) {
		this.pointOfDeparture = pointOfDeparture;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Integer getRouteDays() {
		return routeDays;
	}

	public void setRouteDays(Integer routeDays) {
		this.routeDays = routeDays;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public Double getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(Double nowPrice) {
		this.nowPrice = nowPrice;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	/**
	 * @return the itineraryId
	 */
	public Integer getItineraryId() {
		return itineraryId;
	}

	/**
	 * @param itineraryId the itineraryId to set
	 */
	public void setItineraryId(Integer itineraryId) {
		this.itineraryId = itineraryId;
	}

	/**
	 * @return the costStatementList
	 */
	public List<TourismCostStatement> getCostStatementList() {
		return costStatementList;
	}

	/**
	 * @param costStatementList the costStatementList to set
	 */
	public void setCostStatementList(List<TourismCostStatement> costStatementList) {
		this.costStatementList = costStatementList;
	}

	/**
	 * @return the schedulingList
	 */
	public List<TourismScheduling> getSchedulingList() {
		return schedulingList;
	}

	/**
	 * @param schedulingList the schedulingList to set
	 */
	public void setSchedulingList(List<TourismScheduling> schedulingList) {
		this.schedulingList = schedulingList;
	}

	/**
	 * @return the specialNoticeList
	 */
	public List<TourismSpecialNotice> getSpecialNoticeList() {
		return specialNoticeList;
	}

	/**
	 * @param specialNoticeList the specialNoticeList to set
	 */
	public void setSpecialNoticeList(List<TourismSpecialNotice> specialNoticeList) {
		this.specialNoticeList = specialNoticeList;
	}

	/**
	 * @return the tourismFeature
	 */
	public TourismFeature getTourismFeature() {
		return tourismFeature;
	}

	/**
	 * @param tourismFeature the tourismFeature to set
	 */
	public void setTourismFeature(TourismFeature tourismFeature) {
		this.tourismFeature = tourismFeature;
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
	 * @return the pictureRelativePath
	 */
	public String getPictureRelativePath() {
		return pictureRelativePath;
	}

	/**
	 * @param pictureRelativePath the pictureRelativePath to set
	 */
	public void setPictureRelativePath(String pictureRelativePath) {
		this.pictureRelativePath = pictureRelativePath;
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
	 * @return the keyWord
	 */
	public String getKeyWord() {
		return keyWord;
	}

	/**
	 * @param keyWord the keyWord to set
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	/**
	 * @return the tourismType
	 */
	public DictionaryItem getTourismType() {
		return tourismType;
	}

	/**
	 * @param tourismType the tourismType to set
	 */
	public void setTourismType(DictionaryItem tourismType) {
		this.tourismType = tourismType;
	}

	/**
	 * @return the rangType
	 */
	public DictionaryItem getRangType() {
		return rangType;
	}

	/**
	 * @param rangType the rangType to set
	 */
	public void setRangType(DictionaryItem rangType) {
		this.rangType = rangType;
	}

	/**
	 * @return the sellingPoint
	 */
	public DictionaryItem getSellingPoint() {
		return sellingPoint;
	}

	/**
	 * @param sellingPoint the sellingPoint to set
	 */
	public void setSellingPoint(DictionaryItem sellingPoint) {
		this.sellingPoint = sellingPoint;
	}

	/**
	 * @return the cardType
	 */
	public DictionaryItem getCardType() {
		return cardType;
	}

	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(DictionaryItem cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return the examineResult
	 */
	public DictionaryItem getExamineResult() {
		return examineResult;
	}

	/**
	 * @param examineResult the examineResult to set
	 */
	public void setExamineResult(DictionaryItem examineResult) {
		this.examineResult = examineResult;
	}

	/**
	 * @return the hasDelete
	 */
	public Integer getHasDelete() {
		return hasDelete;
	}

	/**
	 * @param hasDelete the hasDelete to set
	 */
	public void setHasDelete(Integer hasDelete) {
		this.hasDelete = hasDelete;
	}
	
	public String getTransformationImageUrl(){
		return Constant.transformationImageUrl(this.getPictureRelativePath());
	}

	/**
	 * @return the belongFunction
	 */
	public DictionaryItem getBelongFunction() {
		return belongFunction;
	}

	/**
	 * @param belongFunction the belongFunction to set
	 */
	public void setBelongFunction(DictionaryItem belongFunction) {
		this.belongFunction = belongFunction;
	}

	public String getPictureThumbnailPath() {
		return pictureThumbnailPath;
	}

	public void setPictureThumbnailPath(String pictureThumbnailPath) {
		this.pictureThumbnailPath = pictureThumbnailPath;
	}
	
	
	
}
