package com.wing.bean;

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

/**
 * @author 杨康
 * 行程点评
 */
@Entity
@Table(name = "crm_tourism_rate")
public class TourismRate {
	
	@Id
    @TableGenerator(name = "tg_tourism_rate", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_tourism_rate", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_tourism_rate")
    private Integer id;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "itinerary_id", referencedColumnName = "itineraryId")
    private TourismItinerary tourismItinerary;
	
	/**
	 * 导游服务
	 */
	@Column(name = "guide_service",  length = 256)
	private String guideService;
	
	/**
	 * 行程安排
	 */
	@Column(name = "scheduling",  length = 256)
	private String scheduling;
	
	/**
	 * 餐饮住宿
	 */
	@Column(name = "dining_accommodation",  length = 256)
	private String diningAccommodation;
	
	/**
	 * 旅行交通
	 */
	@Column(name = "travel_traffic",  length = 256)
	private String travelTraffic;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
	
	/**
	 * 评语
	 */
	@Lob
	@Column(name = "remark",  length = 256)
	private String remark;
	
	/**
	 * 上传图片1
	 */
	@Column(name = "upload_image_one",  length = 256)
	private String uploadImageOne;
	
	/**
	 * 上传图片2
	 */
	@Column(name = "upload_image_two",  length = 256)
	private String uploadImageTwo;
	
	/**
	 * 上传图片3
	 */
	@Column(name = "upload_image_there",  length = 256)
	private String uploadImageThere;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGuideService() {
		return guideService;
	}

	public void setGuideService(String guideService) {
		this.guideService = guideService;
	}

	public String getScheduling() {
		return scheduling;
	}

	public void setScheduling(String scheduling) {
		this.scheduling = scheduling;
	}

	public String getDiningAccommodation() {
		return diningAccommodation;
	}

	public void setDiningAccommodation(String diningAccommodation) {
		this.diningAccommodation = diningAccommodation;
	}

	public String getTravelTraffic() {
		return travelTraffic;
	}

	public void setTravelTraffic(String travelTraffic) {
		this.travelTraffic = travelTraffic;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUploadImageOne() {
		return uploadImageOne;
	}

	public void setUploadImageOne(String uploadImageOne) {
		this.uploadImageOne = uploadImageOne;
	}

	public String getUploadImageTwo() {
		return uploadImageTwo;
	}

	public void setUploadImageTwo(String uploadImageTwo) {
		this.uploadImageTwo = uploadImageTwo;
	}

	public String getUploadImageThere() {
		return uploadImageThere;
	}

	public void setUploadImageThere(String uploadImageThere) {
		this.uploadImageThere = uploadImageThere;
	}

	/**
	 * @return the tourismItinerary
	 */
	public TourismItinerary getTourismItinerary() {
		return tourismItinerary;
	}

	/**
	 * @param tourismItinerary the tourismItinerary to set
	 */
	public void setTourismItinerary(TourismItinerary tourismItinerary) {
		this.tourismItinerary = tourismItinerary;
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
