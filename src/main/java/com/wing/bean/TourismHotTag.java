package com.wing.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author 杨康
 *	热门行程标签
 */
@Entity
@Table(name = "crm_tourism_hot_tag")
public class TourismHotTag {
	
	@Id
    @TableGenerator(name = "tg_tourism_hot_tag", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_tourism_hot_tag", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_tourism_hot_tag")
	private Integer id;
	
	/**
	 * 热门行程ID
	 */
	@Column(name = "popular_travel_id",length = 32)
	private Integer popularTravelId;

	/**
	 * 标签
	 */
	@Column(name = "label",length = 32)
	private String label;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPopularTravelId() {
		return popularTravelId;
	}

	public void setPopularTravelId(Integer popularTravelId) {
		this.popularTravelId = popularTravelId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
