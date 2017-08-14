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
 *	特别提示
 */
@Entity
@Table(name = "crm_tourism_special_notice")
public class TourismSpecialNotice {

	@Id
    @TableGenerator(name = "tg_tourism_special_notice", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_tourism_special_notice", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_tourism_special_notice")
    private Integer specialNoticeId;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "itinerary_id", referencedColumnName = "itineraryId", updatable=false)
    private TourismItinerary tourismItinerary;
	
	/**
	 * 特别提示
	 */
	@Column(name = "special_notice",  length = 256)
	private String specialNotice;

	/**
	 * @return the specialNotice
	 */
	public String getSpecialNotice() {
		return specialNotice;
	}

	/**
	 * @param specialNotice the specialNotice to set
	 */
	public void setSpecialNotice(String specialNotice) {
		this.specialNotice = specialNotice;
	}

	/**
	 * @return the specialNoticeId
	 */
	public Integer getSpecialNoticeId() {
		return specialNoticeId;
	}

	/**
	 * @param specialNoticeId the specialNoticeId to set
	 */
	public void setSpecialNoticeId(Integer specialNoticeId) {
		this.specialNoticeId = specialNoticeId;
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
