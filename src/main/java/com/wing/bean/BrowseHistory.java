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

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author 杨康
 *	浏览历史
 */
@Entity
@Table(name = "crm_browse_history")
public class BrowseHistory {
	
	public BrowseHistory(){};
	
	public BrowseHistory(Customer customer, TourismItinerary tourismItinerary){
		
		this.customer = customer;
		this.tourismItinerary = tourismItinerary;
		this.browseDate = new Date();
	};
	
	@Id
	@TableGenerator(name = "tg_browse_history", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_browse_history", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_browse_history")
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "browse_date", nullable = false, length = 19)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date browseDate;
	
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	private Customer customer;
	
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "itinerary_id", referencedColumnName = "itineraryId", updatable=false)
	private TourismItinerary tourismItinerary;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the browseDate
	 */
	public Date getBrowseDate() {
		return browseDate;
	}

	/**
	 * @param browseDate the browseDate to set
	 */
	public void setBrowseDate(Date browseDate) {
		this.browseDate = browseDate;
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
