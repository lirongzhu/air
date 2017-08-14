package com.wing.bean;

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

/**
 * @author 杨康
 * 行程安排
 */
@Entity
@Table(name = "crm_tourism_scheduling")
public class TourismScheduling {
	
	@Id
    @TableGenerator(name = "tg_tourism_scheduling", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_tourism_scheduling", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_tourism_scheduling")
    private Integer schedulingId;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "itinerary_id", referencedColumnName = "itineraryId", updatable=false)
    private TourismItinerary tourismItinerary;
	
	/**
	 * 天数
	 */
	@Column(name = "days")
	private Integer days;

	/**
	 * 交通
	 */
	@Column(name="traffic",length=256)
	private String traffic;
	
	/**
	 * 住宿
	 */
	@Column(name="stay",length=256)
	private String stay;
	
	/**
	 * 餐饮
	 */
	@Column(name="repast",length=256)
	private String repast;
	
	/**
	 * 景点
	 */
	@Column(name="attractions",length=256)
	private String attractions;

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getStay() {
		return stay;
	}

	public void setStay(String stay) {
		this.stay = stay;
	}

	public String getRepast() {
		return repast;
	}

	public void setRepast(String repast) {
		this.repast = repast;
	}

	public String getAttractions() {
		return attractions;
	}

	public void setAttractions(String attractions) {
		this.attractions = attractions;
	}

	/**
	 * @return the schedulingId
	 */
	public Integer getSchedulingId() {
		return schedulingId;
	}

	/**
	 * @param schedulingId the schedulingId to set
	 */
	public void setSchedulingId(Integer schedulingId) {
		this.schedulingId = schedulingId;
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
	
	
}
