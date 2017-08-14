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

import com.wing.common.constant.Constant;

/**
 * @author 杨康
 *	行程特色
 */
@Entity
@Table(name = "crm_tourism_feature")
public class TourismFeature {
	
	@Id
    @TableGenerator(name = "tg_tourism_feature", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_tourism_feature", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_tourism_feature")
    private Integer featureId;
	
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "itinerary_id", referencedColumnName = "itineraryId", updatable=false)
    private TourismItinerary tourismItinerary;
	
	/**
	 * 文字介绍
	 */
	@Lob
	@Column(name = "text_description")
	private String textDescription;

	public String getTextDescription() {
		return textDescription;
	}

	public void setTextDescription(String textDescription) {
		this.textDescription = textDescription;
	}

	/**
	 * @return the featureId
	 */
	public Integer getFeatureId() {
		return featureId;
	}

	/**
	 * @param featureId the featureId to set
	 */
	public void setFeatureId(Integer featureId) {
		this.featureId = featureId;
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
	
	public String getTransformTextDescription(){
		return Constant.transformationTextUrl(this.getTextDescription());
	}
}
